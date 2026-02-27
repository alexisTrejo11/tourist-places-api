package at.backend.tourist.places.modules.user.controller.annotation;

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
    summary = "Create a review for a tourist place",
    description = "Create a new review with rating and comment for a specific tourist place. The author will be automatically set to the currently authenticated user. **Requires authentication**.",
    security = @SecurityRequirement(name = "bearerAuth")
)
@ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Review created successfully",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Success Response",
                value = ApiResponseExamples.USER_REVIEW_CREATED
            )
        )
    ),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid input or validation failed",
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
    )
})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateUserReviewOperation {
}

