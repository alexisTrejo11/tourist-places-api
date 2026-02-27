package at.backend.tourist.places.modules.places.controller;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.modules.places.controller.annotation.*;
import at.backend.tourist.places.modules.places.dto.TouristPlaceDTO;
import at.backend.tourist.places.modules.places.dto.TouristPlaceInsertDTO;
import at.backend.tourist.places.modules.places.dto.TouristPlaceSearchDTO;
import at.backend.tourist.places.modules.places.service.TouristPlaceService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/tourist_places")
@Tag(name = "Tourist places", description = "API for managing tourist places")
public class TouristPlaceController {

    @Autowired
    private TouristPlaceService placeService;

    @GetMapping("/search")
    @SearchTouristPlacesOperation
    public ResponseWrapper<Page<TouristPlaceDTO>> searchTouristPlaces(
            @ModelAttribute TouristPlaceSearchDTO searchDto) {

        Sort sort = Sort.by(
                searchDto.getSortDirection().equalsIgnoreCase("desc") ?
                        Sort.Direction.DESC : Sort.Direction.ASC,
                searchDto.getSortBy()
        );

        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(), sort);
        Page<TouristPlaceDTO> results = placeService.searchTouristPlaces(searchDto, pageable);

        return ResponseWrapper.found(results, "Tourist places");
    }

    @GetMapping("/{id}")
    @GetTouristPlaceByIdOperation
    public ResponseWrapper<TouristPlaceDTO> getTouristPlaceById(
            @Parameter(description = "Unique identifier of the tourist place", example = "1", required = true)
            @PathVariable Long id) {
        TouristPlaceDTO place = placeService.getById(id);
        return place == null ? ResponseWrapper.notFound("Tourist Place") : ResponseWrapper.found(place, "Tourist Place");
    }

    @GetMapping("/country/{countryId}")
    @GetTouristPlacesByCountryOperation
    public ResponseWrapper<List<TouristPlaceDTO>> getByCountryId(
            @Parameter(description = "Unique identifier of the country", example = "1", required = true)
            @PathVariable Long countryId) {
        List<TouristPlaceDTO> places = placeService.getByCountry(countryId);
        return places.isEmpty() ? ResponseWrapper.notFound("Tourist places for country ID: " + countryId) : ResponseWrapper.found(places, "Tourist places");
    }

    @GetMapping("/category/{categoryId}")
    @GetTouristPlacesByCategoryOperation
    public ResponseWrapper<List<TouristPlaceDTO>> getByCategoryId(
            @Parameter(description = "Unique identifier of the category", example = "3", required = true)
            @PathVariable Long categoryId) {
        List<TouristPlaceDTO> places = placeService.getByCategory(categoryId);
        return places.isEmpty() ? ResponseWrapper.notFound("Tourist places for Category ID: " + categoryId) : ResponseWrapper.found(places, "Tourist places");
    }


    @PostMapping
    @CreateTouristPlaceOperation
    public ResponseWrapper<TouristPlaceDTO> createTouristPlace(
            @Parameter(description = "Details of the new tourist place to create", required = true)
            @Valid @RequestBody TouristPlaceInsertDTO insertDTO) {
        TouristPlaceDTO createdTouristPlace = placeService.create(insertDTO);
        return ResponseWrapper.created(createdTouristPlace, "Tourist Place");
    }

    @DeleteMapping("/{id}")
    @DeleteTouristPlaceOperation
    public ResponseWrapper<Void> deleteTouristPlace(
            @Parameter(description = "Unique identifier of the tourist place to delete", example = "1", required = true)
            @PathVariable Long id) {
        TouristPlaceDTO place = placeService.getById(id);
        if (place == null) {
            return ResponseWrapper.notFound("Tourist Place");
        }

        placeService.delete(id);
        return ResponseWrapper.deleted("Tourist Place");
    }
}
