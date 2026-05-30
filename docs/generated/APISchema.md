# API Schema

**API type:** REST

## Activities

### `GET` /v1/api/activities

**List activities**

Returns activities (tours, experiences) linked to tourist places.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | activities |

#### Responses

- **200** — Activity list

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Guided palace tour",
      "price": 25.0,
      "duration": "2h"
    }
  ]
}
```

---

### `GET` /v1/api/activities/tourist_place/{place_id}

**Activities for a place**

Lists activities available at a specific tourist place.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | activities |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| place_id | path | integer | Yes | Tourist place ID |

#### Responses

- **200** — Activities for place

```json
{
  "success": true,
  "data": []
}
```

---

## Auth

### `POST` /auth/signup

**Register a new user**

Creates a VIEWER account and sends an email activation token via SMTP.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Unconfigured — protect against abuse at ALB/WAF |
| **Tags** | auth |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "name": "string (required)",
  "email": "string (required)",
  "password": "string (required, min 8 chars)"
}
```

**Example:**

```json
{
  "name": "Alex Rivera",
  "email": "alex@example.com",
  "password": "SecurePass123!"
}
```

#### Responses

- **201** — Signup accepted; activation email sent

```json
{
  "success": true,
  "message": "An Email will be sent to the email provided. Use that token to activate your account.",
  "status_code": 201
}
```

---

### `POST` /auth/login

**Authenticate and obtain JWT**

Email/password login for activated users. Returns access and refresh tokens.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Unconfigured — recommend per-IP throttling on login |
| **Tags** | auth |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "email": "string (required)",
  "password": "string (required)"
}
```

**Example:**

```json
{
  "email": "alex@example.com",
  "password": "SecurePass123!"
}
```

#### Responses

- **200** — Login successful

```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  },
  "message": "user successfully Logind",
  "status_code": 200
}
```

---

### `POST` /auth/logout

**Logout and blacklist token**

Invalidates the Bearer access token in Redis.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Standard authenticated |
| **Tags** | auth |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| Authorization | header | string | Yes | Bearer access token |

#### Responses

- **200** — Logout successful

```json
{
  "success": true,
  "message": "Logout Successfully",
  "status_code": 200
}
```

---

### `POST` /auth/activate-account/{token}

**Activate account**

Activates user account using the token from the activation email.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Unconfigured |
| **Tags** | auth |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| token | path | string | Yes | Activation token from email |

#### Responses

- **200** — Account activated

```json
{
  "success": true,
  "message": "Account successfully activated",
  "status_code": 200
}
```

---

### `POST` /auth/{email}/reset-password-request

**Request password reset**

Sends a password reset token to the user's email.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Unconfigured — limit to prevent email flooding |
| **Tags** | auth |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| email | path | string | Yes | Registered user email |

#### Responses

- **200** — Reset token sent

```json
{
  "success": true,
  "message": "A token will be sent to your email to allow password change",
  "status_code": 200
}
```

---

### `POST` /auth/reset-password

**Reset password**

Sets a new password using a valid reset token.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Unconfigured |
| **Tags** | auth |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "token": "string (required)",
  "newPassword": "string (required)"
}
```

**Example:**

```json
{
  "token": "reset-token-from-email",
  "newPassword": "NewSecurePass456!"
}
```

#### Responses

- **200** — Password changed

```json
{
  "success": true,
  "message": "Password successfully changed",
  "status_code": 200
}
```

---

### `GET` /oauth2/authorization/google

**Start Google OAuth2 login**

Redirects to Google; on success issues JWT tokens. Requires GOOGLE_* env vars.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | OAuth provider limits apply |
| **Tags** | auth |

#### Responses

- **302** — Redirect to Google

```json
{
  "note": "Browser redirect flow"
}
```

---

## Countries

### `GET` /v1/api/countries

**List all countries**

Returns all countries. Response cached in Caffeine (countryById, allCountries caches).

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | countries |

#### Responses

- **200** — Country list

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Austria",
      "capital": "Vienna",
      "continent": "EUROPE"
    }
  ]
}
```

---

### `GET` /v1/api/countries/{id}

**Get country by ID**

Returns a single country with capital, currency, language, population, area, and continent.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | countries |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| id | path | integer | Yes | Country ID |

#### Responses

- **200** — Country found

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Austria",
    "capital": "Vienna"
  }
}
```

---

### `GET` /v1/api/countries/name/{name}

**Get country by name**

Lookup country by exact name match.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | countries |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| name | path | string | Yes | Country name |

#### Responses

- **200** — Country found

```json
{
  "success": true,
  "data": {
    "name": "Austria"
  }
}
```

---

### `GET` /v1/api/countries/by-continent/{continentSTR}

**List countries by continent**

Filter by continent enum: AFRICA, ASIA, EUROPE, AMERICA, OCEANIA.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | countries |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| continentSTR | path | string | Yes | Continent code |

#### Responses

- **200** — Filtered countries

```json
{
  "success": true,
  "data": []
}
```

---

### `POST` /v1/api/countries

**Create country**

Adds a new country. Swagger documents ADMIN requirement; enforce with @PreAuthorize in production.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated write |
| **Tags** | countries |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "name": "string (required)",
  "capital": "string (required)",
  "currency": "string (required)",
  "language": "string (required)",
  "population": "integer (required)",
  "area": "number (required)",
  "continent": "AFRICA | ASIA | EUROPE | AMERICA | OCEANIA"
}
```

**Example:**

```json
{
  "name": "Austria",
  "capital": "Vienna",
  "currency": "EUR",
  "language": "German",
  "population": 9000000,
  "area": 83871.0,
  "continent": "EUROPE"
}
```

#### Responses

- **201** — Country created

```json
{
  "success": true,
  "message": "Country successfully created",
  "status_code": 201
}
```

---

## Place-Categories

### `GET` /v1/api/place_categories

**List place categories**

Returns all categories used to classify tourist places.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | place-categories |

#### Responses

- **200** — Category list

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Historical",
      "description": "Heritage and monuments"
    }
  ]
}
```

---

## Reviews

### `GET` /v1/api/reviews

**List all reviews**

Admin-oriented listing of all reviews in the system.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET — security gap; should require ADMIN |
| **Tags** | reviews |

#### Responses

- **200** — Review list

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "ratings": 4.5,
      "comment": "Beautiful views and great guides"
    }
  ]
}
```

---

### `GET` /v1/api/reviews/tourist_place/{touristPlaceId}

**Reviews for a place**

Returns reviews associated with a tourist place.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | reviews |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| touristPlaceId | path | integer | Yes | Tourist place ID |

#### Responses

- **200** — Place reviews

```json
{
  "success": true,
  "data": []
}
```

---

## Service

### `GET` /swagger-ui.html

**Swagger UI**

Interactive OpenAPI documentation for all registered endpoints.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Unconfigured — add reverse-proxy rate limiting on EC2/ALB |
| **Tags** | service |

#### Responses

- **200** — HTML Swagger UI

```json
{
  "note": "Open in browser for full interactive reference"
}
```

---

### `GET` /v3/api-docs

**OpenAPI JSON schema**

Machine-readable OpenAPI 3 document exported by springdoc.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Unconfigured |
| **Tags** | service |

#### Responses

- **200** — OpenAPI JSON

```json
{
  "openapi": "3.0.1",
  "info": {
    "title": "Tourist Places API"
  }
}
```

---

## Tourist-Places

### `GET` /v1/api/tourist_places/search

**Search tourist places**

Paginated search with sort, filters via TouristPlaceSearchDTO query params.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | tourist-places |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| page | query | integer | No | Zero-based page index |
| size | query | integer | No | Page size |
| sortBy | query | string | No | Sort field |

#### Responses

- **200** — Paginated results

```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "name": "Schönbrunn Palace",
        "rating": 4.8
      }
    ]
  }
}
```

---

### `GET` /v1/api/tourist_places/{id}

**Get tourist place by ID**

Returns place details including country, category, rating, and opening hours.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | tourist-places |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| id | path | integer | Yes | Tourist place ID |

#### Responses

- **200** — Place found

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Schönbrunn Palace"
  }
}
```

---

### `GET` /v1/api/tourist_places/country/{countryId}

**Places by country**

Lists tourist places linked to a country.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | tourist-places |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| countryId | path | integer | Yes | Country ID |

#### Responses

- **200** — Places list

```json
{
  "success": true,
  "data": []
}
```

---

### `GET` /v1/api/tourist_places/category/{categoryId}

**Places by category**

Lists tourist places for a place category (museum, nature, etc.).

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET |
| **Tags** | tourist-places |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| categoryId | path | integer | Yes | Category ID |

#### Responses

- **200** — Places list

```json
{
  "success": true,
  "data": []
}
```

---

### `POST` /v1/api/tourist_places

**Create tourist place**

Adds a destination with country and category references.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated write |
| **Tags** | tourist-places |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "name": "string (required)",
  "description": "string (required)",
  "countryId": "integer (optional)",
  "categoryId": "integer (required)",
  "rating": "number (optional)",
  "openingHours": "string (optional)",
  "priceRange": "string (optional)"
}
```

**Example:**

```json
{
  "name": "Hallstatt",
  "description": "Alpine village and UNESCO site",
  "countryId": 1,
  "categoryId": 2,
  "rating": 4.9
}
```

#### Responses

- **201** — Place created

```json
{
  "success": true,
  "message": "Tourist Place successfully created",
  "status_code": 201
}
```

---

## User-Place-Lists

### `GET` /v1/api/user/place-lists/mine

**Get my place lists**

Returns curated place lists owned by the authenticated user.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | user-place-lists |

#### Responses

- **200** — User place lists

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Summer 2026 bucket list"
    }
  ]
}
```

---

### `POST` /v1/api/user/place-lists

**Create place list**

Creates a named list for saving favorite destinations.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated write |
| **Tags** | user-place-lists |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "name": "string (required)"
}
```

**Example:**

```json
{
  "name": "Weekend getaways"
}
```

#### Responses

- **201** — List created

```json
{
  "success": true,
  "message": "Place list successfully created",
  "status_code": 201
}
```

---

### `POST` /v1/api/user/place-lists/{placeListId}/add-place

**Add place to list**

Links a tourist place to the user's list.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated write |
| **Tags** | user-place-lists |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| placeListId | path | integer | Yes | Place list ID |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "placeId": "integer (required)"
}
```

**Example:**

```json
{
  "placeId": 5
}
```

#### Responses

- **200** — Place added

```json
{
  "success": true,
  "message": "Place successfully added to list",
  "status_code": 200
}
```

---

## User-Reviews

### `GET` /v1/api/user/reviews

**Get my reviews**

Paginated list of reviews authored by the authenticated user.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | user-reviews |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| page | query | integer | No | Page number |
| size | query | integer | No | Page size |

#### Responses

- **200** — User reviews

```json
{
  "success": true,
  "data": {
    "content": []
  }
}
```

---

### `POST` /v1/api/user/reviews

**Create review as user**

Authenticated user submits a rating and comment; updates place average rating.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated write |
| **Tags** | user-reviews |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "placeId": "integer (required)",
  "ratings": "number (required)",
  "comment": "string (required)"
}
```

**Example:**

```json
{
  "placeId": 1,
  "ratings": 5.0,
  "comment": "Absolutely worth the visit!"
}
```

#### Responses

- **201** — Review created

```json
{
  "success": true,
  "message": "Review successfully created",
  "status_code": 201
}
```

---

## Users-Admin

### `GET` /v1/api/users/admin/me

**Get current admin profile**

Returns profile for the authenticated admin user.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | users-admin |

#### Responses

- **200** — Admin profile

```json
{
  "success": true,
  "data": {
    "email": "admin@example.com",
    "role": "ADMIN"
  }
}
```

---

### `GET` /v1/api/users/admin/all

**List all users**

Returns all users with roles and activation status. Currently public GET — must be restricted.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Public GET — security gap |
| **Tags** | users-admin |

#### Responses

- **200** — All users

```json
{
  "success": true,
  "data": []
}
```

---

## Additional notes

# API Schema

> **Base URL (production placeholder):** `https://api.tourist-places.example.com`

> **Auth header:** `Authorization: Bearer <accessToken>` for protected routes.

> **Response envelope:** Most endpoints return `ResponseWrapper` with `success`, `data`, `message`, and `status_code`.

> **Potentially dangerous:** `SecurityConfig` uses `.requestMatchers(HttpMethod.GET, "/**").permitAll()` — admin GET endpoints and review listings are reachable without a token until role checks are added. Never expose EC2/RDS security groups to `0.0.0.0/0` on database ports.

> **Path inconsistency:** Place lists admin routes use `/api/place-lists` while most resources use `/v1/api/`.

> **Schema assertion:** Endpoint objects match `ApiEndpoint` in `docs/source/schema.ts`. Validate with `python docs/yaml_to_markdown.py --validate`.

