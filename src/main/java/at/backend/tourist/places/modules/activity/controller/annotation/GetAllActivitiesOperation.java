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
    summary = "Get all activities",
    description = "Retrieve a paginated list of all activities available in the system. Returns all activities with their details including name, description, price, and duration."
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
        description = "No activities found",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Not Found",
                value = ApiResponseExamples.NOT_FOUND
            )
        )
    )
})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetAllActivitiesOperation {
}
