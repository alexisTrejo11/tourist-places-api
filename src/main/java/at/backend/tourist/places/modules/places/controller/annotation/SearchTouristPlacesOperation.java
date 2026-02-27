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
    summary = "Search tourist places",
    description = "Search for tourist places based on various criteria such as name, description, rating, country, category, price range, and opening hours. Supports pagination and sorting. Returns a paginated list of matching tourist places."
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Tourist places found successfully",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Success Response",
                value = ApiResponseExamples.TOURIST_PLACES
            )
        )
    ),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid search parameters",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseWrapper.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Bad Request",
                value = ApiResponseExamples.BAD_REQUEST
            )
        )
    )
})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchTouristPlacesOperation {
}

