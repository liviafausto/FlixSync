import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { catchError, Observable, of } from 'rxjs';

import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { Movie } from '../model/movie';
import { MoviesService } from '../services/movies.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrl: './movies.component.scss'
})
export class MoviesComponent {

  movies$: Observable<Movie[]>;
  displayedColumns = ['id', 'name', 'duration', 'release-date', 'director', 'summary'];

  constructor(private moviesService: MoviesService, public dialog: MatDialog) {
    this.movies$ = this.moviesService.list().pipe(
      catchError(error => {
        this.onError('An error occurred while loading movies.');
        return of([]);
      })
    );
  }

  onError(errorMsg: string){
    this.dialog.open(ErrorDialogComponent, {data: errorMsg})
  }

}
