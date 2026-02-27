package at.backend.tourist.places.modules.country.controller.annotation;

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
    summary = "Get all countries",
    description = "Retrieve a comprehensive list of all countries available in the system. Returns complete details for each country including name, capital, currency, language, population, area, continent, and flag image."
)
@ApiResponse(
    responseCode = "200",
    description = "List of countries retrieved successfully",
    content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ResponseWrapper.class),
        examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
            name = "Success Response",
            value = ApiResponseExamples.COUNTRIES
        )
    )
)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetAllCountriesOperation {
}

