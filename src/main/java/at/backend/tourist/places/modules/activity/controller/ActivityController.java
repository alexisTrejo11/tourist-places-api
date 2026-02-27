package at.backend.tourist.places.modules.activity.controller;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.modules.activity.controller.annotation.*;
import at.backend.tourist.places.modules.activity.dtos.ActivityDTO;
import at.backend.tourist.places.modules.activity.dtos.ActivityInsertDTO;
import at.backend.tourist.places.modules.activity.service.ActivityService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/activities")
@Tag(name = "Activities", description = "Endpoints for managing activities")
public class ActivityController {
  private final ActivityService activityService;

  @Autowired
  public ActivityController(ActivityService activityService) {
    this.activityService = activityService;
  }

  @GetMapping
  @GetAllActivitiesOperation
  public ResponseEntity<ResponseWrapper<List<ActivityDTO>>> getAllActivities() {
    List<ActivityDTO> activities = activityService.getAll();

    return ResponseEntity.ok(ResponseWrapper.found(activities, "Activities"));
  }

  @GetMapping("/{id}")
  @GetActivityByIdOperation
  public ResponseEntity<ResponseWrapper<ActivityDTO>> getActivityById(
      @Parameter(description = "Unique identifier of the activity to retrieve", example = "1", required = true)
      @PathVariable Long id) {
    ActivityDTO activity = activityService.getById(id);

    return ResponseEntity.ok(ResponseWrapper.found(activity, "activity"));
  }

  @GetMapping("/tourist_place/{place_id}")
  @GetActivitiesByTouristPlaceOperation
  public ResponseEntity<ResponseWrapper<List<ActivityDTO>>> getByTouristPlaceId(
      @Parameter(description = "Unique identifier of the tourist place", example = "101", required = true)
      @PathVariable Long place_id) {
    List<ActivityDTO> activities = activityService.getByTouristPlace(place_id);

    return ResponseEntity.ok(ResponseWrapper.found(activities, "Activities"));
  }

  @PostMapping
  @CreateActivityOperation
  public ResponseEntity<ResponseWrapper<ActivityDTO>> createActivity(
      @Parameter(description = "Details of the activity to create", required = true)
      @RequestBody ActivityInsertDTO insertDTO) {
    ActivityDTO createdActivity = activityService.create(insertDTO);
    return ResponseEntity.status(201).body(ResponseWrapper.created(createdActivity, "activity"));
  }

  @DeleteMapping("/{id}")
  @DeleteActivityOperation
  public ResponseEntity<ResponseWrapper<Void>> deleteActivity(
      @Parameter(description = "Unique identifier of the activity to delete", example = "1", required = true)
      @PathVariable Long id) {

    activityService.delete(id);
    return ResponseEntity.status(204).body(ResponseWrapper.deleted("activity"));
  }
}
