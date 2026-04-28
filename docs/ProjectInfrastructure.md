---
# InfrastructureMetric[]
metrics:
  - label: "Deployability"
    value: "Ready baseline"
    icon: "Rocket"
    description: "Dockerized service and environment-driven configuration are in place."
  - label: "Stateful Components"
    value: "PostgreSQL + Redis"
    icon: "Database"
    description: "Primary database and cache dependencies are defined."
  - label: "Cloud Completion"
    value: "Pending final rollout"
    icon: "Cloud"
    description: "Managed cloud resources and production endpoint are still to be finalized."

# CloudService[]
cloudServices:
  - name: "Container Runtime (TBD Provider)"
    purpose: "Run Spring Boot API container in production."
    icon: "Server"
    cost: "TBD"
  - name: "Managed PostgreSQL (TBD Provider)"
    purpose: "Persistent relational database for domain entities."
    icon: "Database"
    cost: "TBD"
  - name: "Managed Redis (TBD Provider)"
    purpose: "Distributed cache and token/session support."
    icon: "Zap"
    cost: "TBD"
  - name: "Secrets Manager (TBD Provider)"
    purpose: "Store JWT secret, DB credentials, OAuth keys, and mail credentials securely."
    icon: "KeyRound"
    cost: "TBD"
  - name: "Reverse Proxy / API Gateway (TBD Provider)"
    purpose: "TLS termination, routing, and rate limiting."
    icon: "Globe"
    cost: "TBD"

# DeploymentLayer[]
deploymentLayers:
  - name: "Edge Layer"
    color: "#0EA5E9"
    components:
      - name: "DNS + TLS Endpoint"
        icon: "Globe"
        description: "Public API domain with HTTPS."
      - name: "Gateway / Reverse Proxy"
        icon: "Shield"
        description: "Ingress routing and request protection."
  - name: "Application Layer"
    color: "#22C55E"
    components:
      - name: "Tourist Places API Container"
        icon: "Server"
        description: "Spring Boot service handling all domain modules."
      - name: "Auto Scaling Group (TBD)"
        icon: "ArrowUpDown"
        description: "Horizontal scaling configuration for traffic growth."
  - name: "Data Layer"
    color: "#F59E0B"
    components:
      - name: "PostgreSQL"
        icon: "Database"
        description: "Primary relational data store with Flyway-managed schema."
      - name: "Redis"
        icon: "Zap"
        description: "Distributed cache for high-throughput reads."
      - name: "Backups / Snapshot Policy (TBD)"
        icon: "Archive"
        description: "Disaster recovery and data retention strategy."
  - name: "Operations Layer"
    color: "#A855F7"
    components:
      - name: "CI/CD Pipeline (TBD)"
        icon: "Workflow"
        description: "Automated test/build/deploy workflow."
      - name: "Monitoring Stack (TBD)"
        icon: "Activity"
        description: "Metrics, logs, traces, and alerting."

# DockerFile[]
dockerFiles:
  - service: "app"
    description: "Legacy Gradle-based multi-stage dockerfile; project build now uses Maven and should be aligned."
    content: "FROM gradle:8.4-jdk17 ... RUN gradle clean build -x test ... ENTRYPOINT [\"java\",\"-jar\",\"app.jar\"]"
  - service: "compose-app"
    description: "Docker Compose service that builds the app image from repository root."
    content: "app: { build: ., ports: ['8080:8080'], env_file: .env, depends_on: [postgres] }"
  - service: "compose-postgres"
    description: "PostgreSQL 15 service with persistent volume and environment-based credentials."
    content: "postgres: { image: postgres:15-alpine, ports: ['5431:5432'], volumes: [postgres-data] }"
---

# Infrastructure
Infrastructure is functionally prepared for cloud deployment, but still needs provider-specific implementation (managed services selection, production networking, secrets wiring, observability, and deployment automation).