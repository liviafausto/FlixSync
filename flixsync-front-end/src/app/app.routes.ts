import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'movies',
    loadChildren: () => import('./movies/movies.module').then(m => m.MoviesModule)
  },
  {
    path: 'categories',
    loadChildren: () => import('./categories/categories.module').then(m => m.CategoriesModule)
  },
  {
    path: 'tv-shows',
    loadChildren: () => import('./tv-shows/tv-shows.module').then(m => m.TvShowsModule)
  },
  {
    path: 'episodes',
    loadChildren: () => import('./episodes/episodes.module').then(m => m.EpisodesModule)
  }
];
