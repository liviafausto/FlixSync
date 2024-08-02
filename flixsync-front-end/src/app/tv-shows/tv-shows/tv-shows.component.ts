import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { catchError, Observable, of } from 'rxjs';

import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { TvShow } from '../model/tv-show';
import { TvShowsService } from './../services/tv-shows.service';

@Component({
  selector: 'app-tv-shows',
  templateUrl: './tv-shows.component.html',
  styleUrl: './tv-shows.component.scss'
})
export class TvShowsComponent {

  tvShows$: Observable<TvShow[]>;
  displayedColumns = ['id', 'title', 'average-duration', 'summary', 'seasons'];

  constructor(private tvShowsService: TvShowsService, public dialog: MatDialog) {
    this.tvShows$ = tvShowsService.list().pipe(
      catchError(error => {
        this.onError('An error occurred while loading TV shows.');
        return of([]);
      })
    );
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {data: errorMsg});
  }

}
