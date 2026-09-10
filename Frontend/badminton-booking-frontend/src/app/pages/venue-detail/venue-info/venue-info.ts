import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { Venue } from '../../../core/models/venue.model';
import { VenueService } from '../../../core/services/venue';

// Nested child route (/venues/:venueId/info) rendered inside VenueDetail's <router-outlet>.
@Component({
  imports: [CurrencyPipe],
  selector: 'app-venue-info',
  styleUrl: './venue-info.css',
  templateUrl: './venue-info.html',
})
export class VenueInfo implements OnInit {
  venue: Venue | null = null;
  loading = true;
  errorMessage = '';

  constructor(private venueService: VenueService, private route: ActivatedRoute, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    const venueId = Number(this.route.parent?.snapshot.paramMap.get('venueId'));
    this.venueService.getVenue(venueId).subscribe({
      next: (venue) => {
        this.venue = venue;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.errorMessage = 'Unable to load venue info.';
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }
}
