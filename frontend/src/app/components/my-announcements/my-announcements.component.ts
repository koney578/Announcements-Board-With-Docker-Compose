import {Component, OnInit} from '@angular/core';
import {AnnouncementsService} from "../../services/announcements/announcements.service";
import {AnnouncementResponse} from "../../interfaces/AnnouncementResponse";
import {AnnouncementElementComponent} from "../announcement-element/announcement-element.component";
import {NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-announcements',
  standalone: true,
  imports: [
    AnnouncementElementComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './my-announcements.component.html',
  styleUrl: './my-announcements.component.css'
})
export class MyAnnouncementsComponent implements OnInit {
  reviewedAnnouncements: AnnouncementResponse[] = [];
  unreviewedAnnouncements: AnnouncementResponse[] = [];

  constructor(private router: Router, private announcementsService: AnnouncementsService) {
  }

  ngOnInit() {
    this.announcementsService.getAllLoggedUserAnnouncements()
      .subscribe(data => {
        let announcements = data;
        this.unreviewedAnnouncements = announcements.filter(announcement => !announcement.isReviewed);
        this.reviewedAnnouncements = announcements.filter(announcement => announcement.isReviewed);
      });
  }

  removeAnnouncement(id: number): void {
    if (!confirm('Czy na pewno chcesz usunąć to ogłoszenie?')) {
      return;
    }
    this.announcementsService.deleteAnnouncement(id)
      .subscribe(() => {
        this.reviewedAnnouncements = this.reviewedAnnouncements.filter(announcement => announcement.id !== id);
        this.unreviewedAnnouncements = this.unreviewedAnnouncements.filter(announcement => announcement.id !== id);
      });
  }

  editAnnouncement(id: number): void {
    this.router.navigate(['/edit-announcement', id]);
  }
}
