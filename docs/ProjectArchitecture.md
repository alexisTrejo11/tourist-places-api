---
# ArchitectureLayer[]
layers:
  - name: "Presentation"
    description: "REST controllers expose domain endpoints and validate request payloads."
    color: "#2563EB"
    expanded: true
    components:
      - "AuthController"
      - "CountryController"
      - "TouristPlaceController"
    responsibilities:
      - "Input validation"
      - "HTTP response contracts"
      - "Endpoint routing"
    technologies:
      - "Spring MVC"
      - "Bean Validation"
  - name: "Security"
    description: "Authentication/authorization flow with JWT and OAuth2 support."
    color: "#DC2626"
    expanded: true
    components:
      - "JwtAuthenticationFilter"
      - "SecurityConfig"
      - "OAuth2Controller"
    responsibilities:
      - "Token verification"
      - "Role-based access"
      - "OAuth2 login flow"
    technologies:
      - "Spring Security"
      - "OAuth2 Client"
      - "JJWT"
  - name: "Application Service"
    description: "Business logic per module coordinates repositories, mappers, and cache usage."
    color: "#059669"
    expanded: true
    components:
      - "AuthServiceImpl"
      - "TouristPlaceServiceImpl"
      - "ReviewServiceImpl"
    responsibilities:
      - "Use-case orchestration"
      - "Transactional boundaries"
      - "Cache-aware operations"
    technologies:
      - "Spring Service"
      - "Spring Cache"
  - name: "Persistence"
    description: "Entity and repository access over PostgreSQL with migration control."
    color: "#7C3AED"
    expanded: true
    components:
      - "JPA Repositories"
      - "Flyway Migrations"
      - "Entity Models"
    responsibilities:
      - "Data read/write"
      - "Schema evolution"
      - "Query execution"
    technologies:
      - "Spring Data JPA"
      - "PostgreSQL"
      - "Flyway"

# DesignPattern[]
designPatterns:
  - title: "Layered Architecture"
    emoji: "🏗️"
    description: "Separates concerns between controllers, services, and repositories."
    category: "architecture"
    badge: "core"
    githubExampleUrl: "src/main/java/at/backend/tourist/places/modules"
  - title: "Repository Pattern"
    emoji: "🗃️"
    description: "Abstracts persistence logic through Spring Data repository interfaces."
    category: "data"
    badge: "stable"
    githubExampleUrl: "src/main/java/at/backend/tourist/places/modules/*/repository"
  - title: "DTO Mapper Pattern"
    emoji: "🔄"
    description: "Uses MapStruct and model mappers to decouple API payloads from entities."
    category: "api"
    badge: "stable"
    githubExampleUrl: "src/main/java/at/backend/tourist/places/modules/*/auto_mappers"

# StrategyItem[] - Scalability
scalabilityStrategies:
  - title: "Stateless API design"
    description: "JWT authentication enables horizontal scaling of app instances."
  - title: "Distributed cache support"
    description: "Redis-backed caching can be shared by multiple replicas in cloud."
  - title: "Containerized runtime"
    description: "Dockerized service is portable across cloud container platforms."

# StrategyItem[] - Security
securityStrategies:
  - title: "Token-based security"
    description: "Access control enforced via JWT and security filter chain."
  - title: "Role-restricted endpoints"
    description: "Sensitive routes protected through role checks and secured mappings."
  - title: "Credential-safe storage"
    description: "Passwords are stored hashed, never in plain text."

# CacheStrategy[]
cacheStrategies:
  - name: "Redis cache"
    description: "Distributed cache for shared high-value reads and token/session related data."
    ttl: "Configured by cache manager / TBD exact per-key TTL"
    coverage: "Cross-instance cache"
  - name: "Caffeine cache"
    description: "In-memory cache for fast local responses."
    ttl: "Configured by cache manager / TBD exact per-key TTL"
    coverage: "Single-instance hot data"

# ArchitectureFeature[]
architectureFeatures:
  - title: "Domain Modules"
    emoji: "🧩"
    description: "Auth, user, places, country, activity, and review modules keep the codebase maintainable."
  - title: "Cross-cutting Error Handling"
    emoji: "🛡️"
    description: "Custom global exception handler centralizes API error responses."
  - title: "Documentation-first API"
    emoji: "📘"
    description: "OpenAPI annotations and Swagger UI provide discoverable endpoints."

# ArchitectureDiagramModel
architectureDiagram:
  legendItems:
    - type: "client"
      label: "Client"
      color: "#0EA5E9"
      icon: "Monitor"
    - type: "service"
      label: "API Service"
      color: "#22C55E"
      icon: "Server"
    - type: "database"
      label: "Database"
      color: "#F59E0B"
      icon: "Database"
    - type: "cache"
      label: "Cache"
      color: "#EF4444"
      icon: "Zap"
  nodes:
    - id: "client"
      label: "Web/Mobile Client"
      type: "client"
      x: 10
      y: 20
      connections:
        - "api"
      status: "healthy"
      traffic: 85
    - id: "api"
      label: "Spring Boot API"
      type: "service"
      x: 40
      y: 20
      connections:
        - "postgres"
        - "redis"
      status: "healthy"
      traffic: 80
    - id: "postgres"
      label: "PostgreSQL"
      type: "database"
      x: 70
      y: 10
      connections:
        - "api"
      status: "healthy"
      traffic: 55
    - id: "redis"
      label: "Redis"
      type: "cache"
      x: 70
      y: 30
      connections:
        - "api"
      status: "healthy"
      traffic: 45
  connections:
    - id: "c1"
      from: "client"
      to: "api"
      label: "HTTPS REST"
      protocol: "HTTP/JSON"
      isActive: true
    - id: "c2"
      from: "api"
      to: "postgres"
      label: "JPA Queries"
      protocol: "TCP"
      isActive: true
    - id: "c3"
      from: "api"
      to: "redis"
      label: "Cache Read/Write"
      protocol: "TCP"
      isActive: true

# DataFlowModel
dataFlow:
  requestFlow:
    - number: 1
      title: "Incoming API request"
      description: "Client sends request to REST endpoint."
      icon: "ArrowRight"
    - number: 2
      title: "Security validation"
      description: "JWT/OAuth2 identity and permissions are validated."
      icon: "Shield"
    - number: 3
      title: "Service execution"
      description: "Business logic runs with cache check before DB query."
      icon: "Cog"
    - number: 4
      title: "Mapped response"
      description: "Entity data is mapped to DTO and returned as JSON."
      icon: "FileJson"
  eventFlow:
    - number: 1
      title: "Schema migration"
      description: "Flyway applies migrations during startup."
      icon: "DatabaseZap"
    - number: 2
      title: "Email events"
      description: "Account lifecycle events trigger email templates."
      icon: "Mail"

# TechDecisionsModel
techDecisions:
  decisions:
    - title: "Spring Boot modular monolith"
      problem: "Need maintainable structure without distributed-system overhead too early."
      solution: "Implement clear modules in one deployable backend service."
      outcome: "High development speed and straightforward cloud deployment path."
      icon: "Package"
      alternatives:
        - "Microservices (rejected for current scope)"
    - title: "Dual cache (Redis + Caffeine)"
      problem: "Need both low-latency reads and shared cache behavior."
      solution: "Combine local in-memory cache with distributed Redis."
      outcome: "Balanced performance and scale-readiness."
      icon: "Zap"
      alternatives:
        - "Redis-only"
        - "Caffeine-only"
---
# Architecture
The architecture is already cloud-aligned: stateless API behavior, migration-based database lifecycle, and cache separation are in place. Remaining cloud work is mostly operational (managed services, secrets, networking, CI/CD rollout).

