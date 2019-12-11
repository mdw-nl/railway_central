import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {RestclientService} from './restclient.service';
import {TrainModel} from 'app/shared/model/train.model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TrainsService {
  private projectName ='testproject' //TODO this needs to come from project selection
  constructor(
    private http: HttpClient,
    private restClient: RestclientService
  ) {}

  public getAllTrains(): Observable<Array<TrainModel>> {
    return this.http
      .get<Array<TrainModel>>(this.restClient.API_URL + 'api/projects/' + this.projectName + '/client', {
        headers: new HttpHeaders().append('Content-Type', 'application/json')
      });
  }

  // public registerNewClient() {
  //   return this.http.post<ClientModel>(this.restClient.API_URL + 'api/projects/' + this.projectName + '/client', {
  //     headers: new HttpHeaders().append('Content-Type', 'application/json'),
  //     params: new HttpParams(),
  //     responseType: 'application/json'
  //   });
  // }

  // public downloadClient(clientId) {
  //   return this.http.get(this.restClient.API_URL + 'api/projects/' + this.projectName + '/client/' + clientId, {
  //     responseType: 'blob',
  //     headers: new HttpHeaders().append('Content-Type', 'application/json'),
  //     params: new HttpParams()
  //   });
  // }

  // public deleteClient(clientId) {
  //   return this.http.delete(this.restClient.API_URL + 'api/projects/' + this.projectName + '/client/' + clientId, {
  //     headers: new HttpHeaders().append('Content-Type', 'application/json'),
  //     params: new HttpParams()  
  //   });
  // }
}
