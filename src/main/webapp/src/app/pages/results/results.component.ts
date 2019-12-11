import { Component, OnInit } from '@angular/core';
import { RestclientService } from 'app/service/restclient.service';
import { Router } from '@angular/router';
import { ResultsService } from 'app/service/results.service';

declare const $: any;

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html'
})

export class ResultsComponent implements OnInit {

  constructor(private resultsService: ResultsService,
              private router: Router,
              public restClientService: RestclientService) {}

  public ngOnInit() {

  }
}
