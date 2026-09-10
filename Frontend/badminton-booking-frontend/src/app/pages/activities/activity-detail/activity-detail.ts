import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { Activity } from '../../../core/models/activity.model';
import { ActivityService } from '../../../core/services/activity';
import { AuthService } from '../../../core/services/auth';

@Component({
  imports: [DatePipe],
  selector: 'app-activity-detail',
  styleUrl: './activity-detail.css',
  templateUrl: './activity-detail.html',
})
export class ActivityDetail implements OnInit {
  activity: Activity | null = null;
  loading = true;
  actionMessage = '';
  actionError = '';

  constructor(
    private activityService: ActivityService,
    private auth: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  get currentUserId(): number | null {
    return this.auth.currentUser()?.id ?? null;
  }

  get isCreator(): boolean {
    return !!this.activity && this.activity.creator.id === this.currentUserId;
  }

  ngOnInit(): void {
    const activityId = Number(this.route.snapshot.paramMap.get('id'));
    this.activityService.getActivity(activityId).subscribe({
      next: (activity) => {
        this.activity = activity;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  join(): void {
    if (!this.activity || !this.currentUserId) return;
    this.actionMessage = '';
    this.actionError = '';
    this.activityService.joinActivity(this.activity.id, this.currentUserId).subscribe({
      next: () => {
        this.actionMessage = 'You have joined this activity!';
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.actionError = err.error?.message || 'Unable to join activity.';
        this.cdr.markForCheck();
      },
    });
  }

  leave(): void {
    if (!this.activity || !this.currentUserId) return;
    this.actionMessage = '';
    this.actionError = '';
    this.activityService.leaveActivity(this.activity.id, this.currentUserId).subscribe({
      next: () => {
        this.actionMessage = 'You have left this activity.';
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.actionError = err.error?.message || 'Unable to leave activity.';
        this.cdr.markForCheck();
      },
    });
  }

  goBack(): void {
    this.router.navigate(['/activities']);
  }
}
