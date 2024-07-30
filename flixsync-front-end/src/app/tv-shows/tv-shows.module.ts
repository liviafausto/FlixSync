import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { TvShowsRoutingModule } from './tv-shows-routing.module';
import { TvShowsComponent } from './tv-shows/tv-shows.component';


@NgModule({
  declarations: [
    TvShowsComponent
  ],
  imports: [
    CommonModule,
    TvShowsRoutingModule
  ]
})
export class TvShowsModule { }
