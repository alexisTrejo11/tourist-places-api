# Code Showcase

## JWT validation with Redis blacklist

Every authenticated request passes through JwtService which rejects blacklisted tokens before parsing claims.

**Category:** security | **Duration:** 3 min read | **Tags:** jwt, redis, auth

### JwtService.java

**Path:** `src/main/java/at/backend/tourist/places/modules/auth/jwt/JwtService.java`

Blacklist check runs before JJWT parse; supports 6-char activation tokens via RedisTokenService.

```java
public boolean validateToken(String token) {
    if (token.length() == 6) {
        return validateCustomerToken(token);
    } else {
        return validateJWTToken(token);
    }
}

private boolean validateJWTToken(String token) {
    if (redisTokenService.isTokenBlacklisted(token)) {
        return false;
    }
    Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token);
    return true;
}
```

## JWT authentication filter

OncePerRequestFilter extracts Bearer token, validates it, and populates SecurityContext.

**Category:** security | **Duration:** 3 min read | **Tags:** spring-security, filter

### JwtAuthenticationFilter.java

**Path:** `src/main/java/at/backend/tourist/places/modules/auth/jwt/JwtAuthenticationFilter.java`

Runs before UsernamePasswordAuthenticationFilter; loads UserDetails from email claim.

```java
@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain) throws ServletException, IOException {
    String token = extractToken(request);
    if (token != null && jwtService.validateToken(token)) {
        String email = jwtService.getEmailFromToken(token);
        UserDetails userDetails = userService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
}
```

## Standardized API response envelope

ResponseWrapper factory methods keep controller responses consistent for frontends and OpenAPI examples.

**Category:** api | **Duration:** 2 min read | **Tags:** api, responses

### ResponseWrapper.java

**Path:** `src/main/java/at/backend/tourist/places/core/shared/Response/ResponseWrapper.java`

Static helpers: created(), found(), ok(), deleted(), badRequest(), notFound().

```java
public static <T> ResponseWrapper<T> found(T data, String entity) {
    String foundMsg = entity + " data successfully fetched";
    return new ResponseWrapper<>(true, data, foundMsg, 200);
}

public static <T> ResponseWrapper<T> created(T data, String entity) {
    String createMsg = entity + " successfully created";
    return new ResponseWrapper<>(true, data, createMsg, 201);
}
```

## Paginated tourist place search

Controller builds Pageable from query DTO and delegates search to TouristPlaceService.

**Category:** domain | **Duration:** 4 min read | **Tags:** places, pagination

### TouristPlaceController.java

**Path:** `src/main/java/at/backend/tourist/places/modules/places/controller/TouristPlaceController.java`

Public GET /search — sort direction and field come from TouristPlaceSearchDTO.

```java
@GetMapping("/search")
public ResponseWrapper<Page<TouristPlaceDTO>> searchTouristPlaces(
        @ModelAttribute TouristPlaceSearchDTO searchDto) {
    Sort sort = Sort.by(
            searchDto.getSortDirection().equalsIgnoreCase("desc") ?
                    Sort.Direction.DESC : Sort.Direction.ASC,
            searchDto.getSortBy());
    Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(), sort);
    Page<TouristPlaceDTO> results = placeService.searchTouristPlaces(searchDto, pageable);
    return ResponseWrapper.found(results, "Tourist places");
}
```

## Caffeine cache for country reads

CacheConfig registers named caches with 1-day expire-after-access for browse-heavy country endpoints.

**Category:** performance | **Duration:** 2 min read | **Tags:** caching, caffeine

### CacheConfig.java

**Path:** `src/main/java/at/backend/tourist/places/core/config/CacheConfig.java`

Four cache names for country query patterns; max 100 entries each.

```java
@Bean
public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager(
            "countryById", "allCountries",
            "countriesByContinent", "countryByName");
    cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(1, TimeUnit.DAYS));
    return cacheManager;
}
```

## Spring Security filter chain

Defines public auth/swagger routes, permissive GET access, and JWT filter placement.

**Category:** security | **Duration:** 4 min read | **Tags:** security, spring

### SecurityConfig.java

**Path:** `src/main/java/at/backend/tourist/places/core/config/SecurityConfig.java`

CSRF disabled; stateless sessions; OAuth2 login for Google; review GET permitAll implications before prod.

```java
http.csrf(AbstractHttpConfigurer::disable)
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/signup", "/login", "/auth/**").permitAll()
        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/**").permitAll()
        .anyRequest().authenticated())
    .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
```

## Additional notes

# Code Showcase

> Snippets are abbreviated from the repository; open the referenced paths for full implementations including tests and MapStruct mappers.

> **Recommended reading order:** SecurityConfig → JwtAuthenticationFilter → JwtService → ResponseWrapper → TouristPlaceController.

> **Potentially dangerous:** SecurityConfig permits all GET requests—pair these snippets with hardening tasks before AWS public launch.

> **Schema assertion:** Examples match `ProjectCodeShowCase` / `CodeExample` in `docs/source/schema.ts`.

