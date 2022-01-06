import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { AuthenticationService } from '../auth/service/authentication.service';
import { UserService } from '../auth/service/user.service';
import { NotificationType } from '../auth/enum/notification-type.enum';
import { NotificationService } from '../auth/service/notification.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Package } from '../model/package';
import { PackageService } from '../_services/package-service/package-service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {
  public loggedInUser: User;
  public message: string;
  public isContentCreator: boolean;
  public contentCreatorToShow: string;
  public favouritePackages: Package[] = [];

  constructor(private authenticationService: AuthenticationService, private userService: UserService, private notificationService: NotificationService,
    private packageService: PackageService) {
    this.loggedInUser = authenticationService.getUserFromLocalCache();
    this.message = "";
    this.isContentCreator = this.authenticationService.isUserContentCreator();
    if (this.isContentCreator === true) {
      this.contentCreatorToShow = "Yes";
    }
    else {
      this.contentCreatorToShow = "No";
    }
    this.getFavouritePackages();

  }

  public getFavouritePackages(): void {
    for (const p of this.loggedInUser.favourite_packages_id) {
      this.getPackage(p);

    }
  }

  public getPackage(idPackage: number): void {
    this.packageService.getPackageById(idPackage).subscribe(
      (response: Package) => {
        this.favouritePackages.push(response)
      }
    )
  }

  ngOnInit(): void {
  }


  public becomeContentCreator() {
    const formData = this.userService.createUserFormDataContentCreator(this.loggedInUser.id, "ROLE_CONTENT_CREATOR");

    this.userService.becomeContentCreator(formData).subscribe(
      (response: User) => {
        this.authenticationService.addUserToLocalCache(response);
        this.sendNotification(NotificationType.SUCCESS, `${response.name} ${response.email} updated successfully`)
        this.message = "The changes were successfully saved! You are now a content creator!";
      },
      (errorResponse: HttpErrorResponse) => {
        this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
        this.message = "An error occured while processing the request. Please try again.";
      }
    )
  }

  private sendNotification(notificationType: NotificationType, message: string) {
    if (message) {
      this.notificationService.notify(notificationType, message);
    }
    else {
      this.notificationService.notify(notificationType, "An error has occured.")
    }
  }

}
