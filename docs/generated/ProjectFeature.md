# Project Features

## JWT authentication & Google OAuth2

Email/password signup with activation tokens, JWT access/refresh pair, Redis token blacklist on logout, and optional Google social login via Spring OAuth2 Client.

| Property | Value |
| --- | --- |
| ID | jwt-auth-oauth2 |
| Category | authentication |
| Status | stable |
| Icon | shield-lock |

### Highlights

- POST /auth/signup, /auth/login, /auth/logout
- Account activation and password reset via email tokens
- Roles: VIEWER, EDITOR, ADMIN

### Tech stack

- Spring Security
- JJWT
- RedisTokenService
- modules/auth

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| Access token TTL | Configurable (JWT_ACCESS_EXPIRATION ms) | stable |
| Refresh token TTL | Configurable (JWT_REFRESH_EXPIRATION ms) | stable |

### Code snippet

_modules/auth/jwt/JwtService.java_

```java
public LoginResponseDTO generateLoginTokens(String email, Long userId, String role) {
    String accessToken = tokenFactory.getTokenGenerator("access").generateToken(email, userId, role);
    String refreshToken = tokenFactory.getTokenGenerator("refresh").generateToken(email, userId, role);
    return new LoginResponseDTO(accessToken, refreshToken);
}
```

## Tourist place catalog & search

Paginated search, lookup by country/category, and CRUD for destinations with ratings, opening hours, and price range metadata.

| Property | Value |
| --- | --- |
| ID | tourist-place-catalog |
| Category | api |
| Status | stable |
| Icon | map-pin |

### Highlights

- GET /v1/api/tourist_places/search with sort and pagination
- Filter by country and place category
- MapStruct mappers for entity ↔ DTO conversion

### Tech stack

- Spring Data JPA
- MapStruct
- modules/places

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| Search pagination | Spring Page | stable |

## Country data with Caffeine cache

Country lookups cached in-process with Caffeine (1-day expire-after-access, max 100 entries per cache name).

| Property | Value |
| --- | --- |
| ID | country-cache |
| Category | caching |
| Status | stable |
| Icon | globe |

### Highlights

- Caches: countryById, allCountries, countriesByContinent, countryByName
- Continent enum constraint in PostgreSQL
- Public GET endpoints for browse-heavy traffic

### Tech stack

- Caffeine
- Spring Cache
- modules/country

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| Cache TTL | 1 day access | stable |

## Reviews & average ratings

Users post reviews under /v1/api/user/reviews; system recalculates tourist place average ratings on create/update/delete.

| Property | Value |
| --- | --- |
| ID | reviews-ratings |
| Category | api |
| Status | stable |
| Icon | star |

### Highlights

- User-scoped review CRUD with JWT
- Public read of reviews by place
- Paginated my-reviews endpoint

### Tech stack

- modules/review
- modules/user

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| Rating scale | Double precision | stable |

## User-curated place lists

Authenticated users create named lists and add/remove tourist places for trip planning.

| Property | Value |
| --- | --- |
| ID | user-place-lists |
| Category | api |
| Status | stable |
| Icon | bookmark |

### Highlights

- POST /v1/api/user/place-lists
- add-place and remove-place actions
- Many-to-many via place_list_places join table

### Tech stack

- modules/user
- modules/places

## Activities at destinations

Tours and experiences linked to tourist places with price, duration, and description.

| Property | Value |
| --- | --- |
| ID | activities |
| Category | api |
| Status | stable |
| Icon | ticket |

### Highlights

- GET /v1/api/activities/tourist_place/{place_id}
- Admin create/delete (authenticated POST/DELETE)

### Tech stack

- modules/activity

## Flyway migrations on PostgreSQL

Versioned SQL schema (V1__create_tables.sql) applied on startup; JPA ddl-auto validate ensures model alignment with RDS.

| Property | Value |
| --- | --- |
| ID | flyway-postgres |
| Category | database |
| Status | stable |
| Icon | database |

### Highlights

- 8 core tables with FK constraints
- baseline-on-migrate enabled
- Designed for Amazon RDS PostgreSQL

### Tech stack

- Flyway 9.22
- PostgreSQL
- Hibernate

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| Migration files | 1 (V1) | stable |

## Redis token storage & blacklist

Activation and reset tokens plus JWT blacklist stored in Redis via StringRedisTemplate.

| Property | Value |
| --- | --- |
| ID | redis-tokens |
| Category | security |
| Status | stable |
| Icon | key |

### Highlights

- Logout invalidates access token in Redis
- 6-char custom activation tokens supported
- Use ElastiCache or Upstash on AWS

### Tech stack

- Spring Data Redis
- RedisTokenService

### Code snippet

_modules/auth/jwt/JwtService.java_

```java
private boolean validateJWTToken(String token) {
    if (redisTokenService.isTokenBlacklisted(token)) {
        return false;
    }
    Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
    return true;
}
```

## OpenAPI & standardized responses

springdoc-openapi exposes Swagger UI and a uniform ResponseWrapper envelope for success and error payloads.

| Property | Value |
| --- | --- |
| ID | openapi-swagger |
| Category | api |
| Status | stable |
| Icon | book |

### Highlights

- /swagger-ui.html and /v3/api-docs
- Custom OpenAPI annotations per controller operation
- CustomGlobalExceptionHandler for validation errors

### Tech stack

- springdoc-openapi 2.2.0
- ResponseWrapper

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| OpenAPI path | /v3/api-docs | stable |

## Docker deploy for AWS EC2

Multi-stage Maven Docker image; docker-compose runs app container pointing to external RDS and Redis.

| Property | Value |
| --- | --- |
| ID | docker-aws |
| Category | integration |
| Status | stable |
| Icon | docker |

### Highlights

- Port 8080 exposed on EC2
- SPRING_DATASOURCE_* overrides from .env
- Local Postgres service commented out for cloud-first deploy

### Tech stack

- Docker
- Maven
- docker-compose.yml

## Additional notes

# Project Features

> **Stable:** Core browse and auth flows (countries, places, user lists, reviews).

> **Useful:** Copy `.env.example` to `.env` and point `DB_URL` at RDS before `docker compose up`.

> **Potentially dangerous:** No application-level rate limiting is enabled (handler code is commented out). Rely on ALB/WAF or add Resilience4j before public launch. OAuth2 success handler writes malformed JSON—fix before exposing Google login in production.

> **Schema assertion:** Feature objects match `ProjectFeature` in `docs/source/schema.ts`.

