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
    date = "2026-02-26T18:31:10-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
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
        touristPlaceDTO.setId( review.getId() );
        touristPlaceDTO.setName( review.getName() );
        touristPlaceDTO.setDescription( review.getDescription() );
        touristPlaceDTO.setRating( review.getRating() );
        touristPlaceDTO.setImage( review.getImage() );
        touristPlaceDTO.setOpeningHours( review.getOpeningHours() );
        touristPlaceDTO.setPriceRange( review.getPriceRange() );

        return touristPlaceDTO;
    }

    @Override
    public TouristPlace DTOToEntity(TouristPlaceInsertDTO insertDTO) {
        if ( insertDTO == null ) {
            return null;
        }

        TouristPlace touristPlace = new TouristPlace();

        touristPlace.setName( insertDTO.getName() );
        touristPlace.setDescription( insertDTO.getDescription() );
        touristPlace.setImage( insertDTO.getImage() );
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

        touristPlace.setId( reviewDTO.getId() );
        touristPlace.setName( reviewDTO.getName() );
        touristPlace.setDescription( reviewDTO.getDescription() );
        touristPlace.setRating( reviewDTO.getRating() );
        touristPlace.setImage( reviewDTO.getImage() );
        touristPlace.setOpeningHours( reviewDTO.getOpeningHours() );
        touristPlace.setPriceRange( reviewDTO.getPriceRange() );

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
