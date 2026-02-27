package at.backend.tourist.places.modules.places.controller.annotation;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
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
    summary = "Get all place lists",
    description = "Retrieve a comprehensive list of all place lists in the system. Each place list contains a collection of tourist places organized by users."
)
@ApiResponse(
    responseCode = "200",
    description = "Place lists retrieved successfully",
    content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ResponseWrapper.class),
        examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
            name = "Success Response",
            value = ApiResponseExamples.PLACE_LISTS
        )
    )
)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetAllPlaceListsOperation {
}

