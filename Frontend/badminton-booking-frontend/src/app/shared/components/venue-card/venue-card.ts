import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { Venue } from '../../../core/models/venue.model';

// A5: @Input/@Output parent-child communication - parent (VenueList) passes a venue in,
// this component emits the selected venue back out.
@Component({
  imports: [CurrencyPipe],
  selector: 'app-venue-card',
  styleUrl: './venue-card.css',
  templateUrl: './venue-card.html',
})
export class VenueCard {
  @Input() venue!: Venue;
  @Input() index = 0;
  @Input() isFirst = false;
  @Output() venueSelected = new EventEmitter<Venue>();

  select(): void {
    this.venueSelected.emit(this.venue);
  }
}
