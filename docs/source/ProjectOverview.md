---
problemStatement:
  problemTitle: "Scattered travel data with no unified API"
  problemDescription: "Travel apps and content sites need a single backend to browse countries, categorize destinations, read reviews, and let users save personal place lists. Without a structured API, frontends duplicate validation, auth, and rating logic."
  problemList:
    - "No shared catalog of countries, tourist places, categories, and activities"
    - "User-generated reviews and ratings stored inconsistently across clients"
    - "Account activation, password reset, and OAuth flows reimplemented per app"
    - "Hard to deploy consistently across environments without container + migration strategy"
    - "Public browse vs authenticated write operations need clear security boundaries"

solution:
  solutionTitle: "Modular Spring Boot REST API for tourist content"
  solutionList:
    - title: "Domain modules with clear boundaries"
      description: "Separate packages for auth, users, countries, places, activities, and reviews—each with controllers, services, DTOs, and repositories."
    - title: "JWT + Google OAuth2 authentication"
      description: "Email/password signup with activation tokens, refresh tokens, Redis-backed logout blacklist, and optional Google social login."
    - title: "Rich place discovery"
      description: "Search, filter by country/category, paginated listings, and user-curated place lists for trip planning."
    - title: "Schema-managed PostgreSQL"
      description: "Flyway migrations version the relational model; JPA validates against RDS in production."
    - title: "AWS-ready container deploy"
      description: "Docker image on EC2 connects to external RDS PostgreSQL and Redis/ElastiCache—no bundled database in compose."

keyMetrics:
  metricsTitle: "Platform snapshot"
  metricsList:
    - "6 domain modules (auth, user, country, places, activity, review)"
    - "REST API under v1/api/* and /auth/*"
    - "158 Java source files in src/main/java"
    - "18 controller/service test classes"
    - "OpenAPI at /swagger-ui.html and /v3/api-docs"
    - "Flyway V1 migration with 8 core tables"

links:
  github: "https://github.com/alexisTrejo11/tourist-places-api"
  demo: "https://api.tourist-places.example.com/swagger-ui.html"
  documentation: "https://api.tourist-places.example.com/swagger-ui.html"
  dockerHub: "https://hub.docker.com/r/alexistrejo11/tourist-places-api"

mediaGallery:
  title: "Tourist Places API — product views"
  description: "Portfolio screenshots and diagrams. Replace placeholder URLs with Swagger UI captures and architecture diagrams from your AWS deployment."
  items:
    - type: "image"
      url: "https://placehold.co/1200x630/1E3A5F/ffffff?text=Tourist+Places+API"
      thumbnail: "https://placehold.co/400x210/1E3A5F/ffffff?text=Tourist+Places"
      title: "API cover"
      description: "Tourist Places REST API for destination discovery and user lists"
      alt: "Tourist Places API branding placeholder"
      category: "screenshot"
    - type: "image"
      url: "https://placehold.co/1200x800/2563EB/ffffff?text=Swagger+OpenAPI"
      thumbnail: "https://placehold.co/400x267/2563EB/ffffff?text=OpenAPI"
      title: "OpenAPI documentation"
      description: "Interactive API schema generated with springdoc-openapi"
      alt: "OpenAPI Swagger UI placeholder"
      category: "demo"

mediaItems:
  - type: "image"
    url: "https://placehold.co/800x500/059669/ffffff?text=AWS+Architecture"
    thumbnail: "https://placehold.co/320x200/059669/ffffff?text=AWS"
    title: "AWS deployment architecture"
    description: "Spring Boot container on EC2 with RDS PostgreSQL and Redis for sessions/tokens"
    alt: "AWS EC2 RDS architecture placeholder"
    category: "architecture"
  - type: "image"
    url: "https://placehold.co/800x500/F97316/ffffff?text=Docker+EC2"
    thumbnail: "https://placehold.co/320x200/F97316/ffffff?text=Docker"
    title: "Docker on EC2"
    description: "docker-compose builds app service; database and cache are cloud-managed"
    alt: "Docker EC2 deployment placeholder"
    category: "diagram"

metrics:
  - label: "Domain modules"
    value: "6"
    description: "auth, user, country, places, activity, review"
  - label: "API version prefix"
    value: "v1"
    description: "Primary paths under /v1/api/"
  - label: "Java runtime"
    value: "17"
    description: "Eclipse Temurin in Docker multi-stage Maven build"
  - label: "Auth"
    value: "JWT + OAuth2"
    description: "Bearer tokens with Redis blacklist; Google OAuth2 optional"
---

# Overview

> **Audience:** Developers building travel/tourism frontends or portfolio consumers reading structured docs from `schema.ts`.

> **Useful:** All GET routes are public for browsing; POST/PUT/DELETE require a valid JWT. User-specific routes live under `/v1/api/user/*`.

> **Potentially dangerous:** Swagger annotations document ADMIN-only mutations, but `SecurityConfig` permits all GET requests without authentication—including admin list endpoints like `/v1/api/users/admin/all`. Harden with method-level `@PreAuthorize` before production. JWT filter also logs tokens to stdout—disable in prod.

> **Schema assertion:** Frontmatter keys match `ProjectOverview` in `docs/source/schema.ts`. Regenerate readable docs with `python docs/yaml_to_markdown.py`.
