import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AnnouncementResponse} from "../../interfaces/AnnouncementResponse";
import {CategoryResponse} from "../../interfaces/CategoryResponse";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private httpClient: HttpClient) { }
  addCategory(categoryName: string): Observable<any> {
    // @ts-ignore
    return this.httpClient.post<any>(`http://localhost:8080/api/v1/categories?name=${encodeURIComponent(categoryName)}`);
  }
  getAllCategories(): Observable<CategoryResponse[]> {
    return this.httpClient.get<CategoryResponse[]>('http://localhost:8080/api/v1/categories');
  }
  updateCategory(newCategoryName: string, id: number): Observable<any> {
    // @ts-ignore
    return this.httpClient.put<any>(`http://localhost:8080/api/v1/categories/${id}?newName=${encodeURIComponent(newCategoryName)}`);
  }
  deleteCategory(id: number): Observable<any> {
    return this.httpClient.delete<any>(`http://localhost:8080/api/v1/categories/${id}`);
  }
}
