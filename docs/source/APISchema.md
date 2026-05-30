---
type: "REST"

httpEndpoints:
  - id: "swagger-ui"
    method: "GET"
    urlPath: "/swagger-ui.html"
    summary: "Swagger UI"
    description: "Interactive OpenAPI documentation for all registered endpoints."
    tags: ["service"]
    authenticated: false
    rateLimit: "Unconfigured — add reverse-proxy rate limiting on EC2/ALB"
    responses:
      - status: 200
        description: "HTML Swagger UI"
        example:
          note: "Open in browser for full interactive reference"

  - id: "openapi-json"
    method: "GET"
    urlPath: "/v3/api-docs"
    summary: "OpenAPI JSON schema"
    description: "Machine-readable OpenAPI 3 document exported by springdoc."
    tags: ["service"]
    authenticated: false
    rateLimit: "Unconfigured"
    responses:
      - status: 200
        description: "OpenAPI JSON"
        example:
          openapi: "3.0.1"
          info:
            title: "Tourist Places API"

  - id: "auth-signup"
    method: "POST"
    urlPath: "/auth/signup"
    summary: "Register a new user"
    description: "Creates a VIEWER account and sends an email activation token via SMTP."
    tags: ["auth"]
    authenticated: false
    rateLimit: "Unconfigured — protect against abuse at ALB/WAF"
    requestBody:
      contentType: "application/json"
      schema:
        name: "string (required)"
        email: "string (required)"
        password: "string (required, min 8 chars)"
      example:
        name: "Alex Rivera"
        email: "alex@example.com"
        password: "SecurePass123!"
    responses:
      - status: 201
        description: "Signup accepted; activation email sent"
        example:
          success: true
          message: "An Email will be sent to the email provided. Use that token to activate your account."
          status_code: 201

  - id: "auth-login"
    method: "POST"
    urlPath: "/auth/login"
    summary: "Authenticate and obtain JWT"
    description: "Email/password login for activated users. Returns access and refresh tokens."
    tags: ["auth"]
    authenticated: false
    rateLimit: "Unconfigured — recommend per-IP throttling on login"
    requestBody:
      contentType: "application/json"
      schema:
        email: "string (required)"
        password: "string (required)"
      example:
        email: "alex@example.com"
        password: "SecurePass123!"
    responses:
      - status: 200
        description: "Login successful"
        example:
          success: true
          data:
            accessToken: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            refreshToken: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
          message: "user successfully Logind"
          status_code: 200

  - id: "auth-logout"
    method: "POST"
    urlPath: "/auth/logout"
    summary: "Logout and blacklist token"
    description: "Invalidates the Bearer access token in Redis."
    tags: ["auth"]
    authenticated: true
    rateLimit: "Standard authenticated"
    parameters:
      - name: "Authorization"
        in: "header"
        type: "string"
        required: true
        description: "Bearer access token"
        example: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    responses:
      - status: 200
        description: "Logout successful"
        example:
          success: true
          message: "Logout Successfully"
          status_code: 200

  - id: "auth-activate"
    method: "POST"
    urlPath: "/auth/activate-account/{token}"
    summary: "Activate account"
    description: "Activates user account using the token from the activation email."
    tags: ["auth"]
    authenticated: false
    rateLimit: "Unconfigured"
    parameters:
      - name: "token"
        in: "path"
        type: "string"
        required: true
        description: "Activation token from email"
        example: "abc123"
    responses:
      - status: 200
        description: "Account activated"
        example:
          success: true
          message: "Account successfully activated"
          status_code: 200

  - id: "auth-reset-request"
    method: "POST"
    urlPath: "/auth/{email}/reset-password-request"
    summary: "Request password reset"
    description: "Sends a password reset token to the user's email."
    tags: ["auth"]
    authenticated: false
    rateLimit: "Unconfigured — limit to prevent email flooding"
    parameters:
      - name: "email"
        in: "path"
        type: "string"
        required: true
        description: "Registered user email"
        example: "alex@example.com"
    responses:
      - status: 200
        description: "Reset token sent"
        example:
          success: true
          message: "A token will be sent to your email to allow password change"
          status_code: 200

  - id: "auth-reset-password"
    method: "POST"
    urlPath: "/auth/reset-password"
    summary: "Reset password"
    description: "Sets a new password using a valid reset token."
    tags: ["auth"]
    authenticated: false
    rateLimit: "Unconfigured"
    requestBody:
      contentType: "application/json"
      schema:
        token: "string (required)"
        newPassword: "string (required)"
      example:
        token: "reset-token-from-email"
        newPassword: "NewSecurePass456!"
    responses:
      - status: 200
        description: "Password changed"
        example:
          success: true
          message: "Password successfully changed"
          status_code: 200

  - id: "countries-list"
    method: "GET"
    urlPath: "/v1/api/countries"
    summary: "List all countries"
    description: "Returns all countries. Response cached in Caffeine (countryById, allCountries caches)."
    tags: ["countries"]
    authenticated: false
    rateLimit: "Public GET"
    responses:
      - status: 200
        description: "Country list"
        example:
          success: true
          data:
            - id: 1
              name: "Austria"
              capital: "Vienna"
              continent: "EUROPE"

  - id: "countries-by-id"
    method: "GET"
    urlPath: "/v1/api/countries/{id}"
    summary: "Get country by ID"
    description: "Returns a single country with capital, currency, language, population, area, and continent."
    tags: ["countries"]
    authenticated: false
    rateLimit: "Public GET"
    parameters:
      - name: "id"
        in: "path"
        type: "integer"
        required: true
        description: "Country ID"
        example: 1
    responses:
      - status: 200
        description: "Country found"
        example:
          success: true
          data:
            id: 1
            name: "Austria"
            capital: "Vienna"

  - id: "countries-by-name"
    method: "GET"
    urlPath: "/v1/api/countries/name/{name}"
    summary: "Get country by name"
    description: "Lookup country by exact name match."
    tags: ["countries"]
    authenticated: false
    rateLimit: "Public GET"
    parameters:
      - name: "name"
        in: "path"
        type: "string"
        required: true
        description: "Country name"
        example: "Austria"
    responses:
      - status: 200
        description: "Country found"
        example:
          success: true
          data:
            name: "Austria"

  - id: "countries-by-continent"
    method: "GET"
    urlPath: "/v1/api/countries/by-continent/{continentSTR}"
    summary: "List countries by continent"
    description: "Filter by continent enum: AFRICA, ASIA, EUROPE, AMERICA, OCEANIA."
    tags: ["countries"]
    authenticated: false
    rateLimit: "Public GET"
    parameters:
      - name: "continentSTR"
        in: "path"
        type: "string"
        required: true
        description: "Continent code"
        example: "EUROPE"
    responses:
      - status: 200
        description: "Filtered countries"
        example:
          success: true
          data: []

  - id: "countries-create"
    method: "POST"
    urlPath: "/v1/api/countries"
    summary: "Create country"
    description: "Adds a new country. Swagger documents ADMIN requirement; enforce with @PreAuthorize in production."
    tags: ["countries"]
    authenticated: true
    rateLimit: "Authenticated write"
    requestBody:
      contentType: "application/json"
      schema:
        name: "string (required)"
        capital: "string (required)"
        currency: "string (required)"
        language: "string (required)"
        population: "integer (required)"
        area: "number (required)"
        continent: "AFRICA | ASIA | EUROPE | AMERICA | OCEANIA"
      example:
        name: "Austria"
        capital: "Vienna"
        currency: "EUR"
        language: "German"
        population: 9000000
        area: 83871.0
        continent: "EUROPE"
    responses:
      - status: 201
        description: "Country created"
        example:
          success: true
          message: "Country successfully created"
          status_code: 201

  - id: "tourist-places-search"
    method: "GET"
    urlPath: "/v1/api/tourist_places/search"
    summary: "Search tourist places"
    description: "Paginated search with sort, filters via TouristPlaceSearchDTO query params."
    tags: ["tourist-places"]
    authenticated: false
    rateLimit: "Public GET"
    parameters:
      - name: "page"
        in: "query"
        type: "integer"
        required: false
        description: "Zero-based page index"
        example: 0
      - name: "size"
        in: "query"
        type: "integer"
        required: false
        description: "Page size"
        example: 10
      - name: "sortBy"
        in: "query"
        type: "string"
        required: false
        description: "Sort field"
        example: "name"
    responses:
      - status: 200
        description: "Paginated results"
        example:
          success: true
          data:
            content:
              - id: 1
                name: "Schönbrunn Palace"
                rating: 4.8

  - id: "tourist-places-by-id"
    method: "GET"
    urlPath: "/v1/api/tourist_places/{id}"
    summary: "Get tourist place by ID"
    description: "Returns place details including country, category, rating, and opening hours."
    tags: ["tourist-places"]
    authenticated: false
    rateLimit: "Public GET"
    parameters:
      - name: "id"
        in: "path"
        type: "integer"
        required: true
        description: "Tourist place ID"
        example: 1
    responses:
      - status: 200
        description: "Place found"
        example:
          success: true
          data:
            id: 1
            name: "Schönbrunn Palace"

  - id: "tourist-places-by-country"
    method: "GET"
    urlPath: "/v1/api/tourist_places/country/{countryId}"
    summary: "Places by country"
    description: "Lists tourist places linked to a country."
    tags: ["tourist-places"]
    authenticated: false
    rateLimit: "Public GET"
    parameters:
      - name: "countryId"
        in: "path"
        type: "integer"
        required: true
        description: "Country ID"
        example: 1
    responses:
      - status: 200
        description: "Places list"
        example:
          success: true
          data: []

  - id: "tourist-places-by-category"
    method: "GET"
    urlPath: "/v1/api/tourist_places/category/{categoryId}"
    summary: "Places by category"
    description: "Lists tourist places for a place category (museum, nature, etc.)."
    tags: ["tourist-places"]
    authenticated: false
    rateLimit: "Public GET"
    parameters:
      - name: "categoryId"
        in: "path"
        type: "integer"
        required: true
        description: "Category ID"
        example: 3
    responses:
      - status: 200
        description: "Places list"
        example:
          success: true
          data: []

  - id: "tourist-places-create"
    method: "POST"
    urlPath: "/v1/api/tourist_places"
    summary: "Create tourist place"
    description: "Adds a destination with country and category references."
    tags: ["tourist-places"]
    authenticated: true
    rateLimit: "Authenticated write"
    requestBody:
      contentType: "application/json"
      schema:
        name: "string (required)"
        description: "string (required)"
        countryId: "integer (optional)"
        categoryId: "integer (required)"
        rating: "number (optional)"
        openingHours: "string (optional)"
        priceRange: "string (optional)"
      example:
        name: "Hallstatt"
        description: "Alpine village and UNESCO site"
        countryId: 1
        categoryId: 2
        rating: 4.9
    responses:
      - status: 201
        description: "Place created"
        example:
          success: true
          message: "Tourist Place successfully created"
          status_code: 201

  - id: "place-categories-list"
    method: "GET"
    urlPath: "/v1/api/place_categories"
    summary: "List place categories"
    description: "Returns all categories used to classify tourist places."
    tags: ["place-categories"]
    authenticated: false
    rateLimit: "Public GET"
    responses:
      - status: 200
        description: "Category list"
        example:
          success: true
          data:
            - id: 1
              name: "Historical"
              description: "Heritage and monuments"

  - id: "activities-list"
    method: "GET"
    urlPath: "/v1/api/activities"
    summary: "List activities"
    description: "Returns activities (tours, experiences) linked to tourist places."
    tags: ["activities"]
    authenticated: false
    rateLimit: "Public GET"
    responses:
      - status: 200
        description: "Activity list"
        example:
          success: true
          data:
            - id: 1
              name: "Guided palace tour"
              price: 25.00
              duration: "2h"

  - id: "activities-by-place"
    method: "GET"
    urlPath: "/v1/api/activities/tourist_place/{place_id}"
    summary: "Activities for a place"
    description: "Lists activities available at a specific tourist place."
    tags: ["activities"]
    authenticated: false
    rateLimit: "Public GET"
    parameters:
      - name: "place_id"
        in: "path"
        type: "integer"
        required: true
        description: "Tourist place ID"
        example: 1
    responses:
      - status: 200
        description: "Activities for place"
        example:
          success: true
          data: []

  - id: "reviews-list"
    method: "GET"
    urlPath: "/v1/api/reviews"
    summary: "List all reviews"
    description: "Admin-oriented listing of all reviews in the system."
    tags: ["reviews"]
    authenticated: false
    rateLimit: "Public GET — security gap; should require ADMIN"
    responses:
      - status: 200
        description: "Review list"
        example:
          success: true
          data:
            - id: 1
              ratings: 4.5
              comment: "Beautiful views and great guides"

  - id: "reviews-by-place"
    method: "GET"
    urlPath: "/v1/api/reviews/tourist_place/{touristPlaceId}"
    summary: "Reviews for a place"
    description: "Returns reviews associated with a tourist place."
    tags: ["reviews"]
    authenticated: false
    rateLimit: "Public GET"
    parameters:
      - name: "touristPlaceId"
        in: "path"
        type: "integer"
        required: true
        description: "Tourist place ID"
        example: 1
    responses:
      - status: 200
        description: "Place reviews"
        example:
          success: true
          data: []

  - id: "user-reviews-mine"
    method: "GET"
    urlPath: "/v1/api/user/reviews"
    summary: "Get my reviews"
    description: "Paginated list of reviews authored by the authenticated user."
    tags: ["user-reviews"]
    authenticated: true
    rateLimit: "Authenticated"
    parameters:
      - name: "page"
        in: "query"
        type: "integer"
        required: false
        description: "Page number"
        example: 0
      - name: "size"
        in: "query"
        type: "integer"
        required: false
        description: "Page size"
        example: 10
    responses:
      - status: 200
        description: "User reviews"
        example:
          success: true
          data:
            content: []

  - id: "user-reviews-create"
    method: "POST"
    urlPath: "/v1/api/user/reviews"
    summary: "Create review as user"
    description: "Authenticated user submits a rating and comment; updates place average rating."
    tags: ["user-reviews"]
    authenticated: true
    rateLimit: "Authenticated write"
    requestBody:
      contentType: "application/json"
      schema:
        placeId: "integer (required)"
        ratings: "number (required)"
        comment: "string (required)"
      example:
        placeId: 1
        ratings: 5.0
        comment: "Absolutely worth the visit!"
    responses:
      - status: 201
        description: "Review created"
        example:
          success: true
          message: "Review successfully created"
          status_code: 201

  - id: "user-place-lists-mine"
    method: "GET"
    urlPath: "/v1/api/user/place-lists/mine"
    summary: "Get my place lists"
    description: "Returns curated place lists owned by the authenticated user."
    tags: ["user-place-lists"]
    authenticated: true
    rateLimit: "Authenticated"
    responses:
      - status: 200
        description: "User place lists"
        example:
          success: true
          data:
            - id: 1
              name: "Summer 2026 bucket list"

  - id: "user-place-lists-create"
    method: "POST"
    urlPath: "/v1/api/user/place-lists"
    summary: "Create place list"
    description: "Creates a named list for saving favorite destinations."
    tags: ["user-place-lists"]
    authenticated: true
    rateLimit: "Authenticated write"
    requestBody:
      contentType: "application/json"
      schema:
        name: "string (required)"
      example:
        name: "Weekend getaways"
    responses:
      - status: 201
        description: "List created"
        example:
          success: true
          message: "Place list successfully created"
          status_code: 201

  - id: "user-place-lists-add-place"
    method: "POST"
    urlPath: "/v1/api/user/place-lists/{placeListId}/add-place"
    summary: "Add place to list"
    description: "Links a tourist place to the user's list."
    tags: ["user-place-lists"]
    authenticated: true
    rateLimit: "Authenticated write"
    parameters:
      - name: "placeListId"
        in: "path"
        type: "integer"
        required: true
        description: "Place list ID"
        example: 1
    requestBody:
      contentType: "application/json"
      schema:
        placeId: "integer (required)"
      example:
        placeId: 5
    responses:
      - status: 200
        description: "Place added"
        example:
          success: true
          message: "Place successfully added to list"
          status_code: 200

  - id: "users-admin-me"
    method: "GET"
    urlPath: "/v1/api/users/admin/me"
    summary: "Get current admin profile"
    description: "Returns profile for the authenticated admin user."
    tags: ["users-admin"]
    authenticated: true
    rateLimit: "Authenticated"
    responses:
      - status: 200
        description: "Admin profile"
        example:
          success: true
          data:
            email: "admin@example.com"
            role: "ADMIN"

  - id: "users-admin-all"
    method: "GET"
    urlPath: "/v1/api/users/admin/all"
    summary: "List all users"
    description: "Returns all users with roles and activation status. Currently public GET — must be restricted."
    tags: ["users-admin"]
    authenticated: false
    rateLimit: "Public GET — security gap"
    responses:
      - status: 200
        description: "All users"
        example:
          success: true
          data: []

  - id: "oauth2-google"
    method: "GET"
    urlPath: "/oauth2/authorization/google"
    summary: "Start Google OAuth2 login"
    description: "Redirects to Google; on success issues JWT tokens. Requires GOOGLE_* env vars."
    tags: ["auth"]
    authenticated: false
    rateLimit: "OAuth provider limits apply"
    responses:
      - status: 302
        description: "Redirect to Google"
        example:
          note: "Browser redirect flow"
---

# API Schema

> **Base URL (production placeholder):** `https://api.tourist-places.example.com`

> **Auth header:** `Authorization: Bearer <accessToken>` for protected routes.

> **Response envelope:** Most endpoints return `ResponseWrapper` with `success`, `data`, `message`, and `status_code`.

> **Potentially dangerous:** `SecurityConfig` uses `.requestMatchers(HttpMethod.GET, "/**").permitAll()` — admin GET endpoints and review listings are reachable without a token until role checks are added. Never expose EC2/RDS security groups to `0.0.0.0/0` on database ports.

> **Path inconsistency:** Place lists admin routes use `/api/place-lists` while most resources use `/v1/api/`.

> **Schema assertion:** Endpoint objects match `ApiEndpoint` in `docs/source/schema.ts`. Validate with `python docs/yaml_to_markdown.py --validate`.
