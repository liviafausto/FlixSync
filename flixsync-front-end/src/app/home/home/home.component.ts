import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(private router: Router) {  }

  goToMovies() {
    this.router.navigate(['movies']);
  }

  goToTvShows() {
    this.router.navigate(['tv-shows']);
  }

  goToCategories() {
    this.router.navigate(['categories']);
  }

}
