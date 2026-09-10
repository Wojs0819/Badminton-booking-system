import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Court } from '../../../core/models/court.model';
import { CourtService } from '../../../core/services/court';
import { CourtCard } from '../../../shared/components/court-card/court-card';

// Nested child route (/venues/:venueId/courts) - A4: VenueDetail -> CourtList -> CourtCard.
@Component({
  imports: [CourtCard],
  selector: 'app-court-list',
  styleUrl: './court-list.css',
  templateUrl: './court-list.html',
})
export class CourtList implements OnInit {
  courts: Court[] = [];
  loading = true;
  errorMessage = '';

  constructor(private courtService: CourtService, private route: ActivatedRoute, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    const venueId = Number(this.route.parent?.snapshot.paramMap.get('venueId'));
    this.courtService.getCourtsForVenue(venueId).subscribe({
      next: (courts) => {
        this.courts = courts;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.errorMessage = 'Unable to load courts for this venue.';
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }
}
