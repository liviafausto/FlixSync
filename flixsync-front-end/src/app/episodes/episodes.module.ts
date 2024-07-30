import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { EpisodesRoutingModule } from './episodes-routing.module';
import { EpisodesComponent } from './episodes/episodes.component';


@NgModule({
  declarations: [
    EpisodesComponent
  ],
  imports: [
    CommonModule,
    EpisodesRoutingModule
  ]
})
export class EpisodesModule { }
