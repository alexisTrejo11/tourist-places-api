# Infrastructure

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Container port | 8080 | Spring Boot embedded Tomcat listens on SERVER_PORT (default 8080) |
| Docker publish | 8080:8080 | docker-compose maps host 8080 to container 8080 on EC2 |
| Java runtime | 17 JRE | Eclipse Temurin in multi-stage Maven Docker build |
| Database | RDS PostgreSQL | External managed instance — local Postgres service commented out in compose |

## Cloud services

| Service | Purpose | Est. cost |
| --- | --- | --- |
| Amazon EC2 | Runs Docker container (tourist_places_app) with Spring Boot API; security group allows 443 via ALB and optionally 8080 for direct access | ~$15–40/mo (t3.small placeholder) |
| Amazon RDS (PostgreSQL) | Primary database for countries, places, users, reviews, activities (DB_URL in .env) | ~$25–80/mo (db.t4g.micro placeholder) |
| Amazon ElastiCache Redis | Token blacklist, activation tokens, password reset tokens (SPRING_DATA_REDIS_HOST) | ~$15–50/mo (cache.t4g.micro placeholder) |
| AWS Application Load Balancer | HTTPS termination with ACM certificate; forwards to EC2 target group on port 8080 | ~$18–25/mo + LCU usage |
| Amazon SES | Transactional email for account activation and password reset (MAIL_HOST) | Low volume ~$0.10/1000 emails |
| Google Cloud Console | OAuth2 client credentials for social login (GOOGLE_CLIENT_ID/SECRET) | Free tier |
| Amazon Route 53 | DNS for api.yourdomain.com pointing to ALB | ~$0.50/hosted zone + queries |

## Deployment layers

### Clients

- **Travel frontend** — SPA or mobile app consuming /v1/api/* and /auth/*
- **Swagger UI** — Interactive API testing at /swagger-ui.html
- **Content admin** — ADMIN role tools for places, countries, activities

### Edge & compute (EC2)

- **AWS ALB** — TLS termination, health checks, optional path-based routing
- **Docker — tourist_places_app** — Built from dockerfile (Maven multi-stage); env_file .env
- **EC2 security group** — Allow 443 from ALB; restrict 8080 to VPC or disable public 8080

### Data layer

- **RDS PostgreSQL** — Flyway migrations applied on app startup; SG allows only EC2/app subnet
- **ElastiCache Redis** — Shared token store for multi-instance future scaling
- **RDS snapshots** — Automated backups and point-in-time recovery enabled

### External integrations

- **Amazon SES / SMTP** — Activation and password-reset emails
- **Google OAuth2** — Social login redirect URI must match production domain
- **ACM certificate** — TLS cert attached to ALB for HTTPS

## Docker configuration

### app (docker-compose)

Cloud-first compose: app only; PostgreSQL commented out—use RDS. Redis configured via SPRING_DATA_REDIS_* env vars.

```yaml
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
```

### dockerfile (Maven multi-stage)

Maven 3.9 + Temurin 17 build stage; JRE 17 runtime with app.jar.

```yaml
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
```

### application.yml (datasource)

Spring reads DB_URL, JWT_*, MAIL_*, and OAuth vars from .env via optional import.

```yaml
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
```

## Additional notes

# Infrastructure

> **Deploy story:** Provision RDS + Redis → configure `.env` on EC2 → `docker compose up --build -d` → point ALB target group at instance:8080 → run Flyway migrations on first boot.

> **EC2 checklist:** Use IAM role for SES if sending via AWS SDK later; store secrets in SSM Parameter Store instead of plain `.env` on disk; never expose RDS port 5432 to the public internet.

> **Useful:** Health can be probed via `GET /swagger-ui.html` or a dedicated actuator endpoint if added later. OpenAPI at `/v3/api-docs` for smoke tests.

> **Potentially dangerous:** Default `application.yml` sets Redis host to `localhost`—override with `SPRING_DATA_REDIS_HOST` for ElastiCache or tokens will fail silently in Docker. JWT_SECRET must be a valid Base64 string or startup will fail token parsing.

> **Schema assertion:** Fields match `InfrastructureModel` in `docs/source/schema.ts`.

