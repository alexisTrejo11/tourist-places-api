package at.backend.tourist.places.modules.places.service;

import at.backend.tourist.places.modules.places.dto.TouristPlaceDTO;
import at.backend.tourist.places.modules.places.dto.TouristPlaceInsertDTO;
import at.backend.tourist.places.modules.places.dto.TouristPlaceSearchDTO;
import at.backend.tourist.places.core.service.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface TouristPlaceService extends CommonService<TouristPlaceDTO, TouristPlaceInsertDTO> {
    Page<TouristPlaceDTO> searchTouristPlaces(TouristPlaceSearchDTO searchDTO, Pageable pageable);
    List<TouristPlaceDTO> getByCountry(Long countryId);
    List<TouristPlaceDTO> getByCategory(Long categoryId);
    List<TouristPlaceDTO> getByIdList(Set<Long> idsList);

    void updatePlaceRating(Long id);
}