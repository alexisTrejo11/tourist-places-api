package at.backend.tourist.places.modules.review.controller.annotation;

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
    summary = "Get a review by ID",
    description = "Retrieve detailed information about a specific review using its unique identifier. Returns the review with rating, comment, author, and associated tourist place. **Requires ADMIN role**.",
    security = @SecurityRequirement(name = "bearerAuth")
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Review retrieved successfully",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Success Response",
                value = ApiResponseExamples.REVIEW
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Review not found with the specified ID",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Not Found",
                value = ApiResponseExamples.NOT_FOUND
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
public @interface GetReviewByIdOperation {
}

