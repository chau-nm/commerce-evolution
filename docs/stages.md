# Stages

Commerce Evolution deliberately grows in stages, from a single well-structured Spring Boot
application to a distributed system of microservices. Each stage is a checkpoint: the system
must be working and coherent at that stage before moving to the next one. This keeps the focus
on *why* an architecture pattern is needed, not just *how* to implement it.

## Stage 1 — Modular Monolith with DDD (current — `v1-ddd`)

A single deployable Spring Boot application, but internally organized as strict Domain-Driven
Design bounded contexts: `authentication`, `catalog`, `inventory`, `order`, `payment`. Each
context is layered into `domain` / `application` / `infrastructure` / `presentation`, with
domain code kept free of framework types, aggregates raising domain events, and use cases as
one-interface-per-operation. Cross-context communication happens in-process via domain events
(`@TransactionalEventListener`), not shared tables or reaching into another context's internals.

Goal: prove the domain boundaries are right *before* paying the cost of a network between them.
A monolith with leaky boundaries only gets worse once it's split into services, so this stage is
about getting the boundaries, aggregates, and use-case shape right first.

Status: `authentication` and `catalog` are implemented as the reference pattern. `inventory`,
`order`, and `payment` packages exist and are being filled in.

## Stage 2 — Strangle the First Service

Extract the bounded context with the clearest boundary and least coupling (candidate:
`payment`, since it's mostly called *from* other contexts rather than depending on them) into
its own deployable service. Introduce:
- A message broker or HTTP contract for what was previously an in-process domain event.
- Service-to-service authentication.
- Basic observability (correlation IDs across the boundary, centralized logging).

Goal: learn the operational cost of a distributed call (latency, partial failure, retries)
against a single, low-risk context before doing it everywhere.

## Stage 3 — Full Microservices

Split the remaining bounded contexts (`authentication`, `catalog`, `inventory`, `order`) into
their own services following the same pattern validated in Stage 2. Address the cross-cutting
concerns that only bite at this scale:
- Saga / choreography for multi-context workflows that used to be a single local transaction
  (e.g. place order → reserve inventory → charge payment).
- Per-service datastores and eventual consistency instead of one shared database.
- API gateway / BFF for the presentation layer that used to be a single set of controllers.
- Distributed tracing and centralized log aggregation.

Goal: the same business capabilities as Stage 1 and 2, now running as independently deployable
services, with the distributed-system complexity justified by boundaries and contracts already
proven in earlier stages rather than guessed upfront.

## Notes

- Stages are cumulative, not a rewrite each time — later stages refactor the *deployment and
  integration* shape, not the domain model established in Stage 1.
- A stage isn't "done" by calendar time; it's done when the domains scaffolded so far
  (currently `authentication` and `catalog`, with `inventory`/`order`/`payment` catching up)
  are implemented cleanly at that stage's level of complexity.
- See [Business Requirements](./business-requirements.md) for the domain scope these stages are
  applied to.
