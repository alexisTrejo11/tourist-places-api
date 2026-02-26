package at.backend.tourist.places.modules.activity.auto_mapper;

import at.backend.tourist.places.modules.activity.dtos.ActivityDTO;
import at.backend.tourist.places.modules.activity.dtos.ActivityInsertDTO;
import at.backend.tourist.places.modules.activity.model.Activity;
import at.backend.tourist.places.modules.places.model.TouristPlace;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T12:36:50-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class ActivityMapperImpl implements ActivityMapper {

    @Override
    public ActivityDTO entityToDTO(Activity activity) {
        if ( activity == null ) {
            return null;
        }

        ActivityDTO activityDTO = new ActivityDTO();

        activityDTO.setTouristPlaceId( activityTouristPlaceId( activity ) );
        activityDTO.setId( activity.getId() );
        activityDTO.setName( activity.getName() );
        activityDTO.setDescription( activity.getDescription() );
        activityDTO.setPrice( activity.getPrice() );
        activityDTO.setDuration( activity.getDuration() );

        return activityDTO;
    }

    @Override
    public Activity DTOToEntity(ActivityInsertDTO insertDTO) {
        if ( insertDTO == null ) {
            return null;
        }

        Activity activity = new Activity();

        activity.setName( insertDTO.getName() );
        activity.setDescription( insertDTO.getDescription() );
        activity.setPrice( insertDTO.getPrice() );
        activity.setDuration( insertDTO.getDuration() );

        return activity;
    }

    @Override
    public Activity DTOToEntity(ActivityDTO activityDTO) {
        if ( activityDTO == null ) {
            return null;
        }

        Activity activity = new Activity();

        activity.setId( activityDTO.getId() );
        activity.setName( activityDTO.getName() );
        activity.setDescription( activityDTO.getDescription() );
        activity.setPrice( activityDTO.getPrice() );
        activity.setDuration( activityDTO.getDuration() );

        return activity;
    }

    private Long activityTouristPlaceId(Activity activity) {
        if ( activity == null ) {
            return null;
        }
        TouristPlace touristPlace = activity.getTouristPlace();
        if ( touristPlace == null ) {
            return null;
        }
        Long id = touristPlace.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
