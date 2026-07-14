# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project vision

Commerce Evolution is a learning project (not production-bound) that models an e-commerce
backend and deliberately evolves its architecture over time: starting as a well-designed
**modular monolith** with strict Domain-Driven Design boundaries, adding distributed-system
patterns only when needed, and eventually splitting into microservices. This explains why the
code favors explicit bounded-context boundaries, domain purity (no framework types leaking into
`domain`), and small, deliberate abstractions even though there is currently only one Spring
Boot application/module.

The intended core business domains are: Authentication, Product Management, Inventory
Management, Order Management, and Payment. Only **Authentication** is implemented so far — use
it as the reference pattern when scaffolding a new bounded context.

## Commands

```bash
# Start Postgres (required for the app and any test that touches the DB)
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
`src/main/resources/db/migration`, never through entity/annotation changes alone.

Default local DB connection (overridable via `DB_URL`/`DB_USERNAME`/`DB_PASSWORD` env vars):
`jdbc:postgresql://localhost:5432/commerceevolutiondb`, user/pass `admin`/`admin` (see
`docker-compose.yml` and `application.yaml`).

## Architecture: DDD bounded contexts

Each bounded context lives under `src/main/java/dev/chaunm/commerceevolution/<context>/` (e.g.
`authentication/`) and is internally layered as:

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

### Auth/security specifics

- JWTs are RS256, signed/verified via Nimbus using an `RSAKey` built in `KeyConfiguration` from
  PEM files at `src/main/resources/jwt/{private,public}.pem` (paths configurable via
  `app.jwt.private-key` / `app.jwt.public-key`). Access/refresh token TTLs are configured via
  `app.jwt.access-token-ttl` / `refresh-token-ttl` (ISO-8601 durations, env-overridable).
- `SecurityConfiguration` permits `/api/v1/auth/**` and `/actuator/**`; everything else requires
  authentication. `AuthenticationFilter` is where inbound JWTs get parsed into claims — request
  authentication population from those claims is not yet wired up, so keep this in mind before
  assuming `SecurityContext` is populated on protected endpoints.
- Passwords are hashed via `PasswordHasherImpl` (BCrypt through the domain `PasswordHasher`
  port) — never hash/compare passwords outside that port.
- The `refresh_tokens` table (V2 migration) exists but login/refresh-token issuance and rotation
  are not implemented yet — `LoginUseCaseImpl` currently only issues an access token.
