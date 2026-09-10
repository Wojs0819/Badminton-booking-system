import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Activity } from '../../../core/models/activity.model';

@Component({
  imports: [DatePipe],
  selector: 'app-activity-card',
  styleUrl: './activity-card.css',
  templateUrl: './activity-card.html',
})
export class ActivityCard {
  @Input() activity!: Activity;
  @Input() currentUserId: number | null = null;
  @Output() view = new EventEmitter<Activity>();

  get isCreator(): boolean {
    return this.activity.creator.id === this.currentUserId;
  }

  openDetail(): void {
    this.view.emit(this.activity);
  }
}
