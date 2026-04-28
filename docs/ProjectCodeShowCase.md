---
codeExamples:
  - id: "jwt-security-filter"
    title: "JWT authentication filter pipeline"
    description: "Demonstrates how JWT tokens are processed and validated before controller access."
    category: "security"
    duration: "5 min"
    views: 0
    tags:
      - "spring-security"
      - "jwt"
      - "filter-chain"
    files:
      - name: "JwtAuthenticationFilter.java"
        path: "src/main/java/at/backend/tourist/places/modules/auth/jwt/JwtAuthenticationFilter.java"
        language: "java"
        content: "TBD_SNIPPET_EXTRACT"
        highlighted: true
        explanation: "Key request interception point for bearer token extraction and security context setup."

  - id: "place-search-controller"
    title: "Tourist place search endpoint"
    description: "Shows endpoint design for filtered tourist place lookup."
    category: "api"
    duration: "3 min"
    views: 0
    tags:
      - "rest"
      - "search"
      - "controller"
    files:
      - name: "TouristPlaceController.java"
        path: "src/main/java/at/backend/tourist/places/modules/places/controller/TouristPlaceController.java"
        language: "java"
        content: "TBD_SNIPPET_EXTRACT"
        highlighted: true
        explanation: "Entry point for place search use-case with service delegation."

  - id: "cache-configuration"
    title: "Cache configuration for cloud-ready performance"
    description: "Illustrates how Redis and local cache are configured in the project."
    category: "performance"
    duration: "4 min"
    views: 0
    tags:
      - "redis"
      - "caffeine"
      - "spring-cache"
    files:
      - name: "CacheConfig.java"
        path: "src/main/java/at/backend/tourist/places/core/config/CacheConfig.java"
        language: "java"
        content: "TBD_SNIPPET_EXTRACT"
        highlighted: true
        explanation: "Core cache strategy configuration to reduce DB pressure in production."
---
# CodeShowCase
This showcase index maps the most relevant production-ready code areas. `content` fields are intentionally left as `TBD_SNIPPET_EXTRACT` placeholders so snippets can be auto-generated from source in a docs pipeline.

