package at.backend.tourist.places.modules.places.auto_mappers;

import at.backend.tourist.places.modules.places.dto.PlaceListDTO;
import at.backend.tourist.places.modules.places.dto.PlaceListInsertDTO;
import at.backend.tourist.places.modules.places.dto.TouristPlaceDTO;
import at.backend.tourist.places.modules.places.model.PlaceList;
import at.backend.tourist.places.modules.places.model.TouristPlace;
import at.backend.tourist.places.modules.user.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T12:37:48-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class PlaceListMapperImpl implements PlaceListMapper {

    @Override
    public PlaceListDTO entityToDTO(PlaceList placeList) {
        if ( placeList == null ) {
            return null;
        }

        PlaceListDTO placeListDTO = new PlaceListDTO();

        placeListDTO.setUserId( placeListUserId( placeList ) );
        placeListDTO.setId( placeList.getId() );
        placeListDTO.setName( placeList.getName() );
        placeListDTO.setPlaces( touristPlaceSetToTouristPlaceDTOList( placeList.getPlaces() ) );

        return placeListDTO;
    }

    @Override
    public PlaceList DTOToEntity(PlaceListInsertDTO insertDTO) {
        if ( insertDTO == null ) {
            return null;
        }

        PlaceList placeList = new PlaceList();

        placeList.setName( insertDTO.getName() );

        return placeList;
    }

    @Override
    public PlaceList DTOToEntity(PlaceListDTO activityDTO) {
        if ( activityDTO == null ) {
            return null;
        }

        PlaceList placeList = new PlaceList();

        placeList.setId( activityDTO.getId() );
        placeList.setName( activityDTO.getName() );

        return placeList;
    }

    private Long placeListUserId(PlaceList placeList) {
        if ( placeList == null ) {
            return null;
        }
        User user = placeList.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TouristPlaceDTO touristPlaceToTouristPlaceDTO(TouristPlace touristPlace) {
        if ( touristPlace == null ) {
            return null;
        }

        TouristPlaceDTO touristPlaceDTO = new TouristPlaceDTO();

        touristPlaceDTO.setId( touristPlace.getId() );
        touristPlaceDTO.setName( touristPlace.getName() );
        touristPlaceDTO.setDescription( touristPlace.getDescription() );
        touristPlaceDTO.setRating( touristPlace.getRating() );
        touristPlaceDTO.setImage( touristPlace.getImage() );
        touristPlaceDTO.setOpeningHours( touristPlace.getOpeningHours() );
        touristPlaceDTO.setPriceRange( touristPlace.getPriceRange() );

        return touristPlaceDTO;
    }

    protected List<TouristPlaceDTO> touristPlaceSetToTouristPlaceDTOList(Set<TouristPlace> set) {
        if ( set == null ) {
            return null;
        }

        List<TouristPlaceDTO> list = new ArrayList<TouristPlaceDTO>( set.size() );
        for ( TouristPlace touristPlace : set ) {
            list.add( touristPlaceToTouristPlaceDTO( touristPlace ) );
        }

        return list;
    }
}
