package at.backend.tourist.places.modules.user.controller;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.modules.user.controller.annotation.*;
import at.backend.tourist.places.modules.review.dto.ReviewDTO;
import at.backend.tourist.places.modules.review.dto.ReviewInsertDTO;
import at.backend.tourist.places.modules.review.dto.ReviewUpdateDTO;
import at.backend.tourist.places.modules.review.service.ReviewService;
import at.backend.tourist.places.modules.auth.jwt.JwtService;
import at.backend.tourist.places.modules.places.service.TouristPlaceService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/api/user/reviews")
@RequiredArgsConstructor
@Tag(name = "User Reviews", description = "User endpoints for creating and managing reviews for tourist places")
@SecurityRequirement(name = "bearerAuth")
public class UserReviewController {

    private final ReviewService reviewService;
    private final JwtService jwtService;
    private final TouristPlaceService touristPlaceService;

    @GetMapping
    @GetUserReviewsOperation
    public ResponseWrapper<Page<ReviewDTO>> getMyReviews(
            HttpServletRequest request,
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field", example = "createdDate")
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @Parameter(description = "Sort direction (ASC/DESC)", example = "DESC")
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        String email = jwtService.getEmailFromRequest(request);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
        );

        return ResponseWrapper.found(reviewService.getReviewByEmail(email, pageable), "Reviews");
    }

    @PostMapping
    @CreateUserReviewOperation
    public ResponseEntity<ResponseWrapper<ReviewDTO>> newReview(
            @Parameter(description = "Details of the review to create", required = true)
            @Valid @RequestBody ReviewInsertDTO insertDTO,
            HttpServletRequest request) {
        String email = jwtService.getEmailFromRequest(request);
        insertDTO.setAuthorEmail(email);
        ReviewDTO createdReview = reviewService.create(insertDTO);
        touristPlaceService.updatePlaceRating(createdReview.getPlaceId());

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(createdReview, "review"));
    }

    @PutMapping
    @UpdateUserReviewOperation
    public ResponseEntity<ResponseWrapper<ReviewDTO>> updateMyReview(
            @Parameter(description = "Details of the review to update", required = true)
            @Valid @RequestBody ReviewUpdateDTO updateDTO,
            HttpServletRequest request) {
        String email = jwtService.getEmailFromRequest(request);

        ReviewDTO createdReview = reviewService.update(updateDTO, email);

        touristPlaceService.updatePlaceRating(createdReview.getPlaceId());

        return ResponseEntity.ok(ResponseWrapper.ok(createdReview, "review", "update"));
    }

    @DeleteMapping("/{id}")
    @DeleteUserReviewOperation
    public ResponseEntity<ResponseWrapper<Void>> deleteMyReview(
            @Parameter(description = "ID of the review to be deleted", example = "1")
            @PathVariable Long id,
            HttpServletRequest request) {
        String email = jwtService.getEmailFromRequest(request);

        reviewService.delete(id, email);

        touristPlaceService.updatePlaceRating(id);

        return ResponseEntity.ok(ResponseWrapper.ok("review", "Delete"));
    }
}