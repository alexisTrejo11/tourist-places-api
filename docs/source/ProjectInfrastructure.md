---
metrics:
  - label: "Container port"
    value: "8080"
    icon: "server"
    description: "Spring Boot embedded Tomcat listens on SERVER_PORT (default 8080)"
  - label: "Docker publish"
    value: "8080:8080"
    icon: "network"
    description: "docker-compose maps host 8080 to container 8080 on EC2"
  - label: "Java runtime"
    value: "17 JRE"
    icon: "cpu"
    description: "Eclipse Temurin in multi-stage Maven Docker build"
  - label: "Database"
    value: "RDS PostgreSQL"
    icon: "database"
    description: "External managed instance — local Postgres service commented out in compose"

cloudServices:
  - name: "Amazon EC2"
    purpose: "Runs Docker container (tourist_places_app) with Spring Boot API; security group allows 443 via ALB and optionally 8080 for direct access"
    icon: "aws-ec2"
    cost: "~$15–40/mo (t3.small placeholder)"
  - name: "Amazon RDS (PostgreSQL)"
    purpose: "Primary database for countries, places, users, reviews, activities (DB_URL in .env)"
    icon: "aws-rds"
    cost: "~$25–80/mo (db.t4g.micro placeholder)"
  - name: "Amazon ElastiCache Redis"
    purpose: "Token blacklist, activation tokens, password reset tokens (SPRING_DATA_REDIS_HOST)"
    icon: "redis"
    cost: "~$15–50/mo (cache.t4g.micro placeholder)"
  - name: "AWS Application Load Balancer"
    purpose: "HTTPS termination with ACM certificate; forwards to EC2 target group on port 8080"
    icon: "aws-alb"
    cost: "~$18–25/mo + LCU usage"
  - name: "Amazon SES"
    purpose: "Transactional email for account activation and password reset (MAIL_HOST)"
    icon: "mail"
    cost: "Low volume ~$0.10/1000 emails"
  - name: "Google Cloud Console"
    purpose: "OAuth2 client credentials for social login (GOOGLE_CLIENT_ID/SECRET)"
    icon: "google"
    cost: "Free tier"
  - name: "Amazon Route 53"
    purpose: "DNS for api.yourdomain.com pointing to ALB"
    icon: "dns"
    cost: "~$0.50/hosted zone + queries"

deploymentLayers:
  - name: "Clients"
    color: "#4F46E5"
    components:
      - name: "Travel frontend"
        icon: "layout"
        description: "SPA or mobile app consuming /v1/api/* and /auth/*"
      - name: "Swagger UI"
        icon: "book"
        description: "Interactive API testing at /swagger-ui.html"
      - name: "Content admin"
        icon: "user-md"
        description: "ADMIN role tools for places, countries, activities"

  - name: "Edge & compute (EC2)"
    color: "#059669"
    components:
      - name: "AWS ALB"
        icon: "globe"
        description: "TLS termination, health checks, optional path-based routing"
      - name: "Docker — tourist_places_app"
        icon: "docker"
        description: "Built from dockerfile (Maven multi-stage); env_file .env"
      - name: "EC2 security group"
        icon: "shield"
        description: "Allow 443 from ALB; restrict 8080 to VPC or disable public 8080"

  - name: "Data layer"
    color: "#DC2626"
    components:
      - name: "RDS PostgreSQL"
        icon: "database"
        description: "Flyway migrations applied on app startup; SG allows only EC2/app subnet"
      - name: "ElastiCache Redis"
        icon: "redis"
        description: "Shared token store for multi-instance future scaling"
      - name: "RDS snapshots"
        icon: "archive"
        description: "Automated backups and point-in-time recovery enabled"

  - name: "External integrations"
    color: "#D97706"
    components:
      - name: "Amazon SES / SMTP"
        icon: "mail"
        description: "Activation and password-reset emails"
      - name: "Google OAuth2"
        icon: "google"
        description: "Social login redirect URI must match production domain"
      - name: "ACM certificate"
        icon: "lock"
        description: "TLS cert attached to ALB for HTTPS"

dockerFiles:
  - service: "app (docker-compose)"
    description: "Cloud-first compose: app only; PostgreSQL commented out—use RDS. Redis configured via SPRING_DATA_REDIS_* env vars."
    content: |
      services:
        app:
          build: .
          container_name: tourist_places_app
          ports:
            - "8080:8080"
          env_file: .env
          environment:
            SPRING_DATASOURCE_URL: ${DB_URL}
            SPRING_DATASOURCE_USERNAME: ${DB_USERNAME}
            SPRING_DATASOURCE_PASSWORD: ${DB_PASSWORD}
          networks:
            - app-network
      # Cloud: Amazon RDS PostgreSQL + ElastiCache Redis

  - service: "dockerfile (Maven multi-stage)"
    description: "Maven 3.9 + Temurin 17 build stage; JRE 17 runtime with app.jar."
    content: |
      FROM maven:3.9-eclipse-temurin-17 AS build
      WORKDIR /app
      COPY pom.xml .
      RUN mvn dependency:go-offline -B
      COPY src ./src
      RUN mvn clean package -DskipTests -B

      FROM eclipse-temurin:17-jre-jammy
      WORKDIR /app
      COPY --from=build /app/target/*.jar app.jar
      EXPOSE 8080
      ENTRYPOINT ["java", "-jar", "app.jar"]

  - service: "application.yml (datasource)"
    description: "Spring reads DB_URL, JWT_*, MAIL_*, and OAuth vars from .env via optional import."
    content: |
      spring:
        config:
          import: optional:file:.env[.properties]
        datasource:
          url: ${DB_URL}
          username: ${DB_USERNAME}
          password: ${DB_PASSWORD}
        flyway:
          locations: classpath:db/migrations
      jwt:
        secret: ${JWT_SECRET}
        accessExpiration: ${JWT_ACCESS_EXPIRATION}
        refreshExpiration: ${JWT_REFRESH_EXPIRATION}
---

# Infrastructure

> **Deploy story:** Provision RDS + Redis → configure `.env` on EC2 → `docker compose up --build -d` → point ALB target group at instance:8080 → run Flyway migrations on first boot.

> **EC2 checklist:** Use IAM role for SES if sending via AWS SDK later; store secrets in SSM Parameter Store instead of plain `.env` on disk; never expose RDS port 5432 to the public internet.

> **Useful:** Health can be probed via `GET /swagger-ui.html` or a dedicated actuator endpoint if added later. OpenAPI at `/v3/api-docs` for smoke tests.

> **Potentially dangerous:** Default `application.yml` sets Redis host to `localhost`—override with `SPRING_DATA_REDIS_HOST` for ElastiCache or tokens will fail silently in Docker. JWT_SECRET must be a valid Base64 string or startup will fail token parsing.

> **Schema assertion:** Fields match `InfrastructureModel` in `docs/source/schema.ts`.
