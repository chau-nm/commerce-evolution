# Setup

## Prerequisites

- **JDK 25** (the Gradle toolchain is pinned to Java 25 — see `build.gradle.kts`), unless you run
  everything through Docker, in which case the JDK inside the container is enough.
- **Docker + Docker Compose** — used to run Postgres (and optionally the whole app) locally.
- **Git**

## 1. Clone the repository

```bash
git clone <repo-url>
cd commerce-evolution
```

## 2. Generate a JWT key pair

`KeyConfiguration` signs/verifies access tokens with an RSA key pair loaded from
`src/main/resources/jwt/{private,public}.pem`. These files are **git-ignored on purpose**
(private key material should never be committed), so you must generate your own before the app
will start:

```bash
mkdir -p src/main/resources/jwt
openssl genpkey -algorithm RSA -out src/main/resources/jwt/private.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in src/main/resources/jwt/private.pem -out src/main/resources/jwt/public.pem
```

Without these files, any endpoint that touches authentication (and any test that loads the full
Spring context, e.g. `@WebMvcTest` controller tests) fails at startup with a
`FileNotFoundException` from `ClassPathResource`.

The key paths are configurable via `app.jwt.private-key` / `app.jwt.public-key` if you want to
point at a different location.

## 3. Start Postgres

```bash
docker compose up -d database
```

This starts a Postgres 18 container (`admin` / `admin` / `commerceevolutiondb`, matching the
defaults in `application.yaml`) on port `5432`. Flyway migrations run automatically on
application startup — no manual migration step needed.

## 4. Run the app

Two options:

**Locally with Gradle** (requires JDK 25 and step 2/3 done):

```bash
./gradlew bootRun
```

**Fully in Docker** (builds the `dev` target from `docker/Dockerfile`, mounts the repo into the
container, and watches/recompiles on change):

```bash
docker compose up --build
```

The app listens on `http://localhost:8080`.

## 5. Run tests

```bash
./gradlew test
```

Tests need both a running Postgres (step 3) and a valid JWT key pair (step 2) — the app config
is shared between `bootRun` and `test`, there's no separate test profile.

To run a single test class:

```bash
./gradlew test --tests "dev.chaunm.commerceevolution.CommerceevolutionApplicationTests"
```

Or entirely inside Docker (also generates a throwaway key pair first, useful for CI or a
machine without a local JDK):

```bash
mkdir -p src/main/resources/jwt
openssl genpkey -algorithm RSA -out src/main/resources/jwt/private.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in src/main/resources/jwt/private.pem -out src/main/resources/jwt/public.pem
docker compose run --rm --build server ./gradlew test --no-daemon
```

## Environment variables (all optional, defaults shown)

| Variable               | Default                                                | Purpose                          |
|------------------------|---------------------------------------------------------|-----------------------------------|
| `DB_URL`               | `jdbc:postgresql://localhost:5432/commerceevolutiondb`  | Postgres JDBC URL                 |
| `DB_USERNAME`          | `admin`                                                 | Postgres user                     |
| `DB_PASSWORD`          | `admin`                                                 | Postgres password                 |
| `JWT_ACCESS_TOKEN_TTL` | `PT15M`                                                 | Access token lifetime (ISO-8601)  |
| `JWT_REFRESH_TOKEN_TTL`| `P30D`                                                  | Refresh token lifetime (ISO-8601) |
| `MEDIA_UPLOAD_DIR`     | `uploads`                                               | Local dir for uploaded media      |
| `MEDIA_PUBLIC_PATH`    | `/media`                                                | Public URL path for media         |

## Troubleshooting

- **`FileNotFoundException` on `ClassPathResource` at startup or in tests** — the JWT key pair
  (step 2) is missing. This is the most common first-run issue since the keys aren't in git.
- **Flyway/Hibernate schema errors** — `ddl-auto` is set to `validate`, so schema changes must go
  through a new migration in `src/main/resources/db/migration`, never through entity changes
  alone.
- **`Unable to delete file '.../build/classes/java/main'` from Gradle** — usually a transient
  file lock (another process, e.g. an IDE indexer or a leftover Gradle daemon, briefly holding
  the file). Retry the build; if it persists, stop any running Gradle daemons
  (`./gradlew --stop`) or run `./gradlew clean` first.
