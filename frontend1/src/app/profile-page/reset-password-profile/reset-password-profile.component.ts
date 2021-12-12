import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/auth/service/authentication.service';
import { User } from 'src/app/model/user';

@Component({
  selector: 'app-reset-password-profile',
  templateUrl: './reset-password-profile.component.html',
  styleUrls: ['./reset-password-profile.component.css']
})
export class ResetPasswordProfileComponent implements OnInit {

  public loggedInUser: User;

  constructor(private authenticationService: AuthenticationService) {
    this.loggedInUser = authenticationService.getUserFromLocalCache();
  }

  ngOnInit(): void {
  }

}
