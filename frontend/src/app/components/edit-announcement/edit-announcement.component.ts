import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {CategoryResponse} from "../../interfaces/CategoryResponse";
import {CategoryService} from "../../services/category/category.service";
import {AnnouncementsService} from "../../services/announcements/announcements.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AddAnnouncementRequest} from "../../interfaces/AddAnnouncementRequest";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-edit-announcement',
  standalone: true,
    imports: [
        NgForOf,
        NgIf,
        ReactiveFormsModule
    ],
  templateUrl: './edit-announcement.component.html',
  styleUrl: './edit-announcement.component.css'
})
export class EditAnnouncementComponent implements OnInit{
  addAnnouncementForm: FormGroup;
  categories: CategoryResponse[] = [];
  announcementId: number;

  constructor(
    private activatedRoute: ActivatedRoute,
    private categoryService: CategoryService,
    private announcementsService: AnnouncementsService,
    private tokenService: TokenService,
    private router: Router
  ) {
    this.announcementId = Number(this.activatedRoute.snapshot.paramMap.get('id'));
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

    this.announcementsService.getAnnouncementById(this.announcementId)
      .subscribe(announcement => {
        if (this.tokenService.getUserId() != announcement.user.id) {
          alert("Nie możesz edytować cudzego ogłoszenia!");
          this.router.navigateByUrl('/announcements');
        } else {
          this.addAnnouncementForm.setValue({
            title: announcement.title,
            description: announcement.description,
            endsAt: announcement.endsAt,
            category: announcement.category.id
          });
        }
      }, error => {
        if (error.status == 404) {
          alert("Nie znaleziono ogłoszenia o podanym ID!");
          this.router.navigateByUrl('/announcements');
        }
      });
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
      this.announcementsService.updateAnnouncementById(this.announcementId, addAnnouncementRequest)
        .subscribe(response => {
          alert("Ogłoszenie edytowane pomyślnie!");
          this.router.navigateByUrl('/my-announcements');
        }, error => {
          alert("Nieudana próba edycji ogłoszenia - sprawdź czy wszystkie pola są uzupełnione!");
        });
    }
  }
}
