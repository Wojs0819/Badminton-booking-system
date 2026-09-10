import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

// A11: rendered by the wildcard (**) route.
@Component({
  imports: [RouterLink],
  selector: 'app-not-found',
  styleUrl: './not-found.css',
  templateUrl: './not-found.html',
})
export class NotFound {}
