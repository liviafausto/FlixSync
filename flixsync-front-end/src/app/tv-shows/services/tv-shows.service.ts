import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, Observable } from 'rxjs';

import { TvShow } from '../model/tv-show';

@Injectable({
  providedIn: 'root'
})
export class TvShowsService {

  private readonly API = '/api/tv-shows'

  constructor(private httpClient: HttpClient) { }

  list(): Observable<TvShow[]> {
    return this.httpClient.get<TvShow[]>(this.API).pipe(first());
  }
}
