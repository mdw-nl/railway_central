import { Routes } from '@angular/router';

import { LockComponent } from './lock/lock.component';
import { LoginComponent } from './login/login.component';

export const AuthRoutes: Routes = [{
    path: '',
    children: [ {
        path: 'login',
        component: LoginComponent
    }, {
        path: 'lock',
        component: LockComponent
    }]
}];
