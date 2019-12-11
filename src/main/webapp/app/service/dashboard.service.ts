import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { RestclientService } from './restclient.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  constructor(
    private http: HttpClient,
    private restClient: RestclientService
  ) {}

  // public getDashboardOverview(project: number): Observable<DashboardOverviewDto> {
  //   const httpOptions = {
  //     headers: new HttpHeaders({
  //       'Content-Type': 'application/json'
  //     }),
  //     params: new HttpParams().set('project', project.toString())
  //   };

  //   return this.http
  //     .get<DashboardOverviewDto>(this.restClient.API_URL + 'api/dashboard', httpOptions);
  // }
}
