package at.backend.tourist.places.modules.activity.service;

import at.backend.tourist.places.modules.activity.dtos.ActivityDTO;
import at.backend.tourist.places.modules.activity.dtos.ActivityInsertDTO;
import at.backend.tourist.places.core.service.CommonService;

import java.util.List;

public interface ActivityService extends CommonService<ActivityDTO, ActivityInsertDTO> {
    List<ActivityDTO> getByTouristPlace(Long touristPlaceId);

}

