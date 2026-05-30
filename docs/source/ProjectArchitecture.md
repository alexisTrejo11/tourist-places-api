---
layers:
  - name: "Presentation (clients)"
    description: "Web and mobile clients for browsing destinations and managing user lists."
    color: "#6366F1"
    expanded: true
    components:
      - "Travel web app (future frontend)"
      - "Mobile browse app"
      - "Admin content tools"
    responsibilities:
      - "Render paginated place search results"
      - "Store JWT securely (memory or secure storage)"
    technologies:
      - "HTTPS"
      - "REST JSON"
      - "OpenAPI client (optional)"

  - name: "API gateway & edge"
    description: "TLS termination and routing to Spring Boot on EC2."
    color: "#10B981"
    expanded: false
    components:
      - "AWS Application Load Balancer (recommended)"
      - "Optional Nginx on EC2"
    responsibilities:
      - "Terminate TLS with ACM certificate"
      - "Route traffic to container port 8080"
      - "Optional WAF rate limiting"
    technologies:
      - "AWS ALB"
      - "ACM"
      - "Route 53"

  - name: "Application layer"
    description: "Spring Boot 3 monolith in Docker with modular packages under at.backend.tourist.places."
    color: "#F59E0B"
    expanded: true
    components:
      - "modules/auth — JWT, OAuth2, activation"
      - "modules/user — profiles, place lists, user reviews"
      - "modules/country — country catalog"
      - "modules/places — tourist places & categories"
      - "modules/activity — tours and experiences"
      - "modules/review — ratings and comments"
      - "core — config, exceptions, ResponseWrapper"
    responsibilities:
      - "Business rules in service implementations"
      - "Flyway migrations on startup"
      - "Swagger/OpenAPI documentation"
    technologies:
      - "Spring Boot 3.2"
      - "Spring Security"
      - "Spring Data JPA"
      - "MapStruct"

  - name: "Data & cache"
    description: "Persistent PostgreSQL on RDS; Redis for tokens; Caffeine for hot country reads."
    color: "#EF4444"
    expanded: true
    components:
      - "Amazon RDS PostgreSQL"
      - "Redis / ElastiCache — token blacklist"
      - "Caffeine — in-process country cache"
    responsibilities:
      - "ACID storage for places, users, reviews"
      - "Token blacklist and short-lived activation keys"
      - "Reduce DB load on country browse endpoints"
    technologies:
      - "PostgreSQL 15+"
      - "Flyway"
      - "Spring Data Redis"
      - "Caffeine"

  - name: "Async & integrations"
    description: "Email delivery and third-party OAuth."
    color: "#8B5CF6"
    expanded: false
    components:
      - "SMTP (activation & reset emails)"
      - "Google OAuth2"
    responsibilities:
      - "Send activation and password-reset messages"
      - "Provision Google-authenticated users as VIEWER"
    technologies:
      - "Spring Mail"
      - "Spring OAuth2 Client"

designPatterns:
  - title: "Layered architecture"
    emoji: "🏗️"
    description: "Controllers delegate to services; services use repositories and mappers—classic Spring layering."
    category: "Structural"
    badge: "Core"
  - title: "DTO + Mapper"
    emoji: "🔄"
    description: "MapStruct and ModelMapper translate JPA entities to API DTOs, keeping persistence out of controllers."
    category: "Structural"
    badge: "MapStruct"
  - title: "Factory — token generators"
    emoji: "🏭"
    description: "TokenFactory selects AccessTokenGenerator, RefreshTokenGenerator, or activation/reset generators by type."
    category: "Creational"
    badge: "Auth"
  - title: "Filter chain — JWT"
    emoji: "🔐"
    description: "JwtAuthenticationFilter runs before UsernamePasswordAuthenticationFilter for stateless Bearer auth."
    category: "Behavioral"
    badge: "Security"
  - title: "Global exception handler"
    emoji: "⚠️"
    description: "CustomGlobalExceptionHandler maps validation and domain exceptions to HTTP status codes."
    category: "Behavioral"
    badge: "Core"
  - title: "Cache-aside"
    emoji: "💾"
    description: "CountryService methods annotated with @Cacheable backed by Caffeine CacheManager."
    category: "Behavioral"
    badge: "Performance"

scalabilityStrategies:
  - title: "Stateless API containers"
    description: "Run multiple app containers on EC2 or scale to additional instances behind ALB; JWT avoids server sessions."
  - title: "Amazon RDS PostgreSQL"
    description: "Managed database with automated backups, Multi-AZ option, and storage autoscaling for growing place/review data."
  - title: "Redis for shared token state"
    description: "ElastiCache or Upstash lets multiple API instances share blacklist and activation token storage."
  - title: "Caffeine for read-heavy country data"
    description: "In-process cache reduces RDS queries on public country endpoints until cache invalidation strategy is needed."

securityStrategies:
  - title: "JWT Bearer authentication"
    description: "Access tokens signed with Base64-decoded HMAC secret; validated on each protected request."
  - title: "Redis token blacklist"
    description: "Logout and one-time tokens stored in Redis to prevent replay."
  - title: "Role model (VIEWER/EDITOR/ADMIN)"
    description: "Roles embedded in JWT claims; admin routes intended for elevated access—needs @PreAuthorize enforcement."
  - title: "CSRF disabled for REST"
    description: "Stateless API disables CSRF—acceptable for Bearer-token clients; not for cookie sessions."
  - title: "Flyway + ddl-auto validate"
    description: "Schema changes only via migrations; Hibernate validates entity mapping at startup."

cacheStrategies:
  - name: "Caffeine country caches"
    description: "countryById, allCountries, countriesByContinent, countryByName"
    ttl: "expireAfterAccess 1 day"
    coverage: "Country read endpoints"
  - name: "Redis token store"
    description: "Activation, reset, and blacklist keys via RedisTokenService"
    ttl: "Per-token configuration"
    coverage: "Auth flows and logout"
  - name: "JPA second-level"
    description: "Not enabled — rely on Caffeine for explicit cache names only"
    ttl: "N/A"
    coverage: "Country module only"

architectureFeatures:
  - title: "ResponseWrapper envelope"
    emoji: "📦"
    description: "Consistent JSON shape with success, data, message, and status_code across controllers."
  - title: "OpenAPI-first docs"
    emoji: "📖"
    description: "springdoc generates live schema from Spring MVC controllers and custom operation annotations."
  - title: "Modular packages"
    emoji: "🧩"
    description: "Each domain (country, places, review) is a self-contained module with controller/service/repository."
  - title: "Cloud-first compose"
    emoji: "☁️"
    description: "docker-compose.yml comments out local Postgres; expects RDS connection string in .env."

architectureDiagram:
  legendItems:
    - type: "client"
      label: "Client"
      color: "#6366F1"
      icon: "monitor"
    - type: "gateway"
      label: "ALB"
      color: "#10B981"
      icon: "shield"
    - type: "service"
      label: "API service"
      color: "#F59E0B"
      icon: "server"
    - type: "database"
      label: "Database"
      color: "#EF4444"
      icon: "database"
    - type: "queue"
      label: "Redis"
      color: "#8B5CF6"
      icon: "layers"
    - type: "monitoring"
      label: "SMTP / OAuth"
      color: "#64748B"
      icon: "mail"

  nodes:
    - id: "client"
      label: "Web / mobile clients"
      type: "client"
      x: 80
      y: 120
      connections: ["alb"]
      status: "healthy"
      traffic: 100
    - id: "alb"
      label: "AWS ALB (TLS)"
      type: "gateway"
      x: 280
      y: 120
      connections: ["api"]
      status: "healthy"
      traffic: 100
    - id: "api"
      label: "Tourist Places API (Docker/EC2)"
      type: "service"
      x: 480
      y: 120
      connections: ["rds", "redis", "smtp", "google"]
      status: "healthy"
      traffic: 85
    - id: "rds"
      label: "RDS PostgreSQL"
      type: "database"
      x: 680
      y: 60
      connections: []
      status: "healthy"
      traffic: 60
    - id: "redis"
      label: "ElastiCache / Redis"
      type: "queue"
      x: 680
      y: 180
      connections: []
      status: "healthy"
      traffic: 40
    - id: "smtp"
      label: "SMTP / SES"
      type: "monitoring"
      x: 480
      y: 260
      connections: []
      status: "healthy"
      traffic: 10
    - id: "google"
      label: "Google OAuth2"
      type: "service"
      x: 280
      y: 260
      connections: []
      status: "healthy"
      traffic: 5

  connections:
    - id: "c1"
      from: "client"
      to: "alb"
      label: "HTTPS"
      protocol: "TLS 1.2+"
      isActive: true
    - id: "c2"
      from: "alb"
      to: "api"
      label: "Forward"
      protocol: "HTTP:8080"
      isActive: true
    - id: "c3"
      from: "api"
      to: "rds"
      label: "JDBC"
      protocol: "PostgreSQL"
      isActive: true
    - id: "c4"
      from: "api"
      to: "redis"
      label: "Tokens"
      protocol: "Redis"
      isActive: true
    - id: "c5"
      from: "api"
      to: "smtp"
      label: "Email"
      protocol: "SMTP"
      isActive: true
    - id: "c6"
      from: "api"
      to: "google"
      label: "OAuth2"
      protocol: "HTTPS"
      isActive: true

dataFlow:
  requestFlow:
    - number: 1
      title: "Client request"
      description: "Client calls REST endpoint; protected routes include Authorization Bearer header."
      icon: "send"
    - number: 2
      title: "ALB & Spring Security"
      description: "ALB forwards to EC2:8080; JwtAuthenticationFilter validates token and sets SecurityContext."
      icon: "filter"
    - number: 3
      title: "Controller & service"
      description: "Controller validates DTO, delegates to service (e.g. TouristPlaceService.searchTouristPlaces)."
      icon: "cog"
    - number: 4
      title: "Persistence"
      description: "JPA repository reads/writes RDS; country reads may hit Caffeine cache."
      icon: "database"
    - number: 5
      title: "ResponseWrapper"
      description: "JSON response with success flag, data payload, and HTTP-aligned status_code."
      icon: "reply"

  eventFlow:
    - number: 1
      title: "User signup"
      description: "AuthService generates activation token and queues email via SendingService."
      icon: "user-plus"
    - number: 2
      title: "Token in Redis"
      description: "Short-lived activation/reset token stored in Redis for validation."
      icon: "key"
    - number: 3
      title: "SMTP delivery"
      description: "Spring Mail sends HTML template (account-registration-email.html)."
      icon: "mail"
    - number: 4
      title: "Account activation"
      description: "User POSTs token to /auth/activate-account/{token}; UserService marks account activated."
      icon: "check"

techDecisions:
  decisions:
    - title: "Spring Boot monolith vs microservices"
      problem: "Solo/small-team tourism API needs fast iteration and simple AWS ops."
      solution: "Single Spring Boot JAR in Docker on EC2 with package-per-domain modules."
      alternatives:
        - "Microservices per domain"
        - "Serverless Lambda + API Gateway"
      outcome: "One deploy artifact; scale EC2/RDS first before splitting services."
      icon: "layers"
    - title: "PostgreSQL on RDS"
      problem: "Relational model with FKs between countries, places, reviews, and users."
      solution: "Flyway-managed schema on Amazon RDS PostgreSQL; H2 only in tests."
      alternatives:
        - "MongoDB"
        - "SQLite in production"
      outcome: "Strong integrity for ratings and place relationships; RDS handles backups."
      icon: "database"
    - title: "JJWT + Redis blacklist"
      problem: "SPA clients need stateless auth with logout support."
      solution: "Custom JwtService with Redis-backed blacklist instead of session cookies."
      alternatives:
        - "Spring Session JDBC"
        - "OAuth2 resource server only"
      outcome: "Works behind ALB; requires Redis availability in AWS."
      icon: "key"
    - title: "Caffeine for countries"
      problem: "Country list endpoints are read-heavy and change infrequently."
      solution: "In-process Caffeine cache with named caches per query pattern."
      alternatives:
        - "Redis cache for all reads"
        - "No caching"
      outcome: "Simple and fast for single-instance EC2; consider Redis cache when horizontally scaling."
      icon: "zap"
---

# Architecture

> **AWS target:** EC2 runs the Docker container; RDS holds all relational data; Redis/ElastiCache holds auth tokens. No database container in compose.

> **Useful:** Import optional `.env` via `spring.config.import: optional:file:.env[.properties]` in application.yml.

> **Potentially dangerous:** Public GET permitAll exposes admin user listings and all reviews without auth. JwtAuthenticationFilter prints tokens to stdout. OAuth2 success handler response format is broken (`{\"tokens\":\"" + responseDTO + "\"}`). Fix before production.

> **Schema assertion:** Structure matches `ProjectArchitectureModel` in `docs/source/schema.ts`.
