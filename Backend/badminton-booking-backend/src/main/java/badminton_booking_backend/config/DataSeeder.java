package badminton_booking_backend.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import badminton_booking_backend.entity.Activity;
import badminton_booking_backend.entity.Booking;
import badminton_booking_backend.entity.Competition;
import badminton_booking_backend.entity.Court;
import badminton_booking_backend.entity.Reward;
import badminton_booking_backend.entity.User;
import badminton_booking_backend.entity.Venue;
import badminton_booking_backend.repository.ActivityRepository;
import badminton_booking_backend.repository.BookingRepository;
import badminton_booking_backend.repository.CompetitionRepository;
import badminton_booking_backend.repository.CourtRepository;
import badminton_booking_backend.repository.RewardRepository;
import badminton_booking_backend.repository.UserRepository;
import badminton_booking_backend.repository.VenueRepository;
import lombok.RequiredArgsConstructor;

// Feature 3: seeds the database with initial records for every domain entity on first run
// (Spring Data JPA repositories + Hibernate ORM create the schema; this class populates it),
// so the API returns real database records instead of empty lists.
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final CourtRepository courtRepository;
    private final BookingRepository bookingRepository;
    private final ActivityRepository activityRepository;
    private final CompetitionRepository competitionRepository;
    private final RewardRepository rewardRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        User alice = userRepository.save(User.builder()
                .name("Alice Tan").email("alice@example.com").password(passwordEncoder.encode("password123"))
                .phone("91234567").role("USER").points(150).build());
        User ben = userRepository.save(User.builder()
                .name("Ben Lim").email("ben@example.com").password(passwordEncoder.encode("password123"))
                .phone("92345678").role("USER").points(80).build());
        User charlie = userRepository.save(User.builder()
                .name("Charlie Ong").email("charlie@example.com").password(passwordEncoder.encode("password123"))
                .phone("93456789").role("USER").points(220).build());

        Venue riverside = venueRepository.save(Venue.builder()
                .name("Riverside Badminton Hall").address("12 Jalan Sungai, Kuala Lumpur")
                .description("A spacious hall with 4 premium courts near the riverside.")
                .openingTime(LocalTime.of(8, 0)).closingTime(LocalTime.of(22, 0))
                .pricePerHour(new BigDecimal("18.00"))
                .imageUrl("https://example.com/images/riverside.jpg").location("Kuala Lumpur").build());
        Venue eastCoast = venueRepository.save(Venue.builder()
                .name("East Coast Sports Centre").address("88 Jalan Pantai Timur, Kuantan")
                .description("Community sports centre with affordable courts.")
                .openingTime(LocalTime.of(7, 0)).closingTime(LocalTime.of(23, 0))
                .pricePerHour(new BigDecimal("12.50"))
                .imageUrl("https://example.com/images/eastcoast.jpg").location("Kuantan").build());
        Venue jurong = venueRepository.save(Venue.builder()
                .name("Petaling Jaya Badminton Arena").address("5 Jalan PJU, Petaling Jaya")
                .description("Modern arena with tournament-grade flooring.")
                .openingTime(LocalTime.of(9, 0)).closingTime(LocalTime.of(21, 0))
                .pricePerHour(new BigDecimal("22.00"))
                .imageUrl("https://example.com/images/jurong.jpg").location("Petaling Jaya").build());

        Court riverside1 = courtRepository.save(Court.builder().venue(riverside).courtNumber(1).courtName("Court A").courtType("Indoor").status(Court.Status.AVAILABLE).build());
        courtRepository.save(Court.builder().venue(riverside).courtNumber(2).courtName("Court B").courtType("Indoor").status(Court.Status.AVAILABLE).build());
        Court eastCoast1 = courtRepository.save(Court.builder().venue(eastCoast).courtNumber(1).courtName("Court 1").courtType("Indoor").status(Court.Status.AVAILABLE).build());
        courtRepository.save(Court.builder().venue(eastCoast).courtNumber(2).courtName("Court 2").courtType("Indoor").status(Court.Status.AVAILABLE).build());
        Court jurong1 = courtRepository.save(Court.builder().venue(jurong).courtNumber(1).courtName("Centre Court").courtType("Indoor").status(Court.Status.AVAILABLE).build());
        courtRepository.save(Court.builder().venue(jurong).courtNumber(2).courtName("Court 2").courtType("Indoor").status(Court.Status.MAINTENANCE).build());

        LocalDate today = LocalDate.now();
        bookingRepository.save(Booking.builder().user(alice).venue(riverside).court(riverside1)
                .bookingDate(today.plusDays(1)).startTime(LocalTime.of(18, 0)).endTime(LocalTime.of(19, 0))
                .status(Booking.Status.CONFIRMED).totalPrice(new BigDecimal("18.00")).build());
        bookingRepository.save(Booking.builder().user(ben).venue(eastCoast).court(eastCoast1)
                .bookingDate(today.plusDays(2)).startTime(LocalTime.of(19, 0)).endTime(LocalTime.of(20, 0))
                .status(Booking.Status.CONFIRMED).totalPrice(new BigDecimal("12.50")).build());
        bookingRepository.save(Booking.builder().user(charlie).venue(jurong).court(jurong1)
                .bookingDate(today.plusDays(3)).startTime(LocalTime.of(17, 0)).endTime(LocalTime.of(18, 30))
                .status(Booking.Status.PENDING).totalPrice(new BigDecimal("33.00")).build());

        activityRepository.save(Activity.builder().creator(alice).title("Friendly Doubles Night")
                .description("Casual doubles session, all levels welcome.").venue(riverside)
                .activityDate(today.plusDays(4)).startTime(LocalTime.of(19, 0)).endTime(LocalTime.of(21, 0))
                .skillLevel("BEGINNER").maxParticipants(4).status(Activity.Status.OPEN).build());
        activityRepository.save(Activity.builder().creator(ben).title("Intermediate Smash Session")
                .description("Looking for players around intermediate level.").venue(eastCoast)
                .activityDate(today.plusDays(5)).startTime(LocalTime.of(20, 0)).endTime(LocalTime.of(22, 0))
                .skillLevel("INTERMEDIATE").maxParticipants(4).status(Activity.Status.OPEN).build());

        competitionRepository.save(Competition.builder().creator(charlie).name("Petaling Jaya Open Championship")
                .description("Annual open badminton tournament.").venue(jurong)
                .competitionDate(today.plusDays(20)).registrationDeadline(today.plusDays(10))
                .skillLevel("ADVANCED").maxParticipants(16).status(Competition.Status.OPEN).build());
        competitionRepository.save(Competition.builder().creator(alice).name("Riverside Friendly Cup")
                .description("Friendly community competition for all levels.").venue(riverside)
                .competitionDate(today.plusDays(15)).registrationDeadline(today.plusDays(7))
                .skillLevel("BEGINNER").maxParticipants(8).status(Competition.Status.OPEN).build());

        rewardRepository.save(Reward.builder().name("Free 1-Hour Court Booking").description("Redeem for one free hour of court time.").pointsRequired(100).imageUrl("https://example.com/images/reward-hour.jpg").build());
        rewardRepository.save(Reward.builder().name("Badminton Shuttlecock Tube").description("A tube of premium shuttlecocks.").pointsRequired(60).imageUrl("https://example.com/images/reward-shuttle.jpg").build());
        rewardRepository.save(Reward.builder().name("10% Booking Discount Voucher").description("10% off your next booking.").pointsRequired(150).imageUrl("https://example.com/images/reward-voucher.jpg").build());
    }
}
