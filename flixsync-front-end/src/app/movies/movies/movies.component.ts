import { Component } from '@angular/core';
import { Observable } from 'rxjs';

import { Movie } from '../model/movie';
import { MoviesService } from '../services/movies.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrl: './movies.component.scss'
})
export class MoviesComponent {

  movies: Observable<Movie[]>;
  displayedColumns = ['id', 'name', 'duration', 'release-date', 'director', 'summary'];

  constructor(private moviesService: MoviesService) {
    this.movies = this.moviesService.list();
  }

}
