---
problemStatement:
  problemTitle: "Tourism data is fragmented and hard to scale"
  problemDescription: "Travel platforms need a reliable backend to centralize place, country, activity, and review data while keeping authentication, performance, and data quality production-ready."
  problemList:
    - "Scattered data models across places, countries, activities, and reviews"
    - "Need for secure JWT/OAuth2 authentication with role controls"
    - "Performance bottlenecks without distributed/local caching"
    - "Lack of consistent API error handling and documentation"

solution:
  solutionTitle: "A modular Spring Boot API with cloud-ready architecture"
  solutionList:
    - title: "Modular domain architecture"
      description: "Separates auth, users, places, countries, activities, and reviews into maintainable modules."
    - title: "Production-grade security"
      description: "JWT and Google OAuth2 flow with role-based authorization and secure password handling."
    - title: "Performance-oriented data layer"
      description: "Uses Redis and Caffeine caching with JPA repositories over PostgreSQL."
    - title: "Deployable platform baseline"
      description: "Dockerized service + PostgreSQL compose setup, ready to be adapted to cloud runtime."

keyMetrics:
  metricsTitle: "Delivery snapshot"
  metricsList:
    - "10+ controllers with REST endpoints"
    - "JWT + OAuth2 authentication paths implemented"
    - "Dual cache strategy (Redis + Caffeine) configured"
    - "Flyway migrations integrated"
    - "Unit/controller tests present in src/test"

coverImage:
  url: "TBD_PROJECT_COVER_IMAGE"
  alt: "Tourist Places API cloud-ready backend illustration"
  credit: "TBD"

links:
  github: "https://github.com/alexisTrejo11/tourist-places-api"
  demo: "TBD_CLOUD_DEPLOY_URL"
  documentation: "http://localhost:8080/swagger-ui.html"
  dockerHub: "TBD_DOCKERHUB_IMAGE"

mediaGallery:
  title: "Project Gallery"
  description: "Placeholder media list for API screenshots and deployment diagrams."
  items:
    - type: "image"
      url: "TBD_SWAGGER_SCREENSHOT"
      thumbnail: "TBD_SWAGGER_SCREENSHOT_THUMB"
      title: "Swagger UI"
      description: "Interactive OpenAPI documentation."
      alt: "Swagger UI screenshot"
      category: "screenshot"
    - type: "image"
      url: "TBD_DEPLOYMENT_DIAGRAM"
      thumbnail: "TBD_DEPLOYMENT_DIAGRAM_THUMB"
      title: "Deployment Architecture"
      description: "Cloud deployment topology draft."
      alt: "Cloud deployment architecture diagram"
      category: "diagram"

mediaItems:
  - type: "image"
    url: "TBD_AUTH_FLOW_IMAGE"
    thumbnail: "TBD_AUTH_FLOW_IMAGE_THUMB"
    title: "Authentication Flow"
    description: "JWT + OAuth2 login flow."
    alt: "Authentication sequence diagram"
    category: "diagram"

metrics:
  - label: "Runtime"
    value: "Java 17"
    description: "Project runtime baseline."
    icon: "Cpu"
    unit: ""
    trend: "stable"
    threshold: null
  - label: "Framework"
    value: "Spring Boot 3.2.2"
    description: "Backend application framework."
    icon: "Server"
    unit: ""
    trend: "stable"
    threshold: null
  - label: "Cloud Status"
    value: "Pending Final Deploy"
    description: "Core app complete; cloud environment setup is remaining."
    icon: "Cloud"
    unit: ""
    trend: "up"
    threshold: null
---

# Overview
This project is an almost-complete backend platform intended to be deployed in the cloud as a production API for tourism applications. Most application concerns (security, modularity, persistence, caching, validation, and testing) are already implemented; the main pending stage is final cloud deployment and environment integration.