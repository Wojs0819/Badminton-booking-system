import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Competition } from '../../core/models/competition.model';
import { Venue } from '../../core/models/venue.model';
import { AuthService } from '../../core/services/auth';
import { CompetitionService } from '../../core/services/competition';
import { VenueService } from '../../core/services/venue';
import { CompetitionCard } from '../../shared/components/competition-card/competition-card';

// A4: Competitions (list) -> CompetitionCard component hierarchy.
@Component({
  imports: [ReactiveFormsModule, CompetitionCard],
  selector: 'app-competitions',
  styleUrl: './competitions.css',
  templateUrl: './competitions.html',
})
export class Competitions implements OnInit {
  private fb = inject(FormBuilder);

  competitions: Competition[] = [];
  venues: Venue[] = [];
  loading = true;
  errorMessage = '';
  showCreateForm = false;

  createForm = this.fb.nonNullable.group({
    name: ['', Validators.required],
    description: [''],
    venueId: [0, Validators.required],
    competitionDate: ['', Validators.required],
    registrationDeadline: ['', Validators.required],
    skillLevel: [''],
    maxParticipants: [8, [Validators.required, Validators.min(2)]],
  });

  constructor(
    private competitionService: CompetitionService,
    private venueService: VenueService,
    private auth: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  get isLoggedIn(): boolean {
    return this.auth.isLoggedIn();
  }

  ngOnInit(): void {
    this.venueService.getVenues().subscribe((venues) => {
      this.venues = venues;
      this.cdr.markForCheck();
    });
    this.loadCompetitions();
  }

  private loadCompetitions(): void {
    this.loading = true;
    this.competitionService.getCompetitions().subscribe({
      next: (competitions) => {
        this.competitions = competitions;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.errorMessage = 'Unable to load competitions right now.';
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  viewCompetition(competition: Competition): void {
    this.router.navigate(['/competitions', competition.id]);
  }

  createCompetition(): void {
    if (this.createForm.invalid) {
      this.createForm.markAllAsTouched();
      return;
    }

    const currentUser = this.auth.currentUser();
    if (!currentUser) return;

    this.competitionService
      .createCompetition({ ...this.createForm.getRawValue(), creatorId: currentUser.id })
      .subscribe(() => {
        this.showCreateForm = false;
        this.createForm.reset({ maxParticipants: 8 });
        this.cdr.markForCheck();
        this.loadCompetitions();
      });
  }
}
