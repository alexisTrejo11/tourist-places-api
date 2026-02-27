package at.backend.tourist.places.modules.review.controller;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.modules.review.controller.annotation.*;
import at.backend.tourist.places.modules.review.dto.ReviewDTO;
import at.backend.tourist.places.modules.review.dto.ReviewInsertDTO;
import at.backend.tourist.places.modules.review.service.ReviewService;
import at.backend.tourist.places.modules.places.service.TouristPlaceService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Admin endpoints for managing tourist place reviews and ratings")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {

    private final ReviewService reviewService;
    private final TouristPlaceService touristPlaceService;

    @GetMapping
    @GetAllReviewsOperation
    public ResponseWrapper<List<ReviewDTO>> getAllReviews() {
        return ResponseWrapper.found(reviewService.getAll(), "Reviews");
    }

    @GetMapping("tourist_place/{touristPlaceId}")
    @GetReviewsByTouristPlaceOperation
    public ResponseEntity<ResponseWrapper<List<ReviewDTO>>> getByTouristPlaceId(
            @Parameter(description = "Unique identifier of the tourist place", example = "1", required = true)
            @PathVariable Long touristPlaceId) {
        List<ReviewDTO> reviews = reviewService.getByTouristPlace(touristPlaceId);
        return ResponseEntity.ok(ResponseWrapper.found(reviews, "Reviews"));
    }

    @GetMapping("/{id}")
    @GetReviewByIdOperation
    public ResponseEntity<ResponseWrapper<ReviewDTO>> getReviewById(
            @Parameter(description = "Unique identifier of the review", example = "1", required = true)
            @PathVariable Long id) {

        ReviewDTO review = reviewService.getById(id);
        return ResponseEntity.ok(ResponseWrapper.found(review, "review"));
    }

    @PostMapping
    @CreateReviewOperation
    public ResponseEntity<ResponseWrapper<ReviewDTO>> createReview(
            @Parameter(description = "Details of the review to create", required = true)
            @Valid @RequestBody ReviewInsertDTO insertDTO) {

        ReviewDTO createdReview = reviewService.create(insertDTO);
        touristPlaceService.updatePlaceRating(createdReview.getPlaceId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(createdReview, "review"));
    }

    @DeleteMapping("/{id}")
    @DeleteReviewOperation
    public ResponseEntity<ResponseWrapper<Void>> deleteReview(
            @Parameter(description = "Unique identifier of the review to delete", example = "1", required = true)
            @PathVariable Long id) {

        reviewService.delete(id);
        touristPlaceService.updatePlaceRating(id);
        return ResponseEntity.ok(ResponseWrapper.deleted("review"));
    }
}
