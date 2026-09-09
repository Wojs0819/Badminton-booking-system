package badminton_booking_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import badminton_booking_backend.entity.Court;
import badminton_booking_backend.service.CourtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Feature 1 & 2: Court endpoints nested under /api/venues/{venueId}/courts as well as flat /api/courts/{courtId},
// showing path-variable-based resource identification with @RestController/@xxxMapping annotations.
@RestController
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    // Feature 1 & 4: path variable + CRUD Read-all for a venue's courts
    @GetMapping("/api/venues/{venueId}/courts")
    public List<Court> getCourtsForVenue(@PathVariable Long venueId) {
        return courtService.getCourtsForVenue(venueId);
    }

    // Feature 4: CRUD Create
    @PostMapping("/api/venues/{venueId}/courts")
    public ResponseEntity<Court> createCourt(@PathVariable Long venueId, @Valid @RequestBody Court court) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courtService.createCourt(venueId, court));
    }

    // Feature 4: CRUD Read-by-id
    @GetMapping("/api/courts/{courtId}")
    public Court getCourt(@PathVariable Long courtId) {
        return courtService.getCourt(courtId);
    }

    // Feature 4: CRUD Update
    @PutMapping("/api/courts/{courtId}")
    public Court updateCourt(@PathVariable Long courtId, @Valid @RequestBody Court court) {
        return courtService.updateCourt(courtId, court);
    }

    // Feature 4: CRUD Delete
    @DeleteMapping("/api/courts/{courtId}")
    public ResponseEntity<Void> deleteCourt(@PathVariable Long courtId) {
        courtService.deleteCourt(courtId);
        return ResponseEntity.noContent().build();
    }
}
