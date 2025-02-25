import { Component } from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Router, RouterLink} from "@angular/router";
import {LoginRequest} from "../../interfaces/LoginRequest";
import {CommonModule} from "@angular/common";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private authenticationService: AuthenticationService, private tokenService: TokenService, private router : Router) {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }

  get username() {
    return this.loginForm.get('username');
  };

  get password() {
    return this.loginForm.get('password');
  };

  onSubmit() {
    if (this.loginForm.valid) {
      const loginRequest: LoginRequest = {
        username: this.loginForm.value.username,
        password: this.loginForm.value.password,
      };
      this.tokenService.removeToken();
      this.authenticationService.login(loginRequest)
        .subscribe(response => {
          this.tokenService.token = response.token;
          localStorage.setItem('user', JSON.stringify(response.user));
          this.router.navigate(['']);
        }, error => {
          alert("Nieudana próba logowania - spróbuj ponownie!");
        });
    }
  }
}
