package at.backend.tourist.places.modules.places.auto_mappers;

import at.backend.tourist.places.modules.places.dto.PlaceCategoryDTO;
import at.backend.tourist.places.modules.places.dto.PlaceCategoryInsertDTO;
import at.backend.tourist.places.modules.places.model.PlaceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaceCategoryMapper {
    PlaceCategoryDTO entityToDTO(PlaceCategory placeCategory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "places", ignore = true)
    PlaceCategory DTOToEntity(PlaceCategoryInsertDTO insertDTO);

    @Mapping(target = "places", ignore = true)
    PlaceCategory DTOToEntity(PlaceCategoryDTO activityDTO);
}