import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../auth/service/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor() {
  }

  loggedIn: boolean;

  ngOnInit(): void {
    if (localStorage.getItem("user")) {
      this.loggedIn = true;
    }
    else {
      this.loggedIn = false;
    }
  }


  showMenu: boolean = true;

  toggle() {
    if (this.showMenu) { this.showMenu = false }
    else { this.showMenu = true }
  }

}
