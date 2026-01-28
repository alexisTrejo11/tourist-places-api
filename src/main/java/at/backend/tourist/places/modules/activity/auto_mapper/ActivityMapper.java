package at.backend.tourist.places.modules.activity.auto_mapper;

import at.backend.tourist.places.modules.activity.dtos.ActivityDTO;
import at.backend.tourist.places.modules.activity.dtos.ActivityInsertDTO;
import at.backend.tourist.places.modules.activity.model.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    @Mapping(target = "touristPlaceId", source = "touristPlace.id")
    ActivityDTO entityToDTO(Activity activity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "touristPlace", ignore = true)
    Activity DTOToEntity(ActivityInsertDTO insertDTO);

    @Mapping(target = "touristPlace", ignore = true)
    Activity DTOToEntity(ActivityDTO activityDTO);
}
