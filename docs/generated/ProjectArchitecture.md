# Architecture

## Presentation (clients)

Web and mobile clients for browsing destinations and managing user lists.

### Components

- Travel web app (future frontend)
- Mobile browse app
- Admin content tools

### Responsibilities

- Render paginated place search results
- Store JWT securely (memory or secure storage)

### Technologies

- HTTPS
- REST JSON
- OpenAPI client (optional)

## API gateway & edge

TLS termination and routing to Spring Boot on EC2.

### Components

- AWS Application Load Balancer (recommended)
- Optional Nginx on EC2

### Responsibilities

- Terminate TLS with ACM certificate
- Route traffic to container port 8080
- Optional WAF rate limiting

### Technologies

- AWS ALB
- ACM
- Route 53

## Application layer

Spring Boot 3 monolith in Docker with modular packages under at.backend.tourist.places.

### Components

- modules/auth — JWT, OAuth2, activation
- modules/user — profiles, place lists, user reviews
- modules/country — country catalog
- modules/places — tourist places & categories
- modules/activity — tours and experiences
- modules/review — ratings and comments
- core — config, exceptions, ResponseWrapper

### Responsibilities

- Business rules in service implementations
- Flyway migrations on startup
- Swagger/OpenAPI documentation

### Technologies

- Spring Boot 3.2
- Spring Security
- Spring Data JPA
- MapStruct

## Data & cache

Persistent PostgreSQL on RDS; Redis for tokens; Caffeine for hot country reads.

### Components

- Amazon RDS PostgreSQL
- Redis / ElastiCache — token blacklist
- Caffeine — in-process country cache

### Responsibilities

- ACID storage for places, users, reviews
- Token blacklist and short-lived activation keys
- Reduce DB load on country browse endpoints

### Technologies

- PostgreSQL 15+
- Flyway
- Spring Data Redis
- Caffeine

## Async & integrations

Email delivery and third-party OAuth.

### Components

- SMTP (activation & reset emails)
- Google OAuth2

### Responsibilities

- Send activation and password-reset messages
- Provision Google-authenticated users as VIEWER

### Technologies

- Spring Mail
- Spring OAuth2 Client

## Design patterns

| Pattern | Category | Description |
| --- | --- | --- |
| 🏗️ Layered architecture | Structural | Controllers delegate to services; services use repositories and mappers—classic Spring layering. |
| 🔄 DTO + Mapper | Structural | MapStruct and ModelMapper translate JPA entities to API DTOs, keeping persistence out of controllers. |
| 🏭 Factory — token generators | Creational | TokenFactory selects AccessTokenGenerator, RefreshTokenGenerator, or activation/reset generators by type. |
| 🔐 Filter chain — JWT | Behavioral | JwtAuthenticationFilter runs before UsernamePasswordAuthenticationFilter for stateless Bearer auth. |
| ⚠️ Global exception handler | Behavioral | CustomGlobalExceptionHandler maps validation and domain exceptions to HTTP status codes. |
| 💾 Cache-aside | Behavioral | CountryService methods annotated with @Cacheable backed by Caffeine CacheManager. |

## Scalability strategies

- **Stateless API containers** — Run multiple app containers on EC2 or scale to additional instances behind ALB; JWT avoids server sessions.
- **Amazon RDS PostgreSQL** — Managed database with automated backups, Multi-AZ option, and storage autoscaling for growing place/review data.
- **Redis for shared token state** — ElastiCache or Upstash lets multiple API instances share blacklist and activation token storage.
- **Caffeine for read-heavy country data** — In-process cache reduces RDS queries on public country endpoints until cache invalidation strategy is needed.

## Security strategies

- **JWT Bearer authentication** — Access tokens signed with Base64-decoded HMAC secret; validated on each protected request.
- **Redis token blacklist** — Logout and one-time tokens stored in Redis to prevent replay.
- **Role model (VIEWER/EDITOR/ADMIN)** — Roles embedded in JWT claims; admin routes intended for elevated access—needs @PreAuthorize enforcement.
- **CSRF disabled for REST** — Stateless API disables CSRF—acceptable for Bearer-token clients; not for cookie sessions.
- **Flyway + ddl-auto validate** — Schema changes only via migrations; Hibernate validates entity mapping at startup.

## Cache strategies

| Name | TTL | Coverage | Description |
| --- | --- | --- | --- |
| Caffeine country caches | expireAfterAccess 1 day | Country read endpoints | countryById, allCountries, countriesByContinent, countryByName |
| Redis token store | Per-token configuration | Auth flows and logout | Activation, reset, and blacklist keys via RedisTokenService |
| JPA second-level | N/A | Country module only | Not enabled — rely on Caffeine for explicit cache names only |

## Architecture highlights

### 📦 ResponseWrapper envelope

Consistent JSON shape with success, data, message, and status_code across controllers.

### 📖 OpenAPI-first docs

springdoc generates live schema from Spring MVC controllers and custom operation annotations.

### 🧩 Modular packages

Each domain (country, places, review) is a self-contained module with controller/service/repository.

### ☁️ Cloud-first compose

docker-compose.yml comments out local Postgres; expects RDS connection string in .env.

## Architecture diagram

### Legend

| Type | Label |
| --- | --- |
| client | Client |
| gateway | ALB |
| service | API service |
| database | Database |
| queue | Redis |
| monitoring | SMTP / OAuth |

### Nodes

| ID | Label | Type | Status |
| --- | --- | --- | --- |
| client | Web / mobile clients | client | healthy |
| alb | AWS ALB (TLS) | gateway | healthy |
| api | Tourist Places API (Docker/EC2) | service | healthy |
| rds | RDS PostgreSQL | database | healthy |
| redis | ElastiCache / Redis | queue | healthy |
| smtp | SMTP / SES | monitoring | healthy |
| google | Google OAuth2 | service | healthy |

### Connections

| From | To | Label | Protocol |
| --- | --- | --- | --- |
| client | alb | HTTPS | TLS 1.2+ |
| alb | api | Forward | HTTP:8080 |
| api | rds | JDBC | PostgreSQL |
| api | redis | Tokens | Redis |
| api | smtp | Email | SMTP |
| api | google | OAuth2 | HTTPS |

### Mermaid overview

```mermaid
flowchart LR
    client([Web / mobile clients])
    alb{AWS ALB (TLS)}
    api[Tourist Places API (Docker/EC2)]
    rds[(RDS PostgreSQL)]
    redis[/ElastiCache / Redis/]
    smtp>SMTP / SES]
    google[Google OAuth2]
    client -->|HTTPS| alb
    alb -->|Forward| api
    api -->|JDBC| rds
    api -->|Tokens| redis
    api -->|Email| smtp
    api -->|OAuth2| google
```

## Data flow

### Request flow

1. **Client request** — Client calls REST endpoint; protected routes include Authorization Bearer header.
2. **ALB & Spring Security** — ALB forwards to EC2:8080; JwtAuthenticationFilter validates token and sets SecurityContext.
3. **Controller & service** — Controller validates DTO, delegates to service (e.g. TouristPlaceService.searchTouristPlaces).
4. **Persistence** — JPA repository reads/writes RDS; country reads may hit Caffeine cache.
5. **ResponseWrapper** — JSON response with success flag, data payload, and HTTP-aligned status_code.

### Event flow

1. **User signup** — AuthService generates activation token and queues email via SendingService.
2. **Token in Redis** — Short-lived activation/reset token stored in Redis for validation.
3. **SMTP delivery** — Spring Mail sends HTML template (account-registration-email.html).
4. **Account activation** — User POSTs token to /auth/activate-account/{token}; UserService marks account activated.

## Technical decisions

### Spring Boot monolith vs microservices

**Problem:** Solo/small-team tourism API needs fast iteration and simple AWS ops.

**Solution:** Single Spring Boot JAR in Docker on EC2 with package-per-domain modules.

**Outcome:** One deploy artifact; scale EC2/RDS first before splitting services.

#### Alternatives considered

- Microservices per domain
- Serverless Lambda + API Gateway

### PostgreSQL on RDS

**Problem:** Relational model with FKs between countries, places, reviews, and users.

**Solution:** Flyway-managed schema on Amazon RDS PostgreSQL; H2 only in tests.

**Outcome:** Strong integrity for ratings and place relationships; RDS handles backups.

#### Alternatives considered

- MongoDB
- SQLite in production

### JJWT + Redis blacklist

**Problem:** SPA clients need stateless auth with logout support.

**Solution:** Custom JwtService with Redis-backed blacklist instead of session cookies.

**Outcome:** Works behind ALB; requires Redis availability in AWS.

#### Alternatives considered

- Spring Session JDBC
- OAuth2 resource server only

### Caffeine for countries

**Problem:** Country list endpoints are read-heavy and change infrequently.

**Solution:** In-process Caffeine cache with named caches per query pattern.

**Outcome:** Simple and fast for single-instance EC2; consider Redis cache when horizontally scaling.

#### Alternatives considered

- Redis cache for all reads
- No caching

## Additional notes

# Architecture

> **AWS target:** EC2 runs the Docker container; RDS holds all relational data; Redis/ElastiCache holds auth tokens. No database container in compose.

> **Useful:** Import optional `.env` via `spring.config.import: optional:file:.env[.properties]` in application.yml.

> **Potentially dangerous:** Public GET permitAll exposes admin user listings and all reviews without auth. JwtAuthenticationFilter prints tokens to stdout. OAuth2 success handler response format is broken (`{\"tokens\":\"" + responseDTO + "\"}`). Fix before production.

> **Schema assertion:** Structure matches `ProjectArchitectureModel` in `docs/source/schema.ts`.

