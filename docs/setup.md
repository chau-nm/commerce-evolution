# Setup

This repo is a monorepo of two independently deployable Spring Boot applications:

- **`modular-monolith/`** — Authentication, Product Management (catalog), Inventory, Order,
  Cart, Customer, Notification. Listens on `:8080`.
- **`payment-service/`** — Payment, extracted into its own app with its own database. Listens
  on `:8081`.

Each has its own Gradle wrapper, `Dockerfile`, and standalone `docker-compose.yml`, so either
can be built/run/tested entirely on its own. The root `docker-compose.yml` brings both up
together, which you need for order → payment HTTP calls to actually work end to end.

## Prerequisites

- **JDK 25** (the Gradle toolchain is pinned to Java 25 — see `build.gradle.kts` in each app),
  unless you run everything through Docker, in which case the JDK inside the container is enough.
- **Docker + Docker Compose** — used to run Postgres (and optionally the whole app) locally.
- **Git**

## 1. Clone the repository

```bash
git clone <repo-url>
cd commerce-evolution
```

## 2. Generate a JWT key pair (modular-monolith only)

`modular-monolith`'s `KeyConfiguration` signs/verifies access tokens with an RSA key pair loaded
from `modular-monolith/src/main/resources/jwt/{private,public}.pem`. These files are
**git-ignored on purpose** (private key material should never be committed), so you must
generate your own before that app will start. `payment-service` has no JWT/user-auth of its own
(service-to-service calls use a shared internal API key instead — see below), so this step is
only needed for `modular-monolith`.

```bash
mkdir -p modular-monolith/src/main/resources/jwt
openssl genpkey -algorithm RSA -out modular-monolith/src/main/resources/jwt/private.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in modular-monolith/src/main/resources/jwt/private.pem -out modular-monolith/src/main/resources/jwt/public.pem
```

Without these files, any endpoint that touches authentication (and any test that loads the full
Spring context, e.g. `@WebMvcTest` controller tests) fails at startup with a
`FileNotFoundException` from `ClassPathResource`.

The key paths are configurable via `app.jwt.private-key` / `app.jwt.public-key` if you want to
point at a different location.

## 3. Start both apps

**Fully in Docker, both apps + both databases together** (recommended — this is the only way to
exercise order ↔ payment HTTP calls locally):

```bash
docker compose up --build
```

This builds the `dev` target from each app's `docker/Dockerfile`/`Dockerfile`, mounts each app
into its container, and watches/recompiles on change. `modular-monolith` listens on
`http://localhost:8080`, `payment-service` on `http://localhost:8081`.

**Locally with Gradle, one app at a time** (requires JDK 25 and step 2 done for
modular-monolith):

```bash
# Postgres for this app only
cd modular-monolith && docker compose up -d database && ./gradlew bootRun
# in another shell
cd payment-service && docker compose up -d payment-database && ./gradlew bootRun
```

Set `PAYMENT_SERVICE_BASE_URL` (on modular-monolith) / `ORDER_SERVICE_BASE_URL` (on
payment-service) if you run them this way — they default to `localhost:8081` /
`localhost:8080`, which is correct outside Docker but not between two Docker containers (the
root `docker-compose.yml` sets these to the container hostnames automatically).

Both apps share one internal secret, `INTERNAL_API_KEY` (default `dev-internal-api-key` in both
`application.yaml`s), used to authenticate their service-to-service calls to each other
(`POST /api/v1/payments`, `POST /internal/payment-events`, `PUT /api/v1/orders/{id}/pay`) — set
it to the same value on both sides if you override it.

## 4. Run tests

From inside either app's directory:

```bash
./gradlew test
```

`modular-monolith` tests need both a running Postgres (step 3) and a valid JWT key pair (step 2)
— the app config is shared between `bootRun` and `test`, there's no separate test profile.
`payment-service` tests only need its own Postgres.

To run a single test class:

```bash
./gradlew test --tests "dev.chaunm.commerceevolution.CommerceevolutionApplicationTests"
```

Or entirely inside Docker (also generates a throwaway key pair first, useful for CI or a
machine without a local JDK), from the repo root:

```bash
mkdir -p modular-monolith/src/main/resources/jwt
openssl genpkey -algorithm RSA -out modular-monolith/src/main/resources/jwt/private.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in modular-monolith/src/main/resources/jwt/private.pem -out modular-monolith/src/main/resources/jwt/public.pem
docker compose run --rm --build server ./gradlew test --no-daemon
docker compose run --rm --build payment-server ./gradlew test --no-daemon
```

## Environment variables (all optional, defaults shown)

### modular-monolith

| Variable                   | Default                                                  | Purpose                                         |
|-----------------------------|-------------------------------------------------------------|---------------------------------------------------|
| `DB_URL`                   | `jdbc:postgresql://localhost:5432/commerceevolutiondb`  | Postgres JDBC URL                               |
| `DB_USERNAME`               | `admin`                                                 | Postgres user                                   |
| `DB_PASSWORD`               | `admin`                                                 | Postgres password                               |
| `JWT_ACCESS_TOKEN_TTL`      | `PT15M`                                                 | Access token lifetime (ISO-8601)                |
| `JWT_REFRESH_TOKEN_TTL`     | `P30D`                                                  | Refresh token lifetime (ISO-8601)               |
| `MEDIA_UPLOAD_DIR`          | `uploads`                                                | Local dir for uploaded media                    |
| `MEDIA_PUBLIC_PATH`         | `/media`                                                | Public URL path for media                       |
| `INTERNAL_API_KEY`          | `dev-internal-api-key`                                  | Shared secret for calls to/from payment-service |
| `PAYMENT_SERVICE_BASE_URL`  | `http://localhost:8081`                                 | Where to reach payment-service                  |

### payment-service

| Variable                | Default                                       | Purpose                                          |
|---------------------------|--------------------------------------------------|-----------------------------------------------------|
| `DB_URL`                | `jdbc:postgresql://localhost:5433/paymentdb`  | Postgres JDBC URL                                |
| `DB_USERNAME`            | `admin`                                       | Postgres user                                    |
| `DB_PASSWORD`            | `admin`                                       | Postgres password                                |
| `SERVER_PORT`            | `8081`                                        | HTTP port                                        |
| `INTERNAL_API_KEY`       | `dev-internal-api-key`                        | Shared secret for calls to/from modular-monolith |
| `ORDER_SERVICE_BASE_URL` | `http://localhost:8080`                       | Where to reach modular-monolith                  |

## Troubleshooting

- **`FileNotFoundException` on `ClassPathResource` at startup or in tests (modular-monolith
  only)** — the JWT key pair (step 2) is missing. This is the most common first-run issue since
  the keys aren't in git.
- **Flyway/Hibernate schema errors** — `ddl-auto` is set to `validate` in both apps, so schema
  changes must go through a new migration in `<app>/src/main/resources/db/migration`, never
  through entity changes alone.
- **`401` on `POST /api/v1/payments`, `POST /internal/payment-events`, or
  `PUT /api/v1/orders/{id}/pay`** — these are service-to-service only; missing/mismatched
  `INTERNAL_API_KEY` between the two apps.
- **`Unable to delete file '.../build/classes/java/main'` from Gradle** — usually a transient
  file lock (another process, e.g. an IDE indexer or a leftover Gradle daemon, briefly holding
  the file). Retry the build; if it persists, stop any running Gradle daemons
  (`./gradlew --stop`) or run `./gradlew clean` first.
