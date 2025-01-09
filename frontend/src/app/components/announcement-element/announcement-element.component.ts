import {Component, Input} from '@angular/core';
import {AnnouncementResponse} from "../../interfaces/AnnouncementResponse";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-announcement-element',
  standalone: true,
  imports: [
    DatePipe
  ],
  templateUrl: './announcement-element.component.html',
  styleUrl: './announcement-element.component.css'
})
export class AnnouncementElementComponent {
  @Input() announcement!: AnnouncementResponse;
}
