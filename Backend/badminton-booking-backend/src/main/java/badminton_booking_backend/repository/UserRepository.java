package badminton_booking_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import badminton_booking_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}