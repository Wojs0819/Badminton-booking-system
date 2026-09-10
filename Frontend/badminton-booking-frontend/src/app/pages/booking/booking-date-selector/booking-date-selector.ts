import { Component, EventEmitter, Input, Output } from '@angular/core';

// A5: another @Input/@Output example - parent Booking component supplies the date, this emits changes back.
@Component({
  imports: [],
  selector: 'app-booking-date-selector',
  styleUrl: './booking-date-selector.css',
  templateUrl: './booking-date-selector.html',
})
export class BookingDateSelector {
  @Input() date = '';
  @Output() dateChange = new EventEmitter<string>();

  onDateInput(value: string): void {
    this.dateChange.emit(value);
  }
}
