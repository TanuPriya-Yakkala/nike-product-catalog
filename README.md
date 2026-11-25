# Product Catalog API

Java 17, Spring Boot 3, Spring Data JPA and PostgreSQL implementation of a read-heavy product catalog.

## Run
1. Create a PostgreSQL database named `product_catalog`.
2. Set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` if your local settings differ.
3. Run `mvn spring-boot:run`.

## Endpoints
- `POST /api/v1/products` — create
- `GET /api/v1/products/{id}` — retrieve
- `GET /api/v1/products?category=Shoes&minPrice=40&maxPrice=150&page=0&size=20` — filter and paginate
- `PUT /api/v1/products/{id}` — update
- `DELETE /api/v1/products/{id}` — delete

Example request:
```json
{
  "name": "Running Shoe",
  "category": "Shoes",
  "price": 89.99,
  "description": "Lightweight daily trainer"
}
```

## Design notes
- Filtering and pagination execute in PostgreSQL, not in application memory.
- A stable sort (`id`) makes page ordering deterministic.
- Page size is capped at 100 to protect the service.
- Indexes support category + price filtering and price-only filtering.
- Read methods use read-only transactions.
- Bean Validation protects writes; a controller advice maps expected errors to HTTP status codes.

## Production improvements to discuss
- For deep pagination or rapidly changing catalogs, replace offset pagination with keyset/cursor pagination using `(price, id)` or `(created_at, id)` as a stable cursor.
- Add Redis caching for popular product detail and category pages, with explicit invalidation on writes and a short TTL.
- Use Flyway migrations instead of `ddl-auto: update`.
- Add optimistic locking (`@Version`) to prevent lost updates.
- Add integration tests with Testcontainers, API documentation, structured logs, metrics, rate limiting, and authentication/authorization.
- Use DTO responses rather than returning entities directly in a production API; add ETags/conditional GET for cache-friendly reads.
- Validate query plans with `EXPLAIN ANALYZE` and tune indexes against real query patterns.
