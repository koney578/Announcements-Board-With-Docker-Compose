import {Component, OnInit} from '@angular/core';
import {AnnouncementResponse} from "../../interfaces/AnnouncementResponse";
import {AnnouncementsService} from "../../services/announcements/announcements.service";
import {AnnouncementElementComponent} from "../announcement-element/announcement-element.component";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-admin-announcements',
  standalone: true,
  imports: [
    AnnouncementElementComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './admin-announcements.component.html',
  styleUrl: './admin-announcements.component.css'
})
export class AdminAnnouncementsComponent implements OnInit {
  reviewedAnnouncements: AnnouncementResponse[] = [];
  unreviewedAnnouncements: AnnouncementResponse[] = [];

  constructor(private announcementsService: AnnouncementsService) {
  }

  ngOnInit(): void {
    this.announcementsService.getAllAnnouncements()
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

  reviewAnnouncement(id: number): void {
    this.announcementsService.reviewAnnouncement(id)
      .subscribe(() => {
        const announcement = this.unreviewedAnnouncements.find(announcement => announcement.id === id);
        this.unreviewedAnnouncements = this.unreviewedAnnouncements.filter(announcement => announcement.id !== id);
        if (announcement) {
          this.reviewedAnnouncements.push(announcement);
        }
      });
  }

}
