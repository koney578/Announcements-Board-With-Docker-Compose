import {Component, OnInit} from '@angular/core';
import {AnnouncementsService} from "../../services/announcements/announcements.service";
import {AnnouncementResponse} from "../../interfaces/AnnouncementResponse";
import {NgForOf} from "@angular/common";
import {AnnouncementElementComponent} from "../announcement-element/announcement-element.component";

@Component({
  selector: 'app-public-announcements',
  standalone: true,
  imports: [
    NgForOf,
    AnnouncementElementComponent
  ],
  templateUrl: './public-announcements.component.html',
  styleUrl: './public-announcements.component.css'
})
export class PublicAnnouncementsComponent implements OnInit {
  announcements: AnnouncementResponse[] = [];

  constructor(private announcementsService: AnnouncementsService) { }

  ngOnInit(): void {
    this.announcementsService.getAllReviewedAnnouncements()
      .subscribe(data => this.announcements = data);
  }
}
