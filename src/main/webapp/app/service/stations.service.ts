import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { RestclientService } from './restclient.service';
import { Observable } from 'rxjs';
import { StationModel } from 'app/shared/model/station.model';

@Injectable({
  providedIn: 'root'
})
export class StationsService {
  constructor(
    private http: HttpClient,
    private restClient: RestclientService
  ) {}

  public getAllTrains(): Observable<Array<StationModel>> {
    return this.http
      .get<Array<StationModel>>(this.restClient.API_URL + 'URL TODO', {
        headers: new HttpHeaders().append('Content-Type', 'application/json')
      });
  }

}
