import {Component, OnInit} from '@angular/core';
import {CommonModule, NgIf} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AddAnnouncementRequest} from "../../interfaces/AddAnnouncementRequest";
import {AnnouncementsService} from "../../services/announcements/announcements.service";
import {CategoryResponse} from "../../interfaces/CategoryResponse";
import {CategoryService} from "../../services/category/category.service";

@Component({
  selector: 'app-add-announcement',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './add-announcement.component.html',
  styleUrl: './add-announcement.component.css'
})
export class AddAnnouncementComponent implements OnInit{
  addAnnouncementForm: FormGroup;
  categories: CategoryResponse[] = [];

  constructor(private categoryService: CategoryService, private announcementsService: AnnouncementsService, private router: Router) {
    this.addAnnouncementForm = new FormGroup({
      title: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      endsAt: new FormControl('', [Validators.required]),
      category: new FormControl('', [Validators.required])
    });

  }

  ngOnInit() {
    this.categoryService.getAllCategories()
      .subscribe(data => this.categories = data)
  }

  get title() {
    return this.addAnnouncementForm.get('title');
  };

  get description() {
    return this.addAnnouncementForm.get('description');
  };

  get endsAt() {
    return this.addAnnouncementForm.get('endsAt');
  };

  get category() {
    return this.addAnnouncementForm.get('endsAt');
  };

  onSubmit() {
    if (this.addAnnouncementForm.valid) {
      const addAnnouncementRequest: AddAnnouncementRequest = {
        title: this.addAnnouncementForm.value.title,
        description: this.addAnnouncementForm.value.description,
        endsAt: this.addAnnouncementForm.value.endsAt,
        categoryId: this.addAnnouncementForm.value.category
      };
      this.announcementsService.addAnnouncement(addAnnouncementRequest)
        .subscribe(response => {
          alert("Ogłoszenie dodane pomyślnie!");
          this.router.navigateByUrl('/');
        }, error => {
          alert("Nieudana próba dodania ogłoszenia - spróbuj ponownie!");
        });
    }
  }
}
