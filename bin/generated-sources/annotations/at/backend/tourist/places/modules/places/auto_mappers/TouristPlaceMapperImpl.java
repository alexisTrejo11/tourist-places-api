package at.backend.tourist.places.modules.places.auto_mappers;

import at.backend.tourist.places.modules.country.model.Country;
import at.backend.tourist.places.modules.places.dto.TouristPlaceDTO;
import at.backend.tourist.places.modules.places.dto.TouristPlaceInsertDTO;
import at.backend.tourist.places.modules.places.model.PlaceCategory;
import at.backend.tourist.places.modules.places.model.TouristPlace;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T11:43:17-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class TouristPlaceMapperImpl implements TouristPlaceMapper {

    @Override
    public TouristPlaceDTO entityToDTO(TouristPlace review) {
        if ( review == null ) {
            return null;
        }

        TouristPlaceDTO touristPlaceDTO = new TouristPlaceDTO();

        touristPlaceDTO.setCategoryId( reviewCategoryId( review ) );
        touristPlaceDTO.setCountryId( reviewCountryId( review ) );
        touristPlaceDTO.setDescription( review.getDescription() );
        touristPlaceDTO.setId( review.getId() );
        touristPlaceDTO.setImage( review.getImage() );
        touristPlaceDTO.setName( review.getName() );
        touristPlaceDTO.setOpeningHours( review.getOpeningHours() );
        touristPlaceDTO.setPriceRange( review.getPriceRange() );
        touristPlaceDTO.setRating( review.getRating() );

        return touristPlaceDTO;
    }

    @Override
    public TouristPlace DTOToEntity(TouristPlaceInsertDTO insertDTO) {
        if ( insertDTO == null ) {
            return null;
        }

        TouristPlace touristPlace = new TouristPlace();

        touristPlace.setDescription( insertDTO.getDescription() );
        touristPlace.setImage( insertDTO.getImage() );
        touristPlace.setName( insertDTO.getName() );
        touristPlace.setOpeningHours( insertDTO.getOpeningHours() );
        touristPlace.setPriceRange( insertDTO.getPriceRange() );

        return touristPlace;
    }

    @Override
    public TouristPlace DTOToEntity(TouristPlaceDTO reviewDTO) {
        if ( reviewDTO == null ) {
            return null;
        }

        TouristPlace touristPlace = new TouristPlace();

        touristPlace.setDescription( reviewDTO.getDescription() );
        touristPlace.setId( reviewDTO.getId() );
        touristPlace.setImage( reviewDTO.getImage() );
        touristPlace.setName( reviewDTO.getName() );
        touristPlace.setOpeningHours( reviewDTO.getOpeningHours() );
        touristPlace.setPriceRange( reviewDTO.getPriceRange() );
        touristPlace.setRating( reviewDTO.getRating() );

        return touristPlace;
    }

    private Long reviewCategoryId(TouristPlace touristPlace) {
        if ( touristPlace == null ) {
            return null;
        }
        PlaceCategory category = touristPlace.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long reviewCountryId(TouristPlace touristPlace) {
        if ( touristPlace == null ) {
            return null;
        }
        Country country = touristPlace.getCountry();
        if ( country == null ) {
            return null;
        }
        Long id = country.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
