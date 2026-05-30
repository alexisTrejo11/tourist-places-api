---
codeExamples:
  - id: "jwt-token-validation"
    title: "JWT validation with Redis blacklist"
    description: "Every authenticated request passes through JwtService which rejects blacklisted tokens before parsing claims."
    category: "security"
    duration: "3 min read"
    views: 0
    tags:
      - "jwt"
      - "redis"
      - "auth"
    files:
      - name: "JwtService.java"
        path: "src/main/java/at/backend/tourist/places/modules/auth/jwt/JwtService.java"
        language: "java"
        highlighted: true
        explanation: "Blacklist check runs before JJWT parse; supports 6-char activation tokens via RedisTokenService."
        content: |
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

  - id: "jwt-auth-filter"
    title: "JWT authentication filter"
    description: "OncePerRequestFilter extracts Bearer token, validates it, and populates SecurityContext."
    category: "security"
    duration: "3 min read"
    views: 0
    tags:
      - "spring-security"
      - "filter"
    files:
      - name: "JwtAuthenticationFilter.java"
        path: "src/main/java/at/backend/tourist/places/modules/auth/jwt/JwtAuthenticationFilter.java"
        language: "java"
        highlighted: true
        explanation: "Runs before UsernamePasswordAuthenticationFilter; loads UserDetails from email claim."
        content: |
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

  - id: "response-wrapper"
    title: "Standardized API response envelope"
    description: "ResponseWrapper factory methods keep controller responses consistent for frontends and OpenAPI examples."
    category: "api"
    duration: "2 min read"
    views: 0
    tags:
      - "api"
      - "responses"
    files:
      - name: "ResponseWrapper.java"
        path: "src/main/java/at/backend/tourist/places/core/shared/Response/ResponseWrapper.java"
        language: "java"
        highlighted: true
        explanation: "Static helpers: created(), found(), ok(), deleted(), badRequest(), notFound()."
        content: |
          public static <T> ResponseWrapper<T> found(T data, String entity) {
              String foundMsg = entity + " data successfully fetched";
              return new ResponseWrapper<>(true, data, foundMsg, 200);
          }

          public static <T> ResponseWrapper<T> created(T data, String entity) {
              String createMsg = entity + " successfully created";
              return new ResponseWrapper<>(true, data, createMsg, 201);
          }

  - id: "tourist-place-search"
    title: "Paginated tourist place search"
    description: "Controller builds Pageable from query DTO and delegates search to TouristPlaceService."
    category: "domain"
    duration: "4 min read"
    views: 0
    tags:
      - "places"
      - "pagination"
    files:
      - name: "TouristPlaceController.java"
        path: "src/main/java/at/backend/tourist/places/modules/places/controller/TouristPlaceController.java"
        language: "java"
        highlighted: true
        explanation: "Public GET /search — sort direction and field come from TouristPlaceSearchDTO."
        content: |
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

  - id: "country-caffeine-cache"
    title: "Caffeine cache for country reads"
    description: "CacheConfig registers named caches with 1-day expire-after-access for browse-heavy country endpoints."
    category: "performance"
    duration: "2 min read"
    views: 0
    tags:
      - "caching"
      - "caffeine"
    files:
      - name: "CacheConfig.java"
        path: "src/main/java/at/backend/tourist/places/core/config/CacheConfig.java"
        language: "java"
        highlighted: true
        explanation: "Four cache names for country query patterns; max 100 entries each."
        content: |
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

  - id: "security-config"
    title: "Spring Security filter chain"
    description: "Defines public auth/swagger routes, permissive GET access, and JWT filter placement."
    category: "security"
    duration: "4 min read"
    views: 0
    tags:
      - "security"
      - "spring"
    files:
      - name: "SecurityConfig.java"
        path: "src/main/java/at/backend/tourist/places/core/config/SecurityConfig.java"
        language: "java"
        highlighted: true
        explanation: "CSRF disabled; stateless sessions; OAuth2 login for Google; review GET permitAll implications before prod."
        content: |
          http.csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/signup", "/login", "/auth/**").permitAll()
                  .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                  .requestMatchers(HttpMethod.GET, "/**").permitAll()
                  .anyRequest().authenticated())
              .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
---

# Code Showcase

> Snippets are abbreviated from the repository; open the referenced paths for full implementations including tests and MapStruct mappers.

> **Recommended reading order:** SecurityConfig → JwtAuthenticationFilter → JwtService → ResponseWrapper → TouristPlaceController.

> **Potentially dangerous:** SecurityConfig permits all GET requests—pair these snippets with hardening tasks before AWS public launch.

> **Schema assertion:** Examples match `ProjectCodeShowCase` / `CodeExample` in `docs/source/schema.ts`.
