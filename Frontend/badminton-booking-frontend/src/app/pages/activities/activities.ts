import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Activity } from '../../core/models/activity.model';
import { Venue } from '../../core/models/venue.model';
import { ActivityService } from '../../core/services/activity';
import { AuthService } from '../../core/services/auth';
import { VenueService } from '../../core/services/venue';
import { ActivityCard } from '../../shared/components/activity-card/activity-card';

// A4: Activities (list) -> ActivityCard component hierarchy.
@Component({
  imports: [ReactiveFormsModule, ActivityCard],
  selector: 'app-activities',
  styleUrl: './activities.css',
  templateUrl: './activities.html',
})
export class Activities implements OnInit {
  private fb = inject(FormBuilder);

  activities: Activity[] = [];
  venues: Venue[] = [];
  loading = true;
  errorMessage = '';
  showCreateForm = false;
  levelFilter = '';

  // A8/A9: reactive form for creating an activity, with validation.
  createForm = this.fb.nonNullable.group({
    title: ['', Validators.required],
    description: [''],
    venueId: [0, Validators.required],
    activityDate: ['', Validators.required],
    startTime: ['', Validators.required],
    endTime: ['', Validators.required],
    skillLevel: [''],
    maxParticipants: [4, [Validators.required, Validators.min(2)]],
  });

  constructor(
    private activityService: ActivityService,
    private venueService: VenueService,
    private auth: AuthService,
    private route: ActivatedRoute,
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

    // A12: query parameter filtering (?level=...).
    this.route.queryParamMap.subscribe((params) => {
      this.levelFilter = params.get('level') ?? '';
      this.loadActivities();
    });
  }

  private loadActivities(): void {
    this.loading = true;
    this.errorMessage = '';
    const request$ = this.levelFilter
      ? this.activityService.searchActivities(this.levelFilter)
      : this.activityService.getActivities();

    request$.subscribe({
      next: (activities) => {
        this.activities = activities;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.errorMessage = 'Unable to load activities right now.';
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  filterByLevel(level: string): void {
    this.router.navigate(['/activities'], { queryParams: { level: level || null } });
  }

  viewActivity(activity: Activity): void {
    this.router.navigate(['/activities', activity.id]);
  }

  createActivity(): void {
    if (this.createForm.invalid) {
      this.createForm.markAllAsTouched();
      return;
    }

    const currentUser = this.auth.currentUser();
    if (!currentUser) return;

    this.activityService
      .createActivity({ ...this.createForm.getRawValue(), creatorId: currentUser.id })
      .subscribe(() => {
        this.showCreateForm = false;
        this.createForm.reset({ maxParticipants: 4 });
        this.cdr.markForCheck();
        this.loadActivities();
      });
  }
}
