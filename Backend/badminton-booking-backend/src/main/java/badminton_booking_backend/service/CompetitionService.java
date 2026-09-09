package badminton_booking_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import badminton_booking_backend.dto.CompetitionRequest;
import badminton_booking_backend.entity.Competition;
import badminton_booking_backend.entity.CompetitionParticipant;
import badminton_booking_backend.entity.User;
import badminton_booking_backend.entity.Venue;
import badminton_booking_backend.exception.ConflictException;
import badminton_booking_backend.exception.ResourceNotFoundException;
import badminton_booking_backend.repository.CompetitionParticipantRepository;
import badminton_booking_backend.repository.CompetitionRepository;
import badminton_booking_backend.repository.UserRepository;
import badminton_booking_backend.repository.VenueRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public Competition getCompetition(Long competitionId) {
        return competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ResourceNotFoundException("Competition with ID " + competitionId + " was not found"));
    }

    public Competition createCompetition(CompetitionRequest request) {
        User creator = userRepository.findById(request.creatorId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + request.creatorId() + " was not found"));
        Venue venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue with ID " + request.venueId() + " was not found"));

        Competition competition = Competition.builder()
                .creator(creator)
                .name(request.name())
                .description(request.description())
                .venue(venue)
                .competitionDate(request.competitionDate())
                .registrationDeadline(request.registrationDeadline())
                .skillLevel(request.skillLevel())
                .maxParticipants(request.maxParticipants())
                .status(Competition.Status.OPEN)
                .build();

        return competitionRepository.save(competition);
    }

    public Competition updateCompetition(Long competitionId, CompetitionRequest request) {
        Competition competition = getCompetition(competitionId);
        competition.setName(request.name());
        competition.setDescription(request.description());
        competition.setCompetitionDate(request.competitionDate());
        competition.setRegistrationDeadline(request.registrationDeadline());
        competition.setSkillLevel(request.skillLevel());
        competition.setMaxParticipants(request.maxParticipants());
        return competitionRepository.save(competition);
    }

    public void deleteCompetition(Long competitionId) {
        Competition competition = getCompetition(competitionId);
        competitionRepository.delete(competition);
    }

    public CompetitionParticipant joinCompetition(Long competitionId, Long userId) {
        Competition competition = getCompetition(competitionId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " was not found"));

        participantRepository.findByCompetitionIdAndUserId(competitionId, userId).ifPresent(p -> {
            throw new ConflictException("User has already joined this competition");
        });

        long currentCount = participantRepository.countByCompetitionId(competitionId);
        if (currentCount >= competition.getMaxParticipants()) {
            throw new ConflictException("Competition is already full");
        }

        CompetitionParticipant participant = CompetitionParticipant.builder()
                .competition(competition)
                .user(user)
                .build();
        CompetitionParticipant saved = participantRepository.save(participant);

        if (currentCount + 1 >= competition.getMaxParticipants()) {
            competition.setStatus(Competition.Status.FULL);
            competitionRepository.save(competition);
        }

        return saved;
    }

    public void leaveCompetition(Long competitionId, Long userId) {
        Competition competition = getCompetition(competitionId);
        CompetitionParticipant participant = participantRepository.findByCompetitionIdAndUserId(competitionId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("User " + userId + " has not joined this competition"));
        participantRepository.delete(participant);

        if (competition.getStatus() == Competition.Status.FULL) {
            competition.setStatus(Competition.Status.OPEN);
            competitionRepository.save(competition);
        }
    }
}
