package at.backend.tourist.places.modules.activity.controller.annotation;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.core.shared.SwaggerHelper.ApiConstants;
import at.backend.tourist.places.core.swagger.ApiResponseExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(
    summary = "Get an activity by ID",
    description = "Retrieve the complete details of a specific activity using its unique identifier. Returns comprehensive information including name, description, pricing, duration, and associated tourist place."
)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = ApiConstants.ACTIVITY_RETRIEVED,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Success Response",
                value = ApiResponseExamples.ACTIVITY
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = ApiConstants.ACTIVITY_NOT_FOUND,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Activity Not Found",
                value = ApiResponseExamples.NOT_FOUND
            )
        )
    ),
    @ApiResponse(
        responseCode = "403",
        description = "Access forbidden, insufficient permissions",
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
public @interface GetActivityByIdOperation {
}

