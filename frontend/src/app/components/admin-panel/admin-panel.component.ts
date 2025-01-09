import { Component } from '@angular/core';
import {AdminAnnouncementsComponent} from "../admin-announcements/admin-announcements.component";
import {CategoryComponent} from "../category/category.component";
import {NgClass, NgIf} from "@angular/common";

@Component({
  selector: 'app-admin-panel',
  standalone: true,
  imports: [
    AdminAnnouncementsComponent,
    CategoryComponent,
    NgIf,
    NgClass
  ],
  templateUrl: './admin-panel.component.html',
  styleUrl: './admin-panel.component.css'
})
export class AdminPanelComponent {
  currentTab: string = 'announcements';

  switchTab(tab: string) {
    this.currentTab = tab;
  }

}
