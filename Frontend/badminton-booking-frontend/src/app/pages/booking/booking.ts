import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { Court } from '../../core/models/court.model';
import { CourtAvailability } from '../../core/models/booking.model';
import { Venue } from '../../core/models/venue.model';
import { AuthService } from '../../core/services/auth';
import { BookingService } from '../../core/services/booking';
import { CourtService } from '../../core/services/court';
import { VenueService } from '../../core/services/venue';
import { CourtCard } from '../../shared/components/court-card/court-card';
import { BookingDateSelector } from './booking-date-selector/booking-date-selector';
import { TimeSlotList } from './time-slot-list/time-slot-list';

@Component({
  imports: [ReactiveFormsModule, RouterLink, CurrencyPipe, CourtCard, BookingDateSelector, TimeSlotList],
  selector: 'app-booking',
  styleUrl: './booking.css',
  templateUrl: './booking.html',
})
export class Booking implements OnInit {
  private fb = inject(FormBuilder);

  venue: Venue | null = null;
  courts: Court[] = [];
  availability: CourtAvailability[] = [];
  loading = true;
  submitting = false;
  errorMessage = '';
  successMessage = '';
  timeSlots: string[] = [];

  // A8: reactive form with multiple grouped controls, matching the spec's booking form example.
  bookingForm = this.fb.nonNullable.group({
    venueId: [0, Validators.required],
    courtId: [0, Validators.required],
    bookingDate: ['', Validators.required],
    startTime: ['', Validators.required],
    duration: [1, [Validators.required, Validators.min(1)]],
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private venueService: VenueService,
    private courtService: CourtService,
    private bookingService: BookingService,
    private auth: AuthService,
    private cdr: ChangeDetectorRef,
  ) {}

  get isLoggedIn(): boolean {
    return this.auth.isLoggedIn();
  }

  ngOnInit(): void {
    const venueId = Number(this.route.snapshot.paramMap.get('venueId'));
    // A12: query parameter pre-fills the booking date (/venues/:id/book?date=...).
    const dateParam = this.route.snapshot.queryParamMap.get('date') ?? '';

    this.bookingForm.patchValue({ venueId, bookingDate: dateParam });

    this.venueService.getVenue(venueId).subscribe((venue) => {
      this.venue = venue;
      this.timeSlots = this.buildHourlySlots(venue.openingTime, venue.closingTime);
      this.loading = false;
      this.cdr.markForCheck();
    });

    this.courtService.getCourtsForVenue(venueId).subscribe((courts) => {
      this.courts = courts;
      this.cdr.markForCheck();
    });

    if (dateParam) {
      this.loadAvailability(venueId, dateParam);
    }
  }

  private buildHourlySlots(opening: string, closing: string): string[] {
    const slots: string[] = [];
    let hour = Number(opening.split(':')[0]);
    const endHour = Number(closing.split(':')[0]);
    while (hour < endHour) {
      slots.push(`${String(hour).padStart(2, '0')}:00`);
      hour++;
    }
    return slots;
  }

  onDateChange(date: string): void {
    this.bookingForm.patchValue({ bookingDate: date });
    this.cdr.markForCheck();
    this.loadAvailability(this.bookingForm.value.venueId!, date);
  }

  selectCourt(court: Court): void {
    this.bookingForm.patchValue({ courtId: court.id });
    this.cdr.markForCheck();
  }

  selectTimeSlot(time: string): void {
    this.bookingForm.patchValue({ startTime: time });
    this.cdr.markForCheck();
  }

  get bookedTimesForSelectedCourt(): string[] {
    const courtId = this.bookingForm.value.courtId;
    const courtAvailability = this.availability.find((a) => a.courtId === courtId);
    return courtAvailability ? courtAvailability.bookedSlots.map((slot) => slot.startTime.substring(0, 5)) : [];
  }

  private loadAvailability(venueId: number, date: string): void {
    if (!venueId || !date) return;
    this.bookingService.getAvailability(venueId, date).subscribe((availability) => {
      this.availability = availability;
      this.cdr.markForCheck();
    });
  }

  submitBooking(): void {
    if (this.bookingForm.invalid || !this.venue) {
      this.bookingForm.markAllAsTouched();
      return;
    }

    const currentUser = this.auth.currentUser();
    if (!currentUser) {
      this.errorMessage = 'Please login to make a booking.';
      this.cdr.markForCheck();
      return;
    }

    const { venueId, courtId, bookingDate, startTime, duration } = this.bookingForm.getRawValue();
    const startHour = Number(startTime.split(':')[0]);
    const endTime = `${String(startHour + duration).padStart(2, '0')}:00`;

    this.submitting = true;
    this.errorMessage = '';
    // 10: HttpClient POST request; subscribing to the returned Observable to handle the async result.
    this.bookingService
      .createBooking({
        userId: currentUser.id,
        venueId: venueId!,
        courtId: courtId!,
        bookingDate: bookingDate!,
        startTime,
        endTime,
      })
      .subscribe({
        next: () => {
          this.submitting = false;
          this.successMessage = 'Booking confirmed!';
          // A14: programmatic navigation after successful booking.
          this.router.navigate(['/bookings']);
        },
        error: (err) => {
          this.submitting = false;
          this.errorMessage = err.error?.message || 'Unable to complete booking.';
          this.cdr.markForCheck();
        },
      });
  }
}
