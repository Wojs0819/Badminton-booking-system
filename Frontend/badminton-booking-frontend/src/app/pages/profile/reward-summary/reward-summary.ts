import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  imports: [RouterLink],
  selector: 'app-reward-summary',
  styleUrl: './reward-summary.css',
  templateUrl: './reward-summary.html',
})
export class RewardSummary {
  @Input() points = 0;
}
