import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {RestclientService} from "./restclient.service";
import {Observable} from "rxjs";
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private router: Router, private http: HttpClient, private rest: RestclientService) {  }

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
    let params = new HttpParams();
    params = params.append('username', username);
    params = params.append('password', password);
    return this.http.post(this.rest.API_URL + 'user/login',{},
        {
          withCredentials: true,
          params: params,
          responseType: 'text',
        });
  }
}