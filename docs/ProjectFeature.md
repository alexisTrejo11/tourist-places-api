---
# ProjectFeature[]
features:
  - id: "auth-security"
    title: "JWT and OAuth2 Authentication"
    description: "Supports account signup/login, token workflows, logout, account activation, reset-password, and OAuth2 entrypoints."
    icon: "ShieldCheck"
    category: "security"
    status: "stable"
    githubExampleUrl: "src/main/java/at/backend/tourist/places/modules/auth/controller/AuthController.java"
    highlights:
      - "JWT-based secured endpoints"
      - "Google OAuth2 integration paths"
      - "Account lifecycle endpoints"
    techStack:
      - "Spring Security"
      - "JJWT"
      - "OAuth2 Client"
    metrics:
      - label: "Auth Endpoints"
        value: "7+"
        trend: "stable"
        icon: "Key"
    codeSnippet:
      language: "java"
      filename: "modules/auth/controller/AuthController.java"
      code: "@RequestMapping(\"/auth\")"

  - id: "core-domain-management"
    title: "Tourist Domain APIs"
    description: "Provides dedicated APIs for countries, tourist places, place categories, activities, reviews, and user place-lists."
    icon: "MapPin"
    category: "api"
    status: "stable"
    githubExampleUrl: "src/main/java/at/backend/tourist/places/modules/places/controller/TouristPlaceController.java"
    highlights:
      - "CRUD by module"
      - "Search endpoint for tourist places"
      - "User-owned place-list operations"
    techStack:
      - "Spring MVC"
      - "Spring Data JPA"
      - "MapStruct"
    metrics:
      - label: "Domain Controllers"
        value: "10+"
        trend: "stable"
        icon: "Layers"
    codeSnippet:
      language: "java"
      filename: "modules/places/controller/TouristPlaceController.java"
      code: "@GetMapping(\"/search\")"

  - id: "caching-performance"
    title: "Dual Caching Strategy"
    description: "Combines Redis (distributed) and Caffeine (in-memory) to improve latency and reduce repetitive database reads."
    icon: "Database"
    category: "performance"
    status: "stable"
    githubExampleUrl: "src/main/java/at/backend/tourist/places/core/config/CacheConfig.java"
    highlights:
      - "Redis integration in application config"
      - "Spring Cache abstraction support"
      - "Cloud-scalable cache pattern"
    techStack:
      - "Spring Cache"
      - "Redis"
      - "Caffeine"
    metrics:
      - label: "Cache Layers"
        value: "2"
        trend: "stable"
        icon: "Zap"
    codeSnippet:
      language: "yaml"
      filename: "src/main/resources/application.yml"
      code: "spring.redis.host: localhost"

  - id: "cloud-ready-delivery"
    title: "Containerized Deployment Baseline"
    description: "Docker Compose orchestrates app + PostgreSQL for reproducible environments and cloud migration preparation."
    icon: "CloudCog"
    category: "devops"
    status: "in-progress"
    githubExampleUrl: "docker-compose.yml"
    highlights:
      - "Service-level environment injection from .env"
      - "Postgres data volume persistence"
      - "Networked container communication"
    techStack:
      - "Docker"
      - "Docker Compose"
      - "PostgreSQL"
    metrics:
      - label: "Cloud Readiness"
        value: "High (final deployment pending)"
        trend: "up"
        icon: "Cloud"
    codeSnippet:
      language: "yaml"
      filename: "docker-compose.yml"
      code: "services: { postgres, app }"
---
# Project Features
Feature implementation is functionally complete for a production backend. Remaining work is primarily around cloud environment rollout (managed services, domain, TLS, CI/CD deployment pipeline).

