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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import badminton_booking_backend.dto.CompetitionRequest;
import badminton_booking_backend.dto.JoinRequest;
import badminton_booking_backend.entity.Competition;
import badminton_booking_backend.entity.CompetitionParticipant;
import badminton_booking_backend.service.CompetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Feature 1 & 2: Competition REST endpoints implemented with @RestController/@RequestMapping and HTTP-verb annotations.
@RestController
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;

    // Feature 4: CRUD Read-all
    @GetMapping
    public List<Competition> getCompetitions() {
        return competitionService.getAllCompetitions();
    }

    // Feature 1 & 4: path variable + CRUD Read-by-id
    @GetMapping("/{competitionId}")
    public Competition getCompetition(@PathVariable Long competitionId) {
        return competitionService.getCompetition(competitionId);
    }

    // Feature 4: CRUD Create
    @PostMapping
    public ResponseEntity<Competition> createCompetition(@Valid @RequestBody CompetitionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(competitionService.createCompetition(request));
    }

    // Feature 4: CRUD Update
    @PutMapping("/{competitionId}")
    public Competition updateCompetition(@PathVariable Long competitionId, @Valid @RequestBody CompetitionRequest request) {
        return competitionService.updateCompetition(competitionId, request);
    }

    // Feature 4: CRUD Delete
    @DeleteMapping("/{competitionId}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable Long competitionId) {
        competitionService.deleteCompetition(competitionId);
        return ResponseEntity.noContent().build();
    }

    // Feature 1: nested path variable identifies parent (competitionId); business action rather than plain CRUD
    @PostMapping("/{competitionId}/participants")
    public ResponseEntity<CompetitionParticipant> joinCompetition(@PathVariable Long competitionId, @RequestBody JoinRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(competitionService.joinCompetition(competitionId, request.userId()));
    }

    // Feature 1: two path variables (competitionId, userId) on the same endpoint
    @DeleteMapping("/{competitionId}/participants/{userId}")
    public ResponseEntity<Void> leaveCompetition(@PathVariable Long competitionId, @PathVariable Long userId) {
        competitionService.leaveCompetition(competitionId, userId);
        return ResponseEntity.noContent().build();
    }
}
