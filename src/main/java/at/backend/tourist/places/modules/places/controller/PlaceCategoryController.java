package at.backend.tourist.places.modules.places.controller;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.modules.places.controller.annotation.*;
import at.backend.tourist.places.modules.places.dto.PlaceCategoryDTO;
import at.backend.tourist.places.modules.places.dto.PlaceCategoryInsertDTO;
import at.backend.tourist.places.modules.places.service.PlaceCategoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/place_categories")
@Tag(name = "Place Categories", description = "Endpoints for managing place categories such as Historical Sites, Monuments, Natural Wonders, and Museums")
public class PlaceCategoryController {

    @Autowired
    private PlaceCategoryService placeCategoryService;

    @GetMapping
    @GetAllPlaceCategoriesOperation
    public ResponseWrapper<List<PlaceCategoryDTO>> getAllPlaceCategories() {
        return ResponseWrapper.found(placeCategoryService.getAll(), "Place Categories");
    }

    @GetMapping("/{id}")
    @GetPlaceCategoryByIdOperation
    public ResponseWrapper<PlaceCategoryDTO> getPlaceCategoryById(
            @Parameter(description = "Unique identifier of the place category", example = "1", required = true)
            @PathVariable Long id) {
        PlaceCategoryDTO placeCategory = placeCategoryService.getById(id);
        if (placeCategory == null) {
            return ResponseWrapper.notFound("Place Category");
        }
        return ResponseWrapper.found(placeCategory, "Place Category");
    }

    @PostMapping
    @CreatePlaceCategoryOperation
    public ResponseWrapper<PlaceCategoryDTO> createPlaceCategory(
            @Parameter(description = "Details of the place category to create", required = true)
            @RequestBody PlaceCategoryInsertDTO insertDTO) {
        PlaceCategoryDTO createdPlaceCategory = placeCategoryService.create(insertDTO);
        return ResponseWrapper.created(createdPlaceCategory, "Place Category");
    }

    @DeleteMapping("/{id}")
    @DeletePlaceCategoryOperation
    public ResponseWrapper<Void> deletePlaceCategory(
            @Parameter(description = "Unique identifier of the place category to delete", example = "1", required = true)
            @PathVariable Long id) {
        if (placeCategoryService.getById(id) == null) {
            return ResponseWrapper.notFound("Place Category");
        }
        placeCategoryService.delete(id);
        return ResponseWrapper.deleted("Place Category");
    }
}
