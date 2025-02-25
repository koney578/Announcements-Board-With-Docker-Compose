import { Component } from '@angular/core';
import {NgIf} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {RegisterRequest} from "../../interfaces/RegisterRequest";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-register',
  standalone: true,
    imports: [
        NgIf,
        ReactiveFormsModule
    ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerForm: FormGroup;

  constructor(private authenticationService: AuthenticationService, private tokenService: TokenService, private router : Router) {
    this.registerForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
    });
  }

  get username() {
    return this.registerForm.get('username');
  };

  get email() {
    return this.registerForm.get('email');
  };

  get password() {
    return this.registerForm.get('password');
  };

  onSubmit() {
    if (this.registerForm.valid) {
      const registerRequest: RegisterRequest = {
        username: this.registerForm.value.username,
        email: this.registerForm.value.email,
        password: this.registerForm.value.password,
      };
      this.tokenService.removeToken();
      this.authenticationService.register(registerRequest)
        .subscribe(response => {
          this.router.navigate(['']);
          alert("Zarejestrowano pomyślnie!")
        }, error => {
          alert(error.error.message);
        });
    }
  }
}
