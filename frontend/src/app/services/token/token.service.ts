import { Injectable } from '@angular/core';
import {jwtDecode, JwtPayload} from "jwt-decode";
import {UserResponse} from "../../interfaces/UserResponse";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  set token(token: string) {
    localStorage.setItem('token', token);
  }

  get token(): string {
    return localStorage.getItem('token') as string;
  }

  isAdmin(): boolean {
    const userString = localStorage.getItem('user');
    if (userString) {
      const user: UserResponse = JSON.parse(userString);
      return user.userType === 'ADMIN';
    } else {
      return false;
    }
  }

  getUserId(): number | null {
    const userString = localStorage.getItem('user');
    if (userString) {
      const user: UserResponse = JSON.parse(userString);
      return user.id;
    } else {
      return null;
    }
  }

  isTokenExpired(): boolean {
    if (this.token) {
      try {
        let decodedToken: JwtPayload = jwtDecode(this.token);
        return decodedToken && decodedToken.exp
          ? decodedToken.exp < Date.now() / 1000
          : true;
      } catch (e) {
        return true;
      }
    }
    return true;
  }

  removeToken(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }

}
