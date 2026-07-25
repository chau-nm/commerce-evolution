-- Payment is now owned exclusively by the standalone payment-service and its own database
-- (see payment-service/src/main/resources/db/migration/V1__create_payments_table.sql). This
-- monolith no longer has any code touching the payments table, so it is dropped here rather
-- than deleting the V19 migration file, which would break Flyway's checksum validation against
-- history already applied in any existing environment.
DROP TABLE IF EXISTS payments;
