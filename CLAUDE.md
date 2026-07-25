# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project vision

Commerce Evolution is a learning project (not production-bound) that models an e-commerce
backend and deliberately evolves its architecture over time: starting as a well-designed
**modular monolith** with strict Domain-Driven Design boundaries, adding distributed-system
patterns only when needed, and eventually splitting into microservices. This explains why the
code favors explicit bounded-context boundaries, domain purity (no framework types leaking into
`domain`), and small, deliberate abstractions even though most of the platform still lives in
one Spring Boot application.

The repo is a monorepo of independently deployable applications, one directory each:

- **`modular-monolith/`** — the original app: Authentication, Product Management (`catalog`),
  Inventory, Order, Cart, Customer, Notification. Its own Gradle build/wrapper, own Dockerfile,
  own `docker-compose.yml`.
- **`payment-service/`** — Payment, extracted out of the monolith into its own Spring Boot app
  with its own database. See "Payment service (extracted)" below.
- Root `docker-compose.yml` brings both up together (needed for order <-> payment HTTP calls);
  each also has its own standalone compose file for developing it in isolation.

**Authentication** and **Product Management** (`catalog`) were the first bounded contexts to
land in `modular-monolith/` and remain the reference pattern when scaffolding a new one there.

## Commands

All of these run from **`modular-monolith/`** (its own Gradle wrapper) unless noted otherwise;
`payment-service/` has the identical set, run from `payment-service/` instead.

```bash
# Start Postgres for just this app (from modular-monolith/); or `docker compose up -d` from the
# repo root to start both apps + both databases together
docker compose up -d

# Build / compile
./gradlew compileJava

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "dev.chaunm.commerceevolution.CommerceevolutionApplicationTests"

# Run the app locally
./gradlew bootRun
```

Java toolchain is pinned to **Java 25** (see `build.gradle.kts`). Flyway runs migrations
automatically on startup (`spring.flyway.enabled=true`) and Hibernate is set to
`ddl-auto: validate` — schema changes must go through a new Flyway migration in
`<app>/src/main/resources/db/migration`, never through entity/annotation changes alone.

Default local DB connection (overridable via `DB_URL`/`DB_USERNAME`/`DB_PASSWORD` env vars):
`jdbc:postgresql://localhost:5432/commerceevolutiondb`, user/pass `admin`/`admin` (see
`modular-monolith/docker-compose.yml` and `modular-monolith/src/main/resources/application.yaml`).

## Architecture: DDD bounded contexts

Each bounded context lives under
`modular-monolith/src/main/java/dev/chaunm/commerceevolution/<context>/` (e.g. `authentication/`)
and is internally layered as:

```
<context>/
  domain/            # pure business logic, no Spring/JPA/web imports
    model/            # aggregates + value objects (records)
    model/valueobject/
    event/            # domain events (marker interface DomainEvent)
    exception/        # per-context ErrorCode enum + DomainException subclasses
    factory/          # static factories that build aggregates and register events
    repository/       # repository *interfaces* (no persistence detail)
    service/          # domain service interfaces (e.g. PasswordHasher, JwtProvider)
  application/
    usecase/<name>/   # UseCase interface + Impl + Command/Result + MapStruct Mapper
    event/             # @TransactionalEventListener handlers reacting to domain events
  infrastructure/
    persistence/       # JPA entities, Spring Data repos, MapStruct entity<->domain mappers,
                        # repository interface implementations
    security/          # context-specific security wiring (JWT, filters, key config)
  presentation/
    <usecase>/          # @RestController + Request/Response DTOs, one usecase per subpackage
```

Cross-context shared code lives in `shared/` (`shared.domain`, `shared.exception`,
`shared.infrastructure`) and must stay generic — nothing in `shared` should depend on a
specific bounded context.

### Key conventions to follow when extending a context

- **Aggregates extend `AggregateRoot`** (`shared/domain/model/AggregateRoot.java`), which
  accumulates `DomainEvent`s via `registerEvent(...)`. Events are raised inside domain
  **factories** (see `AccountFactory`), not inside use cases.
- **Value objects are validating `record`s** in `domain/model/valueobject/` (e.g. `Email`,
  `AccountId`, `HashPassword`) — validation happens in the compact constructor and throws a
  domain exception on failure (never returns a partially-invalid instance).
- **Use cases** are one interface + one `*Impl` per operation (not one god-service). The
  `*Impl` is `@Transactional`, orchestrates: validate → call domain factory/aggregate →
  persist via repository interface → publish accumulated domain events via
  `SpringDomainEventPublisher` (only after save, since publishing dispatches through Spring's
  `ApplicationEventPublisher`).
- **Domain event handlers** use `@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)`
  so side effects only run once the DB transaction has actually committed.
- **Errors**: each context defines its own `enum ... implements ErrorCode`
  (e.g. `AuthenticationErrorCode`) and throws exceptions extending the shared base types in
  `shared/exception` (`ConflictException`, `NotFoundException`, `UnauthorizedException`,
  `DomainException` for generic 400s). `GlobalExceptionHandler` is the single place that maps
  these to RFC 7807 `ProblemDetail` responses — controllers and use cases never set HTTP status
  directly.
- **Persistence**: domain model and JPA entity are always separate classes, converted via a
  `@Mapper(componentModel = "spring")` MapStruct interface with explicit `default` methods for
  value-object <-> primitive conversions (see `AccountMapper`). The repository interface lives
  in `domain/repository`; `infrastructure/persistence/repository` has the Spring Data JPA repo
  plus an `Impl` that implements the domain interface by delegating to it through the mapper.
  All JPA entities extend `shared/infrastructure/persistence/entity/BaseEntity`, which supplies
  audited `createdAt`/`updatedAt` via `@EntityListeners(AuditingEntityListener.class)`
  (`@EnableJpaAuditing` must stay enabled for this to populate).
- **Presentation** controllers are thin: `@Valid @RequestBody` DTO → mapper → use-case command →
  mapper → response DTO. No business logic in controllers.
- **Pagination**: list endpoints take a `PaginationRequest` (`shared/presentation/pagination`,
  1-based `page`), convert it to a `PaginationQuery` (`shared/application/pagination`, 0-based
  `pageIndex`) via `PaginationQuery.from(...)`, and repositories return a
  `PaginationResult<T>` built with `PaginationResult.from(springDataPage)`; use `.map(...)` to
  project domain results to response items and `.toResponse()` to build the final
  `PaginationResponse` (see `catalog` `listproducts` for the full round trip).

### Auth/security specifics

- JWTs are RS256, signed/verified via Nimbus using an `RSAKey` built in `KeyConfiguration` from
  PEM files at `modular-monolith/src/main/resources/jwt/{private,public}.pem` (paths configurable via
  `app.jwt.private-key` / `app.jwt.public-key`). Access/refresh token TTLs are configured via
  `app.jwt.access-token-ttl` / `refresh-token-ttl` (ISO-8601 durations, env-overridable).
- `SecurityConfiguration` permits `/api/v1/auth/**` and `/actuator/**`; everything else requires
  authentication. `AuthenticationFilter` parses inbound JWTs into claims but is **not registered
  in the `SecurityFilterChain`** (no `addFilterBefore` call) and never populates
  `SecurityContext` — so protected endpoints currently reject all requests regardless of a valid
  bearer token. Wiring the filter into the chain and populating `SecurityContext` from its
  claims is outstanding work before any non-`/api/v1/auth/**` endpoint is actually reachable.
- Passwords are hashed via `PasswordHasherImpl` (BCrypt through the domain `PasswordHasher`
  port) — never hash/compare passwords outside that port.
- Login/refresh/logout are fully implemented: `LoginUseCaseImpl` issues both an access token and
  a raw refresh token (persisting only its hash via `TokenHasher`), `RefreshTokenUseCaseImpl`
  rotates it, and `LogoutUseCaseImpl` revokes it — see `RefreshTokenFactory` and the
  `refresh_tokens` table (V2 migration).

### Catalog specifics

- `Product` is the aggregate root; `ProductVariant` and `ProductMedia` are child entities held
  as in-aggregate lists (no separate repositories) — mutate them only through `Product` methods
  (`addVariant`, `removeMedia`, `publish`, `archive`, etc.), never directly.
- Status transitions are guarded on the aggregate itself (e.g. `publish()` requires `DRAFT`,
  `archive()` rejects an already-`ARCHIVED` product) and most mutators reject any change once a
  product is `ARCHIVED` — see `InvalidProductStatusTransitionException` / `ProductArchivedException`.
- Every mutation on `Product` registers a domain event, but the corresponding
  `@TransactionalEventListener` handlers under `catalog/application/event/` are currently stub
  no-ops that only log — extend them when a real side effect is needed instead of adding logic
  inline in the use case.

## Payment service (extracted)

`payment-service/`, a sibling directory to `modular-monolith/` at the repo root, is a **separate,
independently deployable Spring Boot application** — its own Gradle build/wrapper, own
`paymentdb` Postgres database (Flyway migrations under
`payment-service/src/main/resources/db/migration`), own Dockerfile and `docker-compose.yml`
(also wired into the root `docker-compose.yml` as `payment-server` / `payment-database` so the
whole platform comes up together). Its internal layering mirrors the monolith's bounded-context
convention (`domain` / `application` / `infrastructure` / `presentation` under
`dev.chaunm.paymentservice.payment`), plus a small duplicated `dev.chaunm.paymentservice.shared`
(error model, `AggregateRoot`/`DomainEvent` building blocks, `GlobalExceptionHandler`) —
duplicated rather than published as a shared library on purpose, to avoid cross-repo build
coupling for ~150 lines of infra; revisit with a real shared-kernel artifact once a third
service is extracted.

`modular-monolith/` talks to it purely over HTTP, never via shared DB or in-process calls:

- **Order → Payment**: `order/application/event/InitiatePaymentOnOrderCreatedEventHandler`
  reacts to `OrderCreatedEvent` (`AFTER_COMMIT`, same as before) and calls
  `order/application/port/PaymentServiceClient` (impl in `order/infrastructure/client/`), which
  wraps a `RestClient` call to payment-service's `POST /api/v1/payments` with a resilience4j
  timeout + retry + circuit breaker. A 409 (payment-service already has a payment for that
  order) is treated as a delivered, non-retried outcome, not a failure — order placement is
  never blocked or rolled back by a payment-service outage.
- **Payment → Order**: payment-service's `PaymentSucceededEventHandler` /
  `PaymentFailedEventHandler` (`AFTER_COMMIT` listeners on its own domain events) call an
  outbound `PaymentEventNotifier` port that POSTs the outcome to this monolith's
  `POST /internal/payment-events` webhook, with the same resilience policy. That webhook
  (`paymentevents/` package) is the **single inbound integration point**: it marks the order
  paid via `MarkOrderPaidUseCase` and creates the success/failure notification via
  `CreateNotificationUseCase`, resolving the customer through the existing `OrderDirectory`
  port. It is idempotent by construction — `paymentevents.infrastructure.persistence` records
  each `paymentId` the first time it's seen (a payment only ever settles once) and skips any
  redelivery.
- Both the webhook and `PUT /api/v1/orders/{orderId}/pay` are service-to-service only: gated by
  a shared static header (`X-Internal-Api-Key`, see `shared/infrastructure/security/`) rather
  than end-user JWT, wired into `SecurityConfiguration` ahead of `AuthenticationFilter`. This is
  a minimum-viable control, not real service auth — see the migration report for the
  recommended follow-up (verify-only JWT using the shared RSA public key).

No Docker daemon was available in the environment this extraction was performed in; the
end-to-end HTTP flow (initiate → confirm → webhook → order paid → notification, plus both
idempotency guards) was instead verified by running both apps directly against ephemeral
Postgres containers. `docker compose config` was used to validate the compose files themselves.
