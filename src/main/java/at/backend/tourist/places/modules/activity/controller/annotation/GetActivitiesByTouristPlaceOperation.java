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
    summary = "Get activities by tourist place ID",
    description = "Retrieve a comprehensive list of all activities associated with a specific tourist place. Returns multiple activities with complete details for each one."
)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = ApiConstants.ACTIVITIES_RETRIEVED,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Success Response",
                value = ApiResponseExamples.ACTIVITIES
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "No activities found for this tourist place",
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
        responseCode = "403",
        description = "Forbidden access to the activities of this tourist place",
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
public @interface GetActivitiesByTouristPlaceOperation {
}

