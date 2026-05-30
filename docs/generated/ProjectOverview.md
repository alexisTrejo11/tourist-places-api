# Project Overview

## Scattered travel data with no unified API

Travel apps and content sites need a single backend to browse countries, categorize destinations, read reviews, and let users save personal place lists. Without a structured API, frontends duplicate validation, auth, and rating logic.

### Pain points

- No shared catalog of countries, tourist places, categories, and activities
- User-generated reviews and ratings stored inconsistently across clients
- Account activation, password reset, and OAuth flows reimplemented per app
- Hard to deploy consistently across environments without container + migration strategy
- Public browse vs authenticated write operations need clear security boundaries

## Modular Spring Boot REST API for tourist content

- **Domain modules with clear boundaries** — Separate packages for auth, users, countries, places, activities, and reviews—each with controllers, services, DTOs, and repositories.
- **JWT + Google OAuth2 authentication** — Email/password signup with activation tokens, refresh tokens, Redis-backed logout blacklist, and optional Google social login.
- **Rich place discovery** — Search, filter by country/category, paginated listings, and user-curated place lists for trip planning.
- **Schema-managed PostgreSQL** — Flyway migrations version the relational model; JPA validates against RDS in production.
- **AWS-ready container deploy** — Docker image on EC2 connects to external RDS PostgreSQL and Redis/ElastiCache—no bundled database in compose.

## Platform snapshot

- 6 domain modules (auth, user, country, places, activity, review)
- REST API under v1/api/* and /auth/*
- 158 Java source files in src/main/java
- 18 controller/service test classes
- OpenAPI at /swagger-ui.html and /v3/api-docs
- Flyway V1 migration with 8 core tables

## Links

| Resource | URL |
| --- | --- |
| Github | https://github.com/alexisTrejo11/tourist-places-api |
| Demo | https://api.tourist-places.example.com/swagger-ui.html |
| Documentation | https://api.tourist-places.example.com/swagger-ui.html |
| Dockerhub | https://hub.docker.com/r/alexistrejo11/tourist-places-api |

## Tourist Places API — product views

Portfolio screenshots and diagrams. Replace placeholder URLs with Swagger UI captures and architecture diagrams from your AWS deployment.

### API cover

Tourist Places REST API for destination discovery and user lists

- **Type:** image | **Category:** screenshot
- ![Tourist Places API branding placeholder](https://placehold.co/1200x630/1E3A5F/ffffff?text=Tourist+Places+API)

### OpenAPI documentation

Interactive API schema generated with springdoc-openapi

- **Type:** image | **Category:** demo
- ![OpenAPI Swagger UI placeholder](https://placehold.co/1200x800/2563EB/ffffff?text=Swagger+OpenAPI)

## Additional media

### AWS deployment architecture

Spring Boot container on EC2 with RDS PostgreSQL and Redis for sessions/tokens

### Docker on EC2

docker-compose builds app service; database and cache are cloud-managed

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Domain modules | 6 | auth, user, country, places, activity, review |
| API version prefix | v1 | Primary paths under /v1/api/ |
| Java runtime | 17 | Eclipse Temurin in Docker multi-stage Maven build |
| Auth | JWT + OAuth2 | Bearer tokens with Redis blacklist; Google OAuth2 optional |

## Additional notes

# Overview

> **Audience:** Developers building travel/tourism frontends or portfolio consumers reading structured docs from `schema.ts`.

> **Useful:** All GET routes are public for browsing; POST/PUT/DELETE require a valid JWT. User-specific routes live under `/v1/api/user/*`.

> **Potentially dangerous:** Swagger annotations document ADMIN-only mutations, but `SecurityConfig` permits all GET requests without authentication—including admin list endpoints like `/v1/api/users/admin/all`. Harden with method-level `@PreAuthorize` before production. JWT filter also logs tokens to stdout—disable in prod.

> **Schema assertion:** Frontmatter keys match `ProjectOverview` in `docs/source/schema.ts`. Regenerate readable docs with `python docs/yaml_to_markdown.py`.

