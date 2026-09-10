import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-time-slot-list',
  styleUrl: './time-slot-list.css',
  templateUrl: './time-slot-list.html',
})
export class TimeSlotList {
  @Input() slots: string[] = [];
  @Input() bookedTimes: string[] = [];
  @Input() selectedTime: string | null = null;
  @Output() timeSelected = new EventEmitter<string>();

  select(slot: string): void {
    if (!this.bookedTimes.includes(slot)) {
      this.timeSelected.emit(slot);
    }
  }
}
