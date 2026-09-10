import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Court } from '../../../core/models/court.model';

// A5: @Input/@Output used again for court selection (in addition to the venue-card example).
@Component({
  imports: [],
  selector: 'app-court-card',
  styleUrl: './court-card.css',
  templateUrl: './court-card.html',
})
export class CourtCard {
  @Input() court!: Court;
  @Input() selectedCourtId: number | null = null;
  @Output() courtSelected = new EventEmitter<Court>();

  select(): void {
    if (this.court.status === 'AVAILABLE') {
      this.courtSelected.emit(this.court);
    }
  }
}
