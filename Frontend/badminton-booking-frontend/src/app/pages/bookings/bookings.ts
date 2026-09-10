import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DatePipe, CurrencyPipe } from '@angular/common';
import { Booking } from '../../core/models/booking.model';
import { AuthService } from '../../core/services/auth';
import { BookingService } from '../../core/services/booking';
import { UserService } from '../../core/services/user';

@Component({
  imports: [DatePipe, CurrencyPipe],
  selector: 'app-bookings',
  styleUrl: './bookings.css',
  templateUrl: './bookings.html',
})
export class Bookings implements OnInit {
  bookings: Booking[] = [];
  loading = true;

  constructor(private userService: UserService, private bookingService: BookingService, private auth: AuthService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    const currentUser = this.auth.currentUser();
    if (!currentUser) {
      this.loading = false;
      return;
    }

    // Derived-query-backed endpoint: bookings for this user, ordered by date descending.
    this.userService.getUserBookings(currentUser.id).subscribe({
      next: (bookings) => {
        this.bookings = bookings;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  // 10: HttpClient DELETE request; the Subscription's callback updates the list once it resolves.
  cancel(booking: Booking): void {
    this.bookingService.cancelBooking(booking.id).subscribe(() => {
      this.bookings = this.bookings.filter((b) => b.id !== booking.id);
      this.cdr.markForCheck();
    });
  }
}
