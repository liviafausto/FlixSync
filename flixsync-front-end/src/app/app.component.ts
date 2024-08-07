import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { AppMaterialModule } from './shared/app-material/app-material.module';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    AppMaterialModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'flixsync-front-end';
}
