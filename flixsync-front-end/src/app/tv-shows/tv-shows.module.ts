import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AppMaterialModule } from '../shared/app-material/app-material.module';
import { ErrorDialogComponent } from '../shared/components/error-dialog/error-dialog.component';
import { TvShowsRoutingModule } from './tv-shows-routing.module';
import { TvShowsComponent } from './tv-shows/tv-shows.component';


@NgModule({
  declarations: [
    TvShowsComponent
  ],
  imports: [
    CommonModule,
    TvShowsRoutingModule,
    AppMaterialModule,
    ErrorDialogComponent
  ]
})
export class TvShowsModule { }
