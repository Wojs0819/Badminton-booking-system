import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Venue } from '../../../core/models/venue.model';
import { VenueCard } from '../../../shared/components/venue-card/venue-card';

// A4: HomeComponent -> VenueListComponent -> VenueCardComponent hierarchy.
@Component({
  imports: [VenueCard],
  selector: 'app-venue-list',
  styleUrl: './venue-list.css',
  templateUrl: './venue-list.html',
})
export class VenueList {
  @Input() venues: Venue[] = [];
  @Output() venueSelected = new EventEmitter<Venue>();
}
