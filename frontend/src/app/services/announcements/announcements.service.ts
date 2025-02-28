import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AnnouncementResponse} from "../../interfaces/AnnouncementResponse";
import {Observable} from "rxjs";
import {AddAnnouncementRequest} from "../../interfaces/AddAnnouncementRequest";

@Injectable({
  providedIn: 'root'
})
export class AnnouncementsService {

  constructor(private httpClient: HttpClient) {
  }

  getAllReviewedAnnouncements(): Observable<AnnouncementResponse[]> {
    return this.httpClient.get<AnnouncementResponse[]>('http://localhost:8080/api/v1/announcements/reviewed');
  }

  getAllAnnouncements(): Observable<AnnouncementResponse[]> {
    return this.httpClient.get<AnnouncementResponse[]>('http://localhost:8080/api/v1/announcements');
  }

  getAllLoggedUserAnnouncements(): Observable<AnnouncementResponse[]> {
    return this.httpClient.get<AnnouncementResponse[]>('http://localhost:8080/api/v1/announcements/user-announcements');
  }

  getAnnouncementById(id: number): Observable<AnnouncementResponse> {
    return this.httpClient.get<AnnouncementResponse>(`http://localhost:8080/api/v1/announcements/${id}`);
  }

  addAnnouncement(addAnnouncementRequest: AddAnnouncementRequest): Observable<any> {
    return this.httpClient.post<any>(`http://localhost:8080/api/v1/announcements`, addAnnouncementRequest);
  }

  deleteAnnouncement(id: number): Observable<any> {
    return this.httpClient.delete<any>(`http://localhost:8080/api/v1/announcements/${id}`);
  }

  reviewAnnouncement(id: number): Observable<any> {
    return this.httpClient.patch<any>(`http://localhost:8080/api/v1/announcements/${id}`, {});
  }

  updateAnnouncementById(id: number, addAnnouncementRequest: AddAnnouncementRequest) {
    return this.httpClient.put<AddAnnouncementRequest>(`http://localhost:8080/api/v1/announcements/${id}`, addAnnouncementRequest);
  }
}
