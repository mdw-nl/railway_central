import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { RestclientService } from "./restclient.service";
import {Observable} from "rxjs";
import { HttpClient, HttpParams } from '@angular/common/http';
import { KeycloakService } from './keycloak.service';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public KEYCLOAK_URL: String =
      "http://" + environment.keycloakHost + ":" + environment.keycloakPort;
  config = {
    'url': '',
    'realm': 'railway',
    'clientId': 'central'
  };

  constructor(private router: Router,
              private http: HttpClient,
              private rest: RestclientService,
              private keycloakService: KeycloakService) {
    this.config.url = this.KEYCLOAK_URL + '/auth';
    this.keycloakService.init(this.config)
   }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username')
    console.log(!(user === null))
    return !(user === null)
  }

  logOut() {
    sessionStorage.removeItem('username')
    this.http.get(this.rest.API_URL + 'user/logout').subscribe();
    this.router.navigate(['/auth/login'])
  }

  login(username: string, password: string): Observable<String> {
    return new Observable
  }
}