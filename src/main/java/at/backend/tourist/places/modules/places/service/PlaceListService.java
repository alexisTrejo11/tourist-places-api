package at.backend.tourist.places.modules.places.service;

import at.backend.tourist.places.modules.places.dto.PlaceListDTO;
import at.backend.tourist.places.modules.places.dto.PlaceListInsertDTO;
import at.backend.tourist.places.core.service.CommonService;

import java.util.List;
import java.util.Set;

public interface PlaceListService extends CommonService<PlaceListDTO, PlaceListInsertDTO> {
    PlaceListDTO removePlaces(Long placeListId, Set<Long> placeIds);
    List<PlaceListDTO> getByUserId(Long userId);
    PlaceListDTO addPlaces(Long placeListId, Set<Long> placeIds);
    void delete(Long id, Long userId);

}