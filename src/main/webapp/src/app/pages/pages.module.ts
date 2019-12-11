import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { PagesRoutes } from './pages.routing';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { ProfileComponent } from './profile/profile.component';
import { SettingsComponent } from './settings/settings.component';
import { StationsComponent } from './stations/stations.component';
import { TrainsComponent } from './trains/trains.component';
import { ResultsComponent } from './results/results.component';


@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(PagesRoutes),
        FormsModule,
        FormsModule
    ],
    declarations: [
        DashboardComponent,
        StationsComponent,
        TrainsComponent,
        ResultsComponent,
        PagenotfoundComponent,
        ProfileComponent,
        SettingsComponent,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class PagesModule {}
