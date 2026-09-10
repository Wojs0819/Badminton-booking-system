import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withXhr } from '@angular/common/http';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    // Zone.js-based change detection so property updates inside HttpClient subscribe callbacks re-render the view.
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    // withXhr(): the default Fetch backend isn't patched by zone.js, so responses never trigger change detection.
    provideHttpClient(withXhr())
  ]
};
