import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Venue } from '../../core/models/venue.model';
import { VenueService } from '../../core/services/venue';

// A13: nested/child routing - this component hosts a child <router-outlet> for /info and /courts.
@Component({
  imports: [RouterLink, RouterLinkActive, RouterOutlet],
  selector: 'app-venue-detail',
  styleUrl: './venue-detail.css',
  templateUrl: './venue-detail.html',
})
export class VenueDetail implements OnInit {
  venue: Venue | null = null;
  loading = true;
  errorMessage = '';

  constructor(private venueService: VenueService, private route: ActivatedRoute, private router: Router, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    // A12: route parameter identifies which venue to load.
    this.route.paramMap.subscribe((params) => {
      const venueId = Number(params.get('venueId'));
      this.loading = true;
      this.errorMessage = '';
      this.venueService.getVenue(venueId).subscribe({
        next: (venue) => {
          this.venue = venue;
          this.loading = false;
          this.cdr.markForCheck();
        },
        error: () => {
          this.errorMessage = 'Unable to load this venue.';
          this.loading = false;
          this.cdr.markForCheck();
        },
      });
    });
  }

  bookNow(): void {
    if (this.venue) {
      // A14: programmatic navigation to the booking page for this venue.
      this.router.navigate(['/venues', this.venue.id, 'book']);
    }
  }
}
