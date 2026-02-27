package at.backend.tourist.places.modules.places.controller.annotation;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.core.swagger.ApiResponseExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(
    summary = "Get all place categories",
    description = "Retrieve a comprehensive list of all available place categories in the system. Categories include Historical Sites, Monuments, Natural Wonders, Museums, and more."
)
@ApiResponse(
    responseCode = "200",
    description = "Place categories retrieved successfully",
    content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ResponseWrapper.class),
        examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
            name = "Success Response",
            value = ApiResponseExamples.PLACE_CATEGORIES
        )
    )
)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetAllPlaceCategoriesOperation {
}

