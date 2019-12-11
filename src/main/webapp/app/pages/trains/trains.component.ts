import { Component, OnInit } from '@angular/core';
import { RestclientService } from 'app/service/restclient.service';
import { Router } from '@angular/router';
import { TrainsService } from 'app/service/trains.service';

declare const $: any;

@Component({
  selector: 'app-trains',
  templateUrl: './trains.component.html'
})

export class TrainsComponent implements OnInit {

  constructor(private trainsService: TrainsService,
              private router: Router,
              public restClientService: RestclientService) {}

  public ngOnInit() {

  }
}
