import {Injectable} from '@angular/core';
import {LoginRequest} from "../../interfaces/LoginRequest";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {LoginResponse} from "../../interfaces/LoginResponse";
import {RegisterRequest} from "../../interfaces/RegisterRequest";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) { }

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(`http://localhost:8080/api/v1/authentication/login`, loginRequest);
  }

  register(registerRequest: RegisterRequest): Observable<any> {
    return this.httpClient.post<any>(`http://localhost:8080/api/v1/authentication/register`, registerRequest)
  }
}
