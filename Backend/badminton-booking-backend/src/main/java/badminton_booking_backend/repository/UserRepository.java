package badminton_booking_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import badminton_booking_backend.entity.User;

// Feature 4: extending JpaRepository provides CRUD operations for User out of the box.
public interface UserRepository extends JpaRepository<User, Long> {

    // Feature 5: derived query used during registration/login to look up a user by email
    Optional<User> findByEmail(String email);
}