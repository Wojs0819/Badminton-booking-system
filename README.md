# Badminton Booking System

A full-stack badminton venue booking web application, built as a learning project to
practise Angular + Spring Boot + MySQL development end to end.

Users can browse badminton venues, check court availability, make bookings, create or join casual
activities and competitions, manage their profile, and redeem loyalty rewards.

## Description

The system is split into two independently runnable projects:

- **Frontend** (`Frontend/badminton-booking-frontend`) — an Angular 22 standalone-component
  single-page app covering login/registration, venue browsing & search, court booking, activities,
  competitions, profile, rewards and booking history.
- **Backend** (`Backend/badminton-booking-backend`) — a Spring Boot 4 REST API backed by MySQL via
  Spring Data JPA/Hibernate, with layered controllers/services/repositories, centralized exception
  handling, and seeded demo data.

### Tech stack

| Layer | Technology |
|---|---|
| Frontend | Angular 22, TypeScript, RxJS, Reactive Forms |
| Backend | Java 21, Spring Boot 4.1, Spring Web, Spring Data JPA, Lombok |
| Database | MySQL 8.4 |
| Auth | Simple application-level login (BCrypt-hashed passwords), not JWT/OAuth |

### Key features

- Venue browsing/search, venue detail with nested Info/Courts tabs
- Court booking flow with date & time-slot selection and availability lookup
- Create/join/leave activities (friendly matches) and competitions
- User profile editing and a rewards catalogue with points-based redemption
- Booking history with cancellation

## Installation

### Requirements

- Node.js 22+ (project developed with Node 26) and npm
- Angular CLI 22.x (`npm install -g @angular/cli`, optional — the local `ng.cmd` in `node_modules`
  works without a global install)
- Java JDK 21
- Maven (the bundled `mvnw`/`mvnw.cmd` wrapper is used, no separate install needed)
- MySQL Server 8.x running locally

### 1. Clone the repository

```bash
git clone https://github.com/Wojs0819/Badminton-booking-system.git
cd Badminton-booking-system
```

### 2. Set up the database

Create the database in MySQL:

```sql
CREATE DATABASE badminton_booking;
```

Configure your own credentials in
`Backend/badminton-booking-backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/badminton_booking
spring.datasource.username=<your-mysql-username>
spring.datasource.password=<your-mysql-password>
```

The schema is created automatically (`spring.jpa.hibernate.ddl-auto=update`), and demo data
(users, venues, courts, bookings, activities, competitions, rewards) is seeded automatically on
first run of an empty database.

### 3. Run the backend (port 8080)

```powershell
cd Backend\badminton-booking-backend
.\mvnw.cmd spring-boot:run
```

### 4. Run the frontend (port 4200)

```powershell
cd Frontend\badminton-booking-frontend
npm install
npm start
```

Then open `http://localhost:4200` in your browser. CORS is already configured on the backend to
accept requests from `http://localhost:4200` during development.

## Usage

Once both servers are running:

1. Register a new account (or use a seeded demo account, e.g. `alice@example.com` / `password123`).
2. Browse venues on the home page, optionally filtering by location/max price.
3. Open a venue to view its info and courts, then click **Book Now** to pick a date, court and time slot.
4. Visit **Activities** / **Competitions** to create your own or join existing ones.
5. Check **My Bookings** and **Rewards** from the navigation bar.

### API overview

All backend endpoints are namespaced under `/api`, for example:

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/venues` | List venues |
| GET | `/api/venues/search?location=&maxPrice=` | Search venues |
| GET | `/api/bookings/availability?venueId=&date=` | Check court availability |
| POST | `/api/bookings` | Create a booking |
| POST | `/api/activities/{id}/participants` | Join an activity |
| POST | `/api/auth/login` | Login |


## Support

This is a personal/academic practice project without a formal support channel. If you run into
issues, please open an issue on the GitHub repository.

## Roadmap

- Replace the simple application-level login with a proper token-based (JWT) auth flow
- Add automated tests (unit + e2e) for both frontend and backend
- Deploy a hosted demo (currently local-only)

## Contributing

This is primarily a solo learning project, but suggestions and pull requests are welcome. If you'd
like to contribute:

1. Open an issue describing the change you'd like to make.
2. Fork the repo and create a feature branch.
3. Make sure the backend compiles (`mvnw.cmd -DskipTests compile`) and the frontend builds
   (`ng build`) before opening a pull request.

## Authors and acknowledgment

Maintained by the repository owner as a full-stack learning project.

## License

No license has been chosen yet for this project. Until one is added, all rights are reserved by
the author. See [choosealicense.com](https://choosealicense.com/) if you'd like to pick one.

## Project status

Actively developed.
