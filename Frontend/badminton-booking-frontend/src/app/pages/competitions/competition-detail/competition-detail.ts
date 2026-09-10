import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { Competition } from '../../../core/models/competition.model';
import { CompetitionService } from '../../../core/services/competition';
import { AuthService } from '../../../core/services/auth';

@Component({
  imports: [DatePipe],
  selector: 'app-competition-detail',
  styleUrl: './competition-detail.css',
  templateUrl: './competition-detail.html',
})
export class CompetitionDetail implements OnInit {
  competition: Competition | null = null;
  loading = true;
  actionMessage = '';
  actionError = '';

  constructor(
    private competitionService: CompetitionService,
    private auth: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  get currentUserId(): number | null {
    return this.auth.currentUser()?.id ?? null;
  }

  get isCreator(): boolean {
    return !!this.competition && this.competition.creator.id === this.currentUserId;
  }

  ngOnInit(): void {
    const competitionId = Number(this.route.snapshot.paramMap.get('id'));
    this.competitionService.getCompetition(competitionId).subscribe({
      next: (competition) => {
        this.competition = competition;
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
    if (!this.competition || !this.currentUserId) return;
    this.actionMessage = '';
    this.actionError = '';
    this.competitionService.joinCompetition(this.competition.id, this.currentUserId).subscribe({
      next: () => {
        this.actionMessage = 'You have joined this competition!';
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.actionError = err.error?.message || 'Unable to join competition.';
        this.cdr.markForCheck();
      },
    });
  }

  leave(): void {
    if (!this.competition || !this.currentUserId) return;
    this.actionMessage = '';
    this.actionError = '';
    this.competitionService.leaveCompetition(this.competition.id, this.currentUserId).subscribe({
      next: () => {
        this.actionMessage = 'You have left this competition.';
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.actionError = err.error?.message || 'Unable to leave competition.';
        this.cdr.markForCheck();
      },
    });
  }

  goBack(): void {
    this.router.navigate(['/competitions']);
  }
}
