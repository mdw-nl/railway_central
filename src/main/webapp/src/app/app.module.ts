import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppComponent } from './app.component';
import { SidebarModule } from './shared/sidebar/sidebar.module';
import {LoggerModule, NgxLoggerLevel} from 'ngx-logger';
import {environment} from '../environments/environment';
import { FooterModule } from './shared/footer/footer.module';
import { NavbarModule} from './shared/navbar/navbar.module';
import { AdminLayoutComponent } from './layouts/admin/admin-layout.component';
import { AuthLayoutComponent } from './layouts/auth/auth-layout.component';
import { AppRoutes } from './app.routing';

@NgModule({
    imports:      [
        BrowserAnimationsModule,
        FormsModule,
        RouterModule.forRoot(AppRoutes,{
          useHash: false,
          onSameUrlNavigation: 'reload'
        }),
        NgbModule.forRoot(),
        HttpModule,
        HttpClientModule,
        LoggerModule.forRoot({
            level: !environment.production ? NgxLoggerLevel.LOG : NgxLoggerLevel.OFF,
            serverLogLevel: NgxLoggerLevel.OFF
        }),
        SidebarModule,
        NavbarModule,
        FooterModule,
        NgxDatatableModule
    ],
    declarations: [
        AppComponent,
        AdminLayoutComponent,
        AuthLayoutComponent
        ],
    bootstrap:    [ AppComponent ]
})

export class AppModule { }
