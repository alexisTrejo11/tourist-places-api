package at.backend.tourist.places.modules.country.controller.annotation;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.core.swagger.ApiResponseExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(
    summary = "Create a new country",
    description = "Add a new country to the system with complete details including name, capital, currency, language, population, area, continent, and flag image. **Requires ADMIN role**.",
    security = @SecurityRequirement(name = "bearerAuth")
)
@ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Country created successfully",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Success Response",
                value = ApiResponseExamples.COUNTRY_CREATED
            )
        )
    ),
    @ApiResponse(
        responseCode = "400",
        description = "Validation error or invalid input data",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Bad Request",
                value = ApiResponseExamples.BAD_REQUEST
            )
        )
    ),
    @ApiResponse(
        responseCode = "401",
        description = "Unauthorized - authentication required",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Unauthorized",
                value = ApiResponseExamples.UNAUTHORIZED_ACCESS
            )
        )
    ),
    @ApiResponse(
        responseCode = "403",
        description = "Forbidden - requires ADMIN role",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Forbidden",
                value = ApiResponseExamples.FORBIDDEN
            )
        )
    )
})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateCountryOperation {
}

