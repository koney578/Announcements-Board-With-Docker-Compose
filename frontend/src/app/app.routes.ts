import { Routes } from '@angular/router';
import {MainComponent} from "./components/main/main.component";
import {RegisterComponent} from "./components/register/register.component";
import {LoginComponent} from "./components/login/login.component";
import {PublicAnnouncementsComponent} from "./components/public-announcements/public-announcements.component";
import {AddAnnouncementComponent} from "./components/add-announcement/add-announcement.component";
import {adminAuthGuard} from "./guards/admin-auth.guard";
import {LogoutComponent} from "./components/logout/logout.component";
import {authGuard} from "./guards/auth.guard";
import {AdminPanelComponent} from "./components/admin-panel/admin-panel.component";
import {MyAnnouncementsComponent} from "./components/my-announcements/my-announcements.component";
import {EditAnnouncementComponent} from "./components/edit-announcement/edit-announcement.component";

export const routes: Routes = [
  {
    path: '',
    title: 'Strona główna',
    component: MainComponent,
  },
  {
    path: 'login',
    title: 'Logowanie',
    component: LoginComponent,
  },
  {
    path: 'register',
    title: 'Rejestracja',
    component: RegisterComponent,
  },
  {
    path: 'announcements',
    title: 'Ogłoszenia',
    component: PublicAnnouncementsComponent,
  },
  {
    path: 'add-announcement',
    title: 'Dodaj ogłoszenie',
    component: AddAnnouncementComponent,
    canActivate: [authGuard],
  },
  {
    path: 'my-announcements',
    title: 'Moje ogłoszenia',
    component: MyAnnouncementsComponent,
    canActivate: [authGuard],
  },
  {
    path: 'admin',
    title: 'Panel administratora',
    component: AdminPanelComponent,
    canActivate: [adminAuthGuard],
  },
  {
    path: 'logout',
    component: LogoutComponent,
  },
  {
    path: 'edit-announcement/:id',
    component: EditAnnouncementComponent,
  },
];
