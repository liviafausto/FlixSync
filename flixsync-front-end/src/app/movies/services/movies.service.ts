import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, Observable } from 'rxjs';

import { Movie } from '../model/movie';

@Injectable({
  providedIn: 'root'
})
export class MoviesService {

  private readonly API = 'movies.json'

  constructor(private httpClient: HttpClient) { }

  list(): Observable<Movie[]> {
    return this.httpClient.get<Movie[]>(this.API)
    .pipe(first());
  }
}
