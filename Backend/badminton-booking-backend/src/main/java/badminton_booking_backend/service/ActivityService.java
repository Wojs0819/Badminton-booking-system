package badminton_booking_backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import badminton_booking_backend.dto.ActivityRequest;
import badminton_booking_backend.entity.Activity;
import badminton_booking_backend.entity.ActivityParticipant;
import badminton_booking_backend.entity.User;
import badminton_booking_backend.entity.Venue;
import badminton_booking_backend.exception.ConflictException;
import badminton_booking_backend.exception.ResourceNotFoundException;
import badminton_booking_backend.repository.ActivityParticipantRepository;
import badminton_booking_backend.repository.ActivityRepository;
import badminton_booking_backend.repository.UserRepository;
import badminton_booking_backend.repository.VenueRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity getActivity(Long activityId) {
        return activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Activity with ID " + activityId + " was not found"));
    }

    // Derived query used by GET /api/activities/search
    public List<Activity> searchActivities(String skillLevel, LocalDate date) {
        if (skillLevel != null && date != null) {
            return activityRepository.findBySkillLevelAndActivityDate(skillLevel, date);
        }
        if (skillLevel != null) {
            return activityRepository.findBySkillLevel(skillLevel);
        }
        if (date != null) {
            return activityRepository.findByActivityDate(date);
        }
        return activityRepository.findAll();
    }

    public Activity createActivity(ActivityRequest request) {
        User creator = userRepository.findById(request.creatorId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + request.creatorId() + " was not found"));
        Venue venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue with ID " + request.venueId() + " was not found"));

        Activity activity = Activity.builder()
                .creator(creator)
                .title(request.title())
                .description(request.description())
                .venue(venue)
                .activityDate(request.activityDate())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .skillLevel(request.skillLevel())
                .maxParticipants(request.maxParticipants())
                .status(Activity.Status.OPEN)
                .build();

        return activityRepository.save(activity);
    }

    public Activity updateActivity(Long activityId, ActivityRequest request) {
        Activity activity = getActivity(activityId);
        activity.setTitle(request.title());
        activity.setDescription(request.description());
        activity.setActivityDate(request.activityDate());
        activity.setStartTime(request.startTime());
        activity.setEndTime(request.endTime());
        activity.setSkillLevel(request.skillLevel());
        activity.setMaxParticipants(request.maxParticipants());
        return activityRepository.save(activity);
    }

    public void deleteActivity(Long activityId) {
        Activity activity = getActivity(activityId);
        activityRepository.delete(activity);
    }

    public ActivityParticipant joinActivity(Long activityId, Long userId) {
        Activity activity = getActivity(activityId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " was not found"));

        participantRepository.findByActivityIdAndUserId(activityId, userId).ifPresent(p -> {
            throw new ConflictException("User has already joined this activity");
        });

        long currentCount = participantRepository.countByActivityId(activityId);
        if (currentCount >= activity.getMaxParticipants()) {
            throw new ConflictException("Activity is already full");
        }

        ActivityParticipant participant = ActivityParticipant.builder()
                .activity(activity)
                .user(user)
                .build();
        ActivityParticipant saved = participantRepository.save(participant);

        if (currentCount + 1 >= activity.getMaxParticipants()) {
            activity.setStatus(Activity.Status.FULL);
            activityRepository.save(activity);
        }

        return saved;
    }

    public void leaveActivity(Long activityId, Long userId) {
        Activity activity = getActivity(activityId);
        ActivityParticipant participant = participantRepository.findByActivityIdAndUserId(activityId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("User " + userId + " has not joined this activity"));
        participantRepository.delete(participant);

        if (activity.getStatus() == Activity.Status.FULL) {
            activity.setStatus(Activity.Status.OPEN);
            activityRepository.save(activity);
        }
    }
}
