import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-logout',
  standalone: true,
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent {

  constructor(private tokenService: TokenService, private router: Router) {
    this.tokenService.removeToken();
    this.router.navigateByUrl('/');
  }

}
