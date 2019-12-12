import { Injectable } from '@angular/core';
import * as Keycloak from 'keycloak-js';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  config
  private keycloakAuth: any;

  constructor(private http: HttpClient) { }

  init(config): Promise<any> {
    return new Promise((resolve, reject) => {
      this.keycloakAuth = Keycloak(config);
      this.keycloakAuth.init({'onLoad': 'login-required'})
        .success(() => {
          console.log('keycloack success')
          console.log(this.getToken())
          resolve();
        })
        .error(() => {
          console.log('keycloack error')
          reject();
        });
    });
  }

  login() {
    return this.http.post('http://localhost:9080/auth/realms/railway/protocol/openid-connect/token', {
          headers: new HttpHeaders().append('Content-Type', 'application/x-www-form-urlencoded'),
          params: new HttpParams()
            .append('grant_type', 'password')
            .append('client_id', 'web_app')
            .append('username', 'admin')
            .append('password', 'admin')
        })
  }

  getToken(): string {
    return this.keycloakAuth.token;
  }
}
