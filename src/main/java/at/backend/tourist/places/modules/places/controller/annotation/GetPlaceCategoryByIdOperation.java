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
    summary = "Get a place category by ID",
    description = "Retrieve detailed information about a specific place category using its unique identifier. Returns the category name and description."
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Place category found successfully",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Success Response",
                value = ApiResponseExamples.PLACE_CATEGORY
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Place category not found with the specified ID",
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
public @interface GetPlaceCategoryByIdOperation {
}

