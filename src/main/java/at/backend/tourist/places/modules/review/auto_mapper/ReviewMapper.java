package at.backend.tourist.places.modules.review.auto_mapper;

import at.backend.tourist.places.modules.review.dto.ReviewDTO;
import at.backend.tourist.places.modules.review.dto.ReviewInsertDTO;
import at.backend.tourist.places.modules.review.dto.ReviewUpdateDTO;
import at.backend.tourist.places.modules.review.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "placeId", source = "place.id")
    @Mapping(target = "authorId", source = "author.id")
    ReviewDTO entityToDTO(Review review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "place", ignore = true)
    Review DTOToEntity(ReviewInsertDTO insertDTO);

    @Mapping(target = "place", ignore = true)
    Review DTOToEntity(ReviewDTO reviewDTO);

    void update(@MappingTarget Review review, ReviewUpdateDTO updateDTO);
}