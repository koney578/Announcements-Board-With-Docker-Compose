import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {TokenService} from "../../services/token/token.service";
import {Router} from "@angular/router";
import {CategoryService} from "../../services/category/category.service";
import {AnnouncementElementComponent} from "../announcement-element/announcement-element.component";
import {CategoryResponse} from "../../interfaces/CategoryResponse";
import {CategoryElementComponent} from "../category-element/category-element.component";

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    AnnouncementElementComponent,
    NgForOf,
    CategoryElementComponent
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit{

  addCategoryForm: FormGroup;
  categories: CategoryResponse[] = [];
  constructor(private categoryService: CategoryService, private tokenService: TokenService, private router : Router) {
    this.addCategoryForm = new FormGroup({
      category: new FormControl('', [Validators.required]),
    });
  }
  ngOnInit(): void {
    this.categoryService.getAllCategories()
      .subscribe(data => this.categories = data);
  }
  get category() {
    return this.addCategoryForm.get('category');
  };
  addCategory() {
    if (this.addCategoryForm.valid) {
      this.categoryService.addCategory(this.addCategoryForm.value.category)
        .subscribe(response => {
          this.ngOnInit()
        }, error => {
          alert(error.error.message);
        });
    }
  }
}
