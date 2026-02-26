package at.backend.tourist.places.modules.review.auto_mapper;

import at.backend.tourist.places.modules.places.model.TouristPlace;
import at.backend.tourist.places.modules.review.dto.ReviewDTO;
import at.backend.tourist.places.modules.review.dto.ReviewInsertDTO;
import at.backend.tourist.places.modules.review.dto.ReviewUpdateDTO;
import at.backend.tourist.places.modules.review.model.Review;
import at.backend.tourist.places.modules.user.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T11:43:17-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDTO entityToDTO(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setPlaceId( reviewPlaceId( review ) );
        Long id1 = reviewAuthorId( review );
        if ( id1 != null ) {
            reviewDTO.setAuthorId( String.valueOf( id1 ) );
        }
        reviewDTO.setComment( review.getComment() );
        reviewDTO.setId( review.getId() );
        if ( review.getRating() != null ) {
            reviewDTO.setRating( review.getRating().intValue() );
        }

        return reviewDTO;
    }

    @Override
    public Review DTOToEntity(ReviewInsertDTO insertDTO) {
        if ( insertDTO == null ) {
            return null;
        }

        Review review = new Review();

        review.setComment( insertDTO.getComment() );
        if ( insertDTO.getRating() != null ) {
            review.setRating( insertDTO.getRating().doubleValue() );
        }

        return review;
    }

    @Override
    public Review DTOToEntity(ReviewDTO reviewDTO) {
        if ( reviewDTO == null ) {
            return null;
        }

        Review review = new Review();

        review.setComment( reviewDTO.getComment() );
        review.setId( reviewDTO.getId() );
        if ( reviewDTO.getRating() != null ) {
            review.setRating( reviewDTO.getRating().doubleValue() );
        }

        return review;
    }

    @Override
    public void update(Review review, ReviewUpdateDTO updateDTO) {
        if ( updateDTO == null ) {
            return;
        }

        review.setComment( updateDTO.getComment() );
        if ( updateDTO.getRating() != null ) {
            review.setRating( updateDTO.getRating().doubleValue() );
        }
        else {
            review.setRating( null );
        }
    }

    private Long reviewPlaceId(Review review) {
        if ( review == null ) {
            return null;
        }
        TouristPlace place = review.getPlace();
        if ( place == null ) {
            return null;
        }
        Long id = place.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long reviewAuthorId(Review review) {
        if ( review == null ) {
            return null;
        }
        User author = review.getAuthor();
        if ( author == null ) {
            return null;
        }
        Long id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
