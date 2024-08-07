import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { TvShowsComponent } from './tv-shows/tv-shows.component';
import { EpisodesComponent } from './episodes/episodes.component';

const routes: Routes = [
  { path: '', component: TvShowsComponent },
  { path: 'episodes', component: EpisodesComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TvShowsRoutingModule { }
