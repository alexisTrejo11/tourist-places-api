---
features:
  - id: "jwt-auth-oauth2"
    title: "JWT authentication & Google OAuth2"
    description: "Email/password signup with activation tokens, JWT access/refresh pair, Redis token blacklist on logout, and optional Google social login via Spring OAuth2 Client."
    icon: "shield-lock"
    category: "authentication"
    status: "stable"
    highlights:
      - "POST /auth/signup, /auth/login, /auth/logout"
      - "Account activation and password reset via email tokens"
      - "Roles: VIEWER, EDITOR, ADMIN"
    techStack:
      - "Spring Security"
      - "JJWT"
      - "RedisTokenService"
      - "modules/auth"
    metrics:
      - label: "Access token TTL"
        value: "Configurable (JWT_ACCESS_EXPIRATION ms)"
        trend: "stable"
        icon: "clock"
      - label: "Refresh token TTL"
        value: "Configurable (JWT_REFRESH_EXPIRATION ms)"
        trend: "stable"
        icon: "refresh"
    codeSnippet:
      language: "java"
      filename: "modules/auth/jwt/JwtService.java"
      code: |
        public LoginResponseDTO generateLoginTokens(String email, Long userId, String role) {
            String accessToken = tokenFactory.getTokenGenerator("access").generateToken(email, userId, role);
            String refreshToken = tokenFactory.getTokenGenerator("refresh").generateToken(email, userId, role);
            return new LoginResponseDTO(accessToken, refreshToken);
        }

  - id: "tourist-place-catalog"
    title: "Tourist place catalog & search"
    description: "Paginated search, lookup by country/category, and CRUD for destinations with ratings, opening hours, and price range metadata."
    icon: "map-pin"
    category: "api"
    status: "stable"
    highlights:
      - "GET /v1/api/tourist_places/search with sort and pagination"
      - "Filter by country and place category"
      - "MapStruct mappers for entity ↔ DTO conversion"
    techStack:
      - "Spring Data JPA"
      - "MapStruct"
      - "modules/places"
    metrics:
      - label: "Search pagination"
        value: "Spring Page"
        trend: "stable"
        icon: "list"

  - id: "country-cache"
    title: "Country data with Caffeine cache"
    description: "Country lookups cached in-process with Caffeine (1-day expire-after-access, max 100 entries per cache name)."
    icon: "globe"
    category: "caching"
    status: "stable"
    highlights:
      - "Caches: countryById, allCountries, countriesByContinent, countryByName"
      - "Continent enum constraint in PostgreSQL"
      - "Public GET endpoints for browse-heavy traffic"
    techStack:
      - "Caffeine"
      - "Spring Cache"
      - "modules/country"
    metrics:
      - label: "Cache TTL"
        value: "1 day access"
        trend: "stable"
        icon: "timer"

  - id: "reviews-ratings"
    title: "Reviews & average ratings"
    description: "Users post reviews under /v1/api/user/reviews; system recalculates tourist place average ratings on create/update/delete."
    icon: "star"
    category: "api"
    status: "stable"
    highlights:
      - "User-scoped review CRUD with JWT"
      - "Public read of reviews by place"
      - "Paginated my-reviews endpoint"
    techStack:
      - "modules/review"
      - "modules/user"
    metrics:
      - label: "Rating scale"
        value: "Double precision"
        trend: "stable"
        icon: "star"

  - id: "user-place-lists"
    title: "User-curated place lists"
    description: "Authenticated users create named lists and add/remove tourist places for trip planning."
    icon: "bookmark"
    category: "api"
    status: "stable"
    highlights:
      - "POST /v1/api/user/place-lists"
      - "add-place and remove-place actions"
      - "Many-to-many via place_list_places join table"
    techStack:
      - "modules/user"
      - "modules/places"

  - id: "activities"
    title: "Activities at destinations"
    description: "Tours and experiences linked to tourist places with price, duration, and description."
    icon: "ticket"
    category: "api"
    status: "stable"
    highlights:
      - "GET /v1/api/activities/tourist_place/{place_id}"
      - "Admin create/delete (authenticated POST/DELETE)"
    techStack:
      - "modules/activity"

  - id: "flyway-postgres"
    title: "Flyway migrations on PostgreSQL"
    description: "Versioned SQL schema (V1__create_tables.sql) applied on startup; JPA ddl-auto validate ensures model alignment with RDS."
    icon: "database"
    category: "database"
    status: "stable"
    highlights:
      - "8 core tables with FK constraints"
      - "baseline-on-migrate enabled"
      - "Designed for Amazon RDS PostgreSQL"
    techStack:
      - "Flyway 9.22"
      - "PostgreSQL"
      - "Hibernate"
    metrics:
      - label: "Migration files"
        value: "1 (V1)"
        trend: "stable"
        icon: "file"

  - id: "redis-tokens"
    title: "Redis token storage & blacklist"
    description: "Activation and reset tokens plus JWT blacklist stored in Redis via StringRedisTemplate."
    icon: "key"
    category: "security"
    status: "stable"
    highlights:
      - "Logout invalidates access token in Redis"
      - "6-char custom activation tokens supported"
      - "Use ElastiCache or Upstash on AWS"
    techStack:
      - "Spring Data Redis"
      - "RedisTokenService"
    codeSnippet:
      language: "java"
      filename: "modules/auth/jwt/JwtService.java"
      code: |
        private boolean validateJWTToken(String token) {
            if (redisTokenService.isTokenBlacklisted(token)) {
                return false;
            }
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        }

  - id: "openapi-swagger"
    title: "OpenAPI & standardized responses"
    description: "springdoc-openapi exposes Swagger UI and a uniform ResponseWrapper envelope for success and error payloads."
    icon: "book"
    category: "api"
    status: "stable"
    highlights:
      - "/swagger-ui.html and /v3/api-docs"
      - "Custom OpenAPI annotations per controller operation"
      - "CustomGlobalExceptionHandler for validation errors"
    techStack:
      - "springdoc-openapi 2.2.0"
      - "ResponseWrapper"
    metrics:
      - label: "OpenAPI path"
        value: "/v3/api-docs"
        trend: "stable"
        icon: "tag"

  - id: "docker-aws"
    title: "Docker deploy for AWS EC2"
    description: "Multi-stage Maven Docker image; docker-compose runs app container pointing to external RDS and Redis."
    icon: "docker"
    category: "integration"
    status: "stable"
    highlights:
      - "Port 8080 exposed on EC2"
      - "SPRING_DATASOURCE_* overrides from .env"
      - "Local Postgres service commented out for cloud-first deploy"
    techStack:
      - "Docker"
      - "Maven"
      - "docker-compose.yml"
---

# Project Features

> **Stable:** Core browse and auth flows (countries, places, user lists, reviews).

> **Useful:** Copy `.env.example` to `.env` and point `DB_URL` at RDS before `docker compose up`.

> **Potentially dangerous:** No application-level rate limiting is enabled (handler code is commented out). Rely on ALB/WAF or add Resilience4j before public launch. OAuth2 success handler writes malformed JSON—fix before exposing Google login in production.

> **Schema assertion:** Feature objects match `ProjectFeature` in `docs/source/schema.ts`.
