import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { Home } from './pages/home/home';
import { VenueDetail } from './pages/venue-detail/venue-detail';
import { VenueInfo } from './pages/venue-detail/venue-info/venue-info';
import { CourtList } from './pages/venue-detail/court-list/court-list';
import { Booking } from './pages/booking/booking';
import { Activities } from './pages/activities/activities';
import { ActivityDetail } from './pages/activities/activity-detail/activity-detail';
import { Competitions } from './pages/competitions/competitions';
import { CompetitionDetail } from './pages/competitions/competition-detail/competition-detail';
import { Profile } from './pages/profile/profile';
import { Rewards } from './pages/rewards/rewards';
import { Bookings } from './pages/bookings/bookings';
import { NotFound } from './pages/not-found/not-found';

// A11/A12/A13: normal routes, a redirect route, route/query parameters, nested child routes and a wildcard route.
export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'home', component: Home },
  {
    // A13: nested child routes rendered inside VenueDetail's own <router-outlet>.
    path: 'venues/:venueId',
    component: VenueDetail,
    children: [
      { path: '', redirectTo: 'info', pathMatch: 'full' },
      { path: 'info', component: VenueInfo },
      { path: 'courts', component: CourtList },
    ],
  },
  { path: 'venues/:venueId/book', component: Booking },
  { path: 'activities', component: Activities },
  { path: 'activities/:id', component: ActivityDetail },
  { path: 'competitions', component: Competitions },
  { path: 'competitions/:id', component: CompetitionDetail },
  { path: 'profile', component: Profile },
  { path: 'rewards', component: Rewards },
  { path: 'bookings', component: Bookings },
  { path: '**', component: NotFound },
];
