import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { AuthenticationService } from '../auth/service/authentication.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {
  public loggedInUser: User;

  constructor(private authenticationService: AuthenticationService) {
    this.loggedInUser = authenticationService.getUserFromLocalCache();
  }

  ngOnInit(): void {
  }

}
