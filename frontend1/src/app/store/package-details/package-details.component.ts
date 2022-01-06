import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationType } from 'src/app/auth/enum/notification-type.enum';
import { AuthenticationService } from 'src/app/auth/service/authentication.service';
import { NotificationService } from 'src/app/auth/service/notification.service';
import { CustomHttpResponse } from 'src/app/model/custom-http-response';
import { Package } from 'src/app/model/package';
import { Review } from 'src/app/model/review';
import { User } from 'src/app/model/user';
import { PackageVersion } from 'src/app/model/version';
import { PackageService } from 'src/app/_services/package-service/package-service';
import { ReviewService } from 'src/app/_services/review-service';

@Component({
  selector: 'app-package-details',
  templateUrl: './package-details.component.html',
  styleUrls: ['./package-details.component.css']
})
export class PackageDetailsComponent implements OnInit {
  package: Package = null;
  user: User = null;
  message: string = null;
  isPackageFavourite: boolean;
  versions: PackageVersion[];
  currentRate = 0;
  submitReviewMessage: string = null;
  packageReviews: Review[];
  nrReviews: number;

  constructor(
    private packageService: PackageService,
    private router: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private notificationService: NotificationService,
    private reviewService: ReviewService) {
  }

  ngOnInit(): void {
    this.user = this.authenticationService.getUserFromLocalCache();
    console.log(this.router.snapshot.params);
    this.getPackage();
    this.getReviewsOfPackage();
  }


  public getPackage(): void {
    this.packageService.getPackageById(this.router.snapshot.params.id).subscribe(
      (response: Package) => {
        this.package = response;
        this.isPackageInFavourites(this.user.id, this.package.id);
        this.versions = response.versions;
      }
    )
  }

  public getReviewsOfPackage(): void {
    this.reviewService.getReviewsOfPackage(this.router.snapshot.params.id).subscribe(
      (response: HttpResponse<Review[]>) => {
        this.packageReviews = response.body;
        this.nrReviews = this.packageReviews.length;
        console.log(response);

      }
    )
  }


  public isPackageInFavourites(userId: number, packageId: number) {
    this.packageService.isPackageInFavourites(userId, packageId).subscribe(
      (response: Package) => {
        if (response != null) {
          this.isPackageFavourite = true;
        }
        else {
          this.isPackageFavourite = false;
        }
      },
      (errorResponse: HttpErrorResponse) => {
        this.isPackageFavourite = false;
        console.log(this.isPackageFavourite);
      }
    )
  }

  public addPackageToFavourites(userId: number, packageId: number): void {
    const formData = this.packageService.createAddFavouritePackageFormData(userId, packageId);

    this.packageService.addPackageToFavourites(formData).subscribe(
      (response: Package) => {
        this.sendNotification(NotificationType.SUCCESS, `Package: "${response.title}" added to favourites successfully`)
        this.message = "Package added to favourites!";
        this.isPackageFavourite = true;
      },
      (errorResponse: HttpErrorResponse) => {
        this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
        this.message = "Package not added to favourites!"
      }
    )
  }

  public removePackageFromFavourites(userId: number, packageId: number): void {
    this.packageService.removePackageFromFavourites(userId, packageId).subscribe(
      (response: CustomHttpResponse) => {
        this.message = "Package removed from favourites!";
        this.isPackageFavourite = false;
      },
      (errorResponse: HttpErrorResponse) => {
        this.message = "Package not removed from favourites!"
      }
    )
  }

  public submitRating() {
    if (this.currentRate !== 0) {
      this.reviewService.addReview(this.package.id, this.currentRate).subscribe(
        (response: Review) => {
          this.sendNotification(NotificationType.SUCCESS, `Reviews added succesfully`);
          this.submitReviewMessage = "The review was successfully added!"
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR, errorResponse.error);
          this.submitReviewMessage = errorResponse.message;
        }
      )
    }
  }

  showOverview() {
    let packageInfo = document.getElementById("package-info-description")
    if (packageInfo !== null) {
      packageInfo.innerHTML = "Overview: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur.Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim idest laborum.";
    }
    let title = document.getElementById("package-info-title");
    if (title !== null) {
      title.innerHTML = "Overview";
    }
  }

  showSystemRequirements() {
    let packageInfo = document.getElementById("package-info-description")
    if (packageInfo !== null) {
      packageInfo.innerHTML = `Processor Type: ${this.package.systemRequirements.processorType} <br> 
      RAM: ${this.package.systemRequirements.ram} <br> Graphics Card: ${this.package.systemRequirements.graphicsCard}`;
    }
    let title = document.getElementById("package-info-title");
    if (title !== null) {
      title.innerHTML = "System Requirements";
    }
  }

  showRelated() {
    let packageInfo = document.getElementById("package-info-description")
    if (packageInfo !== null) {
      packageInfo.innerHTML = "Related: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur.Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim idest laborum.";
    }
    let title = document.getElementById("package-info-title");
    if (title !== null) {
      title.innerHTML = "Related";
    }
  }

  public downloadVersion(versionName: string) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8, ' + encodeURIComponent(versionName));
    element.setAttribute('download', `${this.package.title} ${versionName}`);
    document.body.appendChild(element);
    element.click();
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
