import { Routes } from '@angular/router';

import { AdminLayoutComponent } from './layouts/admin/admin-layout.component';
import { AuthLayoutComponent } from './layouts/auth/auth-layout.component';
import { AuthGuardService } from './service/auth-guard.service';

export const AppRoutes: Routes = [
  {
    path: '',
    component: AuthLayoutComponent,
    children: [
      {
        path: 'pages',
        loadChildren: './pages/pages.module#PagesModule'
      },
      {
        path: '',
        component: AdminLayoutComponent,
        canActivate: [AuthGuardService],
        children: [
          {
            path: '',
            loadChildren: './dashboard/dashboard.module#DashboardModule'
          },
          {
            path: 'dashboard',
            loadChildren: './dashboard/dashboard.module#DashboardModule'
          },
          {
            path: 'profile',
            loadChildren: './profile/profile.module#ProfileModule'
          },
          {
            path: 'settings',
            loadChildren: './settings/settings.module#SettingsModule'
          },
          {
            path: '**',
            loadChildren:
              './pagenotfound/pagenotfound.module#PagenotfoundModule'
          }
        ]
      }
    ]
  }
];
