# Tourist Places API

REST API for discovering and managing tourist destinations: countries, categories, places, activities, reviews, and user-curated place lists—with JWT auth, Google OAuth2, and PostgreSQL on AWS RDS.

| Field | Value |
| --- | --- |
| Project ID | tourist-places-api |
| Version | 0.0.1-SNAPSHOT |
| Language | Java |
| Framework | Spring Boot |
| Category | backend |
| Status | stable |
| Featured | Yes |
| Repository | https://github.com/alexisTrejo11/tourist-places-api |
| Live demo | https://api.tourist-places.example.com/swagger-ui.html |
| Created | 2024-01-01T00:00:00.000Z |
| Updated | 2026-05-30T00:00:00.000Z |

## Tech stack

- Java 17
- Spring Boot 3.2.2
- Spring Data JPA
- Spring Security + OAuth2 Client
- PostgreSQL (Flyway migrations)
- Redis (token blacklist & activation tokens)
- Caffeine (in-process country cache)
- MapStruct & ModelMapper
- springdoc-openapi (Swagger UI)
- JJWT
- Docker
- Maven

## Additional notes

# Project Metadata

> Portfolio metadata for the Tourist Places backend. YAML frontmatter fields conform to `docs/source/schema.ts` (`ProjectOverview` sibling metadata shape).

> **Replace before production:** `liveDemoUrl` and any `example.com` URLs with your real EC2/ALB domain after deploy.

