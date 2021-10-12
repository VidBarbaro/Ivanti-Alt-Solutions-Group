import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  showMenu: boolean = true;

  toggle() {
    if(this.showMenu){this.showMenu = false }
    else{this.showMenu = true}
  }

}
