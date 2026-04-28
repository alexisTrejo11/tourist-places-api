---
# APISchema
type: "REST"

# ApiEndpoint[]
httpEndpoints:
  - id: "auth-signup"
    method: "POST"
    urlPath: "/auth/signup"
    summary: "Create user account"
    description: "Registers a new account and triggers activation flow."
    authenticated: false
    rateLimit: "TBD"
    tags: ["auth"]
    parameters: []
    requestBody:
      contentType: "application/json"
      schema: {"type":"object"}
      example: {"username":"alex","email":"alex@example.com","password":"StrongPass123!"}
    responses:
      - status: 201
        description: "User created"
        schema: {"type":"object"}
        example: "Success response wrapper"
      - status: 400
        description: "Invalid payload"
        example: "Validation error wrapper"
      - status: 500
        description: "Unexpected error"
        example: "Server error wrapper"

  - id: "auth-login"
    method: "POST"
    urlPath: "/auth/login"
    summary: "Authenticate user"
    description: "Authenticates credentials and returns JWT/token payload."
    authenticated: false
    rateLimit: "TBD"
    tags: ["auth"]
    parameters: []
    requestBody:
      contentType: "application/json"
      schema: {"type":"object"}
      example: {"email":"alex@example.com","password":"StrongPass123!"}
    responses:
      - status: 200
        description: "Authenticated"
        schema: {"type":"object"}
        example: "JWT + user payload"
      - status: 401
        description: "Invalid credentials"
        example: "Unauthorized response wrapper"

  - id: "countries-get-all"
    method: "GET"
    urlPath: "v1/api/countries"
    summary: "List countries"
    description: "Returns all countries."
    authenticated: true
    rateLimit: "TBD"
    tags: ["country"]
    parameters: []
    requestBody:
      contentType: "application/json"
      schema: {}
      example: {}
    responses:
      - status: 200
        description: "Country list"
        schema: {"type":"array"}
        example: "List of country DTOs"
      - status: 401
        description: "Unauthorized"
        example: "Unauthorized response wrapper"

  - id: "tourist-place-search"
    method: "GET"
    urlPath: "v1/api/tourist_places/search"
    summary: "Search tourist places"
    description: "Searches tourist places by optional filters."
    authenticated: true
    rateLimit: "TBD"
    tags: ["tourist_places"]
    parameters:
      - name: "name"
        in: "query"
        type: "string"
        required: false
        description: "Place name filter."
        example: "Cancun"
    requestBody:
      contentType: "application/json"
      schema: {}
      example: {}
    responses:
      - status: 200
        description: "Search results"
        schema: {"type":"array"}
        example: "List of matching places"
      - status: 400
        description: "Invalid query"
        example: "Validation error wrapper"

  - id: "reviews-create"
    method: "POST"
    urlPath: "v1/api/reviews"
    summary: "Create review"
    description: "Creates a review for a tourist place."
    authenticated: true
    rateLimit: "TBD"
    tags: ["reviews"]
    parameters: []
    requestBody:
      contentType: "application/json"
      schema: {"type":"object"}
      example: {"touristPlaceId":1,"rating":5,"comment":"Excellent place"}
    responses:
      - status: 201
        description: "Review created"
        schema: {"type":"object"}
        example: "Review DTO wrapper"
      - status: 401
        description: "Unauthorized"
        example: "Unauthorized response wrapper"
      - status: 404
        description: "Tourist place not found"
        example: "Not found response wrapper"
---

# API Schema

This schema includes representative endpoints from auth, country, places, and review modules. Full endpoint coverage is available in Swagger UI and can be exported to this template in a future docs automation step.