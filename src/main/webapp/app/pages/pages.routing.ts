import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProfileComponent } from './profile/profile.component';
import { SettingsComponent } from './settings/settings.component';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { ResultsComponent } from './results/results.component';
import { TrainsComponent } from './trains/trains.component';
import { StationsComponent } from './stations/stations.component';

export const PagesRoutes: Routes =
[
    {
        path: '',
        children: [{
            path: '',
            component: DashboardComponent
        },
        {
            path: 'dashboard',
            component: DashboardComponent
        },
        {
            path: 'stations',
            component: StationsComponent
        },
        {
            path: 'trains',
            component: TrainsComponent
        },
        {
            path: 'results',
            component: ResultsComponent
        },
        {
            path: 'profile',
            component: ProfileComponent
        },
        {
            path: 'settings',
            component: SettingsComponent
        }],
    },
    {
        path: '**',
        component: PagenotfoundComponent
    }
];