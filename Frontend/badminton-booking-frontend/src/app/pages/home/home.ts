import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Venue } from '../../core/models/venue.model';
import { VenueService } from '../../core/services/venue';
import { VenueList } from './venue-list/venue-list';

// A4: Root -> Home -> VenueList -> VenueCard component hierarchy starts here.
@Component({
  imports: [VenueList],
  selector: 'app-home',
  styleUrl: './home.css',
  templateUrl: './home.html',
})
export class Home implements OnInit {
  venues: Venue[] = [];
  loading = true;
  errorMessage = '';
  locationFilter = '';
  maxPriceFilter: number | null = null;

  constructor(private venueService: VenueService, private route: ActivatedRoute, private router: Router, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    // A12: query parameters used for filtering/searching (/home?location=...&maxPrice=...).
    this.route.queryParamMap.subscribe((params) => {
      this.locationFilter = params.get('location') ?? '';
      const maxPrice = params.get('maxPrice');
      this.maxPriceFilter = maxPrice ? Number(maxPrice) : null;
      this.loadVenues();
    });
  }

  private loadVenues(): void {
    this.loading = true;
    this.errorMessage = '';
    const request$ = this.locationFilter || this.maxPriceFilter != null
      ? this.venueService.searchVenues(this.locationFilter || undefined, this.maxPriceFilter ?? undefined)
      : this.venueService.getVenues();

    // 10: subscribing to the Observable to handle loading, success and error states.
    request$.subscribe({
      next: (venues) => {
        this.venues = venues;
        this.loading = false;
        // Marks this view dirty so the async response is reflected on the next change detection pass.
        this.cdr.markForCheck();
      },
      error: () => {
        this.errorMessage = 'Unable to load venues right now.';
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  search(): void {
    this.router.navigate(['/home'], {
      queryParams: {
        location: this.locationFilter || null,
        maxPrice: this.maxPriceFilter || null,
      },
    });
  }

  // A14: programmatic navigation to the venue detail page.
  goToVenue(venue: Venue): void {
    this.router.navigate(['/venues', venue.id]);
  }
}
