package badminton_booking_backend.service;

import org.springframework.stereotype.Service;

import badminton_booking_backend.dto.UserUpdateRequest;
import badminton_booking_backend.entity.User;
import badminton_booking_backend.exception.ResourceNotFoundException;
import badminton_booking_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " was not found"));
    }

    public User updateUser(Long userId, UserUpdateRequest request) {
        User user = getUser(userId);
        if (request.name() != null) {
            user.setName(request.name());
        }
        if (request.phone() != null) {
            user.setPhone(request.phone());
        }
        if (request.profileImage() != null) {
            user.setProfileImage(request.profileImage());
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = getUser(userId);
        userRepository.delete(user);
    }
}
