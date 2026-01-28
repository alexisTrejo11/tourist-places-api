package at.backend.tourist.places.modules.review.service;

import at.backend.tourist.places.modules.review.dto.ReviewDTO;
import at.backend.tourist.places.modules.review.dto.ReviewInsertDTO;
import at.backend.tourist.places.modules.review.dto.ReviewUpdateDTO;
import at.backend.tourist.places.core.service.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService extends CommonService<ReviewDTO, ReviewInsertDTO> {
    List<ReviewDTO> getByTouristPlace(Long touristPlaceId);
    Page<ReviewDTO> getReviewByEmail(String email, Pageable pageable);

    ReviewDTO update(ReviewUpdateDTO updateDTO, String email);
    void delete(Long id, String email);

}
