import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, Observable } from 'rxjs';

import { Category } from '../model/category';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {

  private readonly API = 'categories.json'

  constructor(private httpClient: HttpClient) { }

  list(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.API)
    .pipe(first());
  }
}
