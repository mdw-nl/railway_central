import { Component, OnInit } from '@angular/core';
import { DashboardService } from 'app/service/dashboard.service';
import { RestclientService } from 'app/service/restclient.service';
import { Router } from '@angular/router';

declare const $: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})

export class DashboardComponent implements OnInit {

  constructor(private dashboardService: DashboardService,
              private router: Router,
              public restClientService: RestclientService) {}

  public ngOnInit() {
    // this.dashboardService.getDashboardOverview(
    //     this.restClientService.getProjectIndex())
    //     .subscribe(
    //       x => this.overviewStats = x[0],
    //       err => console.error('Observer got an error: ' + err),
    //       () => console.log('Observer got a complete notification')
    //     );
  }
}
