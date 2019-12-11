import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { RestclientService } from './restclient.service';
import { Observable } from 'rxjs';
import { ResultModel } from 'app/shared/model/result.model';

@Injectable({
  providedIn: 'root'
})
export class ResultsService {
  constructor(
    private http: HttpClient,
    private restClient: RestclientService
  ) {}

  public getAllTrains(): Observable<Array<ResultModel>> {
    return this.http
      .get<Array<ResultModel>>(this.restClient.API_URL + 'URL TODO', {
        headers: new HttpHeaders().append('Content-Type', 'application/json')
      });
  }

}
