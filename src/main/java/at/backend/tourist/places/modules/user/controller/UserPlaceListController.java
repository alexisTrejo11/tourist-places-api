package at.backend.tourist.places.modules.user.controller;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.modules.user.controller.annotation.*;
import at.backend.tourist.places.modules.places.dto.PlaceListDTO;
import at.backend.tourist.places.modules.places.dto.PlaceListInsertDTO;
import at.backend.tourist.places.modules.places.service.PlaceListService;
import at.backend.tourist.places.modules.auth.jwt.JwtService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("v1/api/user/place-lists")
@RequiredArgsConstructor
@Tag(name = "User Place Lists", description = "User endpoints for managing personal place lists and favorite destinations")
@SecurityRequirement(name = "bearerAuth")
public class UserPlaceListController {

    private final PlaceListService placeListService;
    private final JwtService jwtService;

    @PostMapping
    @CreateUserPlaceListOperation
    public ResponseEntity<PlaceListDTO> newList(
            @Parameter(description = "Details of the place list to create", required = true)
            @Valid @RequestBody PlaceListInsertDTO insertDTO,
            HttpServletRequest request) {
        Long userId = jwtService.getIdFromRequest(request);
        insertDTO.setUserId(userId);
        PlaceListDTO createdList = placeListService.create(insertDTO);
        return ResponseEntity.ok(createdList);
    }

    @GetMapping("/mine")
    @GetUserPlaceListsOperation
    public ResponseEntity<List<PlaceListDTO>> getMyLists(HttpServletRequest request) {
        Long userId = jwtService.getIdFromRequest(request);
        List<PlaceListDTO> placeLists = placeListService.getByUserId(userId);
        return ResponseEntity.ok(placeLists);
    }

    @PostMapping("/{placeListId}/add-place")
    @AddPlacesToListOperation
    public ResponseEntity<PlaceListDTO> addPlaces(
            @Parameter(description = "ID of the place list to update", required = true)
            @PathVariable Long placeListId,
            @Parameter(description = "Set of place IDs to add", required = true)
            @RequestBody Set<Long> placeIds
    ) {
        PlaceListDTO updatedList = placeListService.addPlaces(placeListId, placeIds);
        return ResponseEntity.ok(updatedList);
    }

    @PostMapping("/{placeListId}/remove-place")
    @RemovePlacesFromListOperation
    public ResponseEntity<PlaceListDTO> removePlaces(
            @Parameter(description = "ID of the place list to update", required = true) @PathVariable Long placeListId,
            @Parameter(description = "Set of place IDs to remove", required = true)
            @RequestBody Set<Long> placeIds
    ) {
        PlaceListDTO updatedList = placeListService.removePlaces(placeListId, placeIds);
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/{id}")
    @DeleteUserPlaceListOperation
    public ResponseEntity<Void> deleteMyList(
            @Parameter(description = "ID of the place list to delete", required = true)
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Long userId = jwtService.getIdFromRequest(request);
        placeListService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}