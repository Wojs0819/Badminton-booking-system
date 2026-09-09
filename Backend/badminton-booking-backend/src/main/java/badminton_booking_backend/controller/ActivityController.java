package badminton_booking_backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import badminton_booking_backend.dto.ActivityRequest;
import badminton_booking_backend.dto.JoinRequest;
import badminton_booking_backend.entity.Activity;
import badminton_booking_backend.entity.ActivityParticipant;
import badminton_booking_backend.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Feature 1 & 2: Activity REST endpoints implemented with @RestController/@RequestMapping and HTTP-verb annotations.
@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    // Feature 4: CRUD Read-all
    @GetMapping
    public List<Activity> getActivities() {
        return activityService.getAllActivities();
    }

    // Feature 1 & 5: query parameters (?level=&date=) backing a Spring Data derived query
    @GetMapping("/search")
    public List<Activity> searchActivities(
            @RequestParam(required = false) String level,
            @RequestParam(required = false) LocalDate date) {
        return activityService.searchActivities(level, date);
    }

    // Feature 1 & 4: path variable + CRUD Read-by-id
    @GetMapping("/{activityId}")
    public Activity getActivity(@PathVariable Long activityId) {
        return activityService.getActivity(activityId);
    }

    // Feature 4: CRUD Create
    @PostMapping
    public ResponseEntity<Activity> createActivity(@Valid @RequestBody ActivityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activityService.createActivity(request));
    }

    // Feature 4: CRUD Update
    @PutMapping("/{activityId}")
    public Activity updateActivity(@PathVariable Long activityId, @Valid @RequestBody ActivityRequest request) {
        return activityService.updateActivity(activityId, request);
    }

    // Feature 4: CRUD Delete
    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
        return ResponseEntity.noContent().build();
    }

    // Feature 1: nested path variable identifies parent (activityId); business action rather than plain CRUD
    @PostMapping("/{activityId}/participants")
    public ResponseEntity<ActivityParticipant> joinActivity(@PathVariable Long activityId, @RequestBody JoinRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activityService.joinActivity(activityId, request.userId()));
    }

    // Feature 1: two path variables (activityId, userId) on the same endpoint
    @DeleteMapping("/{activityId}/participants/{userId}")
    public ResponseEntity<Void> leaveActivity(@PathVariable Long activityId, @PathVariable Long userId) {
        activityService.leaveActivity(activityId, userId);
        return ResponseEntity.noContent().build();
    }
}
