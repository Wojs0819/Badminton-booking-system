package badminton_booking_backend.controller;

import java.math.BigDecimal;
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

import badminton_booking_backend.entity.Venue;
import badminton_booking_backend.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Feature 1 & 2: resource-oriented REST endpoints for Venue, implemented with @RestController/@RequestMapping
// and the standard @GetMapping/@PostMapping/@PutMapping/@DeleteMapping HTTP-method annotations.
@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    // Feature 4: CRUD (Read-all) via VenueService -> JpaRepository.findAll()
    @GetMapping
    public List<Venue> getVenues() {
        return venueService.getAllVenues();
    }

    // Feature 1 & 5: query parameters (?location=&maxPrice=) backing a Spring Data derived query
    @GetMapping("/search")
    public List<Venue> searchVenues(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) BigDecimal maxPrice) {
        return venueService.searchVenues(location, maxPrice);
    }

    // Feature 1 & 4: path variable identifies the resource; CRUD Read-by-id
    @GetMapping("/{venueId}")
    public Venue getVenue(@PathVariable Long venueId) {
        return venueService.getVenue(venueId);
    }

    // Feature 4: CRUD Create
    @PostMapping
    public ResponseEntity<Venue> createVenue(@Valid @RequestBody Venue venue) {
        return ResponseEntity.status(HttpStatus.CREATED).body(venueService.createVenue(venue));
    }

    // Feature 4: CRUD Update
    @PutMapping("/{venueId}")
    public Venue updateVenue(@PathVariable Long venueId, @Valid @RequestBody Venue venue) {
        return venueService.updateVenue(venueId, venue);
    }

    // Feature 4: CRUD Delete
    @DeleteMapping("/{venueId}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long venueId) {
        venueService.deleteVenue(venueId);
        return ResponseEntity.noContent().build();
    }
}
