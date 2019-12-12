import { Component, OnInit } from '@angular/core';
import { StationsService } from 'app/service/stations.service';
import { RestclientService } from 'app/service/restclient.service';
import { Router } from '@angular/router';

declare const $: any;

@Component({
  selector: 'app-stations',
  templateUrl: './stations.component.html'
})

export class StationsComponent implements OnInit {

  constructor(private stationsService: StationsService,
              private router: Router,
              public restClientService: RestclientService) {}

  public ngOnInit() {

  }
}
