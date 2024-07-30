import { Component } from '@angular/core';
import { Movie } from '../model/movie';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrl: './movies.component.scss'
})
export class MoviesComponent {

  movies: Movie[];
  displayedColumns = ['id', 'name', 'duration', 'release-date', 'director', 'summary'];

  constructor() {
    this.movies = [
      {_id: 1, name: "Inception", duration: "2 hours and 28 minutes", release_date: "2010-09-24", director: "Christopher Nolan", summary: "A thief who steals corporate secrets through the use of dream-sharing technology..."}
    ];
  }

}
