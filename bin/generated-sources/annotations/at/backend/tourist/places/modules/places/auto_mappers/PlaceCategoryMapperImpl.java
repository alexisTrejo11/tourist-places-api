package at.backend.tourist.places.modules.places.auto_mappers;

import at.backend.tourist.places.modules.places.dto.PlaceCategoryDTO;
import at.backend.tourist.places.modules.places.dto.PlaceCategoryInsertDTO;
import at.backend.tourist.places.modules.places.model.PlaceCategory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T11:43:17-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class PlaceCategoryMapperImpl implements PlaceCategoryMapper {

    @Override
    public PlaceCategoryDTO entityToDTO(PlaceCategory placeCategory) {
        if ( placeCategory == null ) {
            return null;
        }

        PlaceCategoryDTO placeCategoryDTO = new PlaceCategoryDTO();

        placeCategoryDTO.setDescription( placeCategory.getDescription() );
        placeCategoryDTO.setId( placeCategory.getId() );
        placeCategoryDTO.setName( placeCategory.getName() );

        return placeCategoryDTO;
    }

    @Override
    public PlaceCategory DTOToEntity(PlaceCategoryInsertDTO insertDTO) {
        if ( insertDTO == null ) {
            return null;
        }

        PlaceCategory placeCategory = new PlaceCategory();

        placeCategory.setDescription( insertDTO.getDescription() );
        placeCategory.setName( insertDTO.getName() );

        return placeCategory;
    }

    @Override
    public PlaceCategory DTOToEntity(PlaceCategoryDTO activityDTO) {
        if ( activityDTO == null ) {
            return null;
        }

        PlaceCategory placeCategory = new PlaceCategory();

        placeCategory.setDescription( activityDTO.getDescription() );
        placeCategory.setId( activityDTO.getId() );
        placeCategory.setName( activityDTO.getName() );

        return placeCategory;
    }
}
