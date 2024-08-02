import { Component } from '@angular/core';
import { Observable } from 'rxjs';

import { Category } from '../model/category';
import { CategoriesService } from '../services/categories.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.scss'
})
export class CategoriesComponent {

  categories$: Observable<Category[]>;
  displayedColumns = ['id', 'name'];

  constructor(private categoriesService: CategoriesService) {
    this.categories$ = this.categoriesService.list();
  }

}
