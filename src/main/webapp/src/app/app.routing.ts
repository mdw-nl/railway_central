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
        path: 'auth',
        loadChildren: './auth/auth.module#AuthModule'
      },
      {
        path: '',
        component: AdminLayoutComponent,
        children: [
          {
            path: 'pages',
            loadChildren: './pages/pages.module#PagesModule'
          }
        ]
      }
    ]
  }
];
