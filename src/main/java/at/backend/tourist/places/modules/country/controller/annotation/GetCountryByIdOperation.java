package at.backend.tourist.places.modules.country.controller.annotation;

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
    summary = "Get a country by its ID",
    description = "Retrieve comprehensive details about a country using its unique identifier. Returns complete country information including name, capital, currency, language, population, area, continent, and flag image."
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Country found successfully",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Success Response",
                value = ApiResponseExamples.COUNTRY
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Country not found with the specified ID",
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
public @interface GetCountryByIdOperation {
}

