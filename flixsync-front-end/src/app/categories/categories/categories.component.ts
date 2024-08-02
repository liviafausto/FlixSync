import { Component } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';

import { Category } from '../model/category';
import { CategoriesService } from '../services/categories.service';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.scss'
})
export class CategoriesComponent {

  categories$: Observable<Category[]>;
  displayedColumns = ['id', 'name'];

  constructor(private categoriesService: CategoriesService, public dialog: MatDialog) {
    this.categories$ = this.categoriesService.list().pipe(
      catchError(error => {
        this.onError('An error occurred while loading categories.')
        return of([]);
      })
    );
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {data: errorMsg});
  }

}
