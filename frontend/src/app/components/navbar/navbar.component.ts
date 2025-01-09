import { Component } from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  constructor(public tokenService: TokenService, private router: Router) {
  }

  logout() {
    this.tokenService.removeToken();
    this.router.navigateByUrl('/');
  }
}
