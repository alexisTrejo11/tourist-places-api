package at.backend.tourist.places.modules.places.controller;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.modules.places.controller.annotation.*;
import at.backend.tourist.places.modules.places.dto.PlaceListDTO;
import at.backend.tourist.places.modules.places.service.PlaceListService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/place-lists")
@RequiredArgsConstructor
@Tag(name = "Place Lists", description = "Endpoints for managing user-created lists of favorite tourist places")
public class PlaceListController {

    private final PlaceListService placeListService;

    @GetMapping("/{id}")
    @GetPlaceListByIdOperation
    public ResponseWrapper<PlaceListDTO> getById(
            @Parameter(description = "Unique identifier of the place list", example = "1", required = true)
            @PathVariable Long id) {
        PlaceListDTO placeList = placeListService.getById(id);
        if (placeList == null) {
            return ResponseWrapper.notFound("Place List");
        }
        return ResponseWrapper.found(placeList, "Place List");
    }

    @GetMapping
    @GetAllPlaceListsOperation
    public ResponseWrapper<List<PlaceListDTO>> getAll() {
        List<PlaceListDTO> placeLists = placeListService.getAll();
        return ResponseWrapper.found(placeLists, "Place Lists");
    }

    @GetMapping("/user/{userId}")
    @GetPlaceListsByUserIdOperation
    public ResponseWrapper<List<PlaceListDTO>> getByUserId(
            @Parameter(description = "Unique identifier of the user", example = "101", required = true)
            @PathVariable Long userId) {
        List<PlaceListDTO> placeLists = placeListService.getByUserId(userId);
        return ResponseWrapper.found(placeLists, "Place Lists");
    }

    @DeleteMapping("/{id}")
    @DeletePlaceListOperation
    public ResponseWrapper<Void> delete(
            @Parameter(description = "Unique identifier of the place list to delete", example = "1", required = true)
            @PathVariable Long id) {
        PlaceListDTO placeList = placeListService.getById(id);
        if (placeList == null) {
            return ResponseWrapper.notFound("Place List");
        }
        placeListService.delete(id);
        return ResponseWrapper.deleted("Place List");
    }
}
