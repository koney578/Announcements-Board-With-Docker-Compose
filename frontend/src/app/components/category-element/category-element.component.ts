import {Component, Input} from '@angular/core';
import {CategoryResponse} from "../../interfaces/CategoryResponse";
import {DatePipe} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {CategoryService} from "../../services/category/category.service";
import {CategoryComponent} from "../category/category.component";
@Component({
  selector: 'app-category-element',
  standalone: true,
  imports: [
    DatePipe
  ],
  templateUrl: './category-element.component.html',
  styleUrl: './category-element.component.css'
})
export class CategoryElementComponent {
  @Input() category!: CategoryResponse;
  constructor(private categoryComponent: CategoryComponent, private categoryService: CategoryService, private httpClient: HttpClient) {}
  editCategory(id: number): void {
    const newCategoryName = prompt('Podaj nową nazwę kategorii:');
    if (newCategoryName) {
      this.categoryService.updateCategory(newCategoryName, id)
        .subscribe(response => {
          this.categoryComponent.ngOnInit()
      }, error => {
          alert(error.error.message);
        });
    }
  }
  deleteCategory(id: number): void {
    if (confirm('Czy na pewno chcesz usunąć tę kategorię?')) {
      this.categoryService.deleteCategory(id)
        .subscribe(response => {
          this.categoryComponent.ngOnInit()
        }, error => {
          alert(error.error.message);
        });
    }
  }
}
