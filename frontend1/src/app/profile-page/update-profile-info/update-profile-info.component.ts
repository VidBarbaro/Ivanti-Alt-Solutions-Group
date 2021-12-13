import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/auth/service/authentication.service';
import { User } from 'src/app/model/user';

@Component({
  selector: 'app-update-profile-info',
  templateUrl: './update-profile-info.component.html',
  styleUrls: ['./update-profile-info.component.css']
})
export class UpdateProfileInfoComponent implements OnInit {

  public loggedInUser: User;

  constructor(private authenticationService: AuthenticationService) {
    this.loggedInUser = authenticationService.getUserFromLocalCache();
  }

  ngOnInit(): void {
  }

  // public updateProfile(email: string, name: string): void {
  //  console.log("intra aici");
   
  // }

  // valuechange(newValue : String) {
  //   mymodel = newValue;
  //   console.log(newValue)
  // }

}
