package at.backend.tourist.places.modules.places.auto_mappers;

import at.backend.tourist.places.modules.places.dto.PlaceCategoryDTO;
import at.backend.tourist.places.modules.places.dto.PlaceCategoryInsertDTO;
import at.backend.tourist.places.modules.places.model.PlaceCategory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T12:37:48-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class PlaceCategoryMapperImpl implements PlaceCategoryMapper {

    @Override
    public PlaceCategoryDTO entityToDTO(PlaceCategory placeCategory) {
        if ( placeCategory == null ) {
            return null;
        }

        PlaceCategoryDTO placeCategoryDTO = new PlaceCategoryDTO();

        placeCategoryDTO.setId( placeCategory.getId() );
        placeCategoryDTO.setName( placeCategory.getName() );
        placeCategoryDTO.setDescription( placeCategory.getDescription() );

        return placeCategoryDTO;
    }

    @Override
    public PlaceCategory DTOToEntity(PlaceCategoryInsertDTO insertDTO) {
        if ( insertDTO == null ) {
            return null;
        }

        PlaceCategory placeCategory = new PlaceCategory();

        placeCategory.setName( insertDTO.getName() );
        placeCategory.setDescription( insertDTO.getDescription() );

        return placeCategory;
    }

    @Override
    public PlaceCategory DTOToEntity(PlaceCategoryDTO activityDTO) {
        if ( activityDTO == null ) {
            return null;
        }

        PlaceCategory placeCategory = new PlaceCategory();

        placeCategory.setId( activityDTO.getId() );
        placeCategory.setName( activityDTO.getName() );
        placeCategory.setDescription( activityDTO.getDescription() );

        return placeCategory;
    }
}
