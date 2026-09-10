import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Competition } from '../../../core/models/competition.model';

@Component({
  imports: [DatePipe],
  selector: 'app-competition-card',
  styleUrl: './competition-card.css',
  templateUrl: './competition-card.html',
})
export class CompetitionCard {
  @Input() competition!: Competition;
  @Input() currentUserId: number | null = null;
  @Output() view = new EventEmitter<Competition>();

  get isCreator(): boolean {
    return this.competition.creator.id === this.currentUserId;
  }

  openDetail(): void {
    this.view.emit(this.competition);
  }
}
