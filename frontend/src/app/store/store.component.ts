import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationType } from '../auth/enum/notification-type.enum';
import { AuthenticationService } from '../auth/service/authentication.service';
import { NotificationService } from '../auth/service/notification.service';
import { Package } from '../model/package';
import { User } from '../model/user';
import { PackageService } from '../_services/package-service/package-service';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {
  public showLoading: boolean;
  private subscriptions: Subscription[] = [];
  public packages: Package[];

  constructor(private router: Router, private authenticationService: AuthenticationService,
    private notificationService: NotificationService, private packageService: PackageService) { }


  ngOnInit(): void {
    if (this.authenticationService.isUserLoggedIn()) {
      this.router.navigateByUrl('/home');    ///user/management
    } else {
      this.router.navigateByUrl('/login');
    }

  }

  public showPackages(user: User): void {
    this.showLoading = true;
    this.subscriptions.push(
      this.packageService.getPackages().subscribe( // we get user from the back-end
        (response: HttpResponse<Package[]>) => {
          this.packages = response.body;
          this.showLoading = false; // stop showing loading on button
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendErrorNotification(NotificationType.ERROR, errorResponse.error.message);
          this.showLoading = false;
        }
      )
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  private sendErrorNotification(notificationType: NotificationType, message: string): void {
    if (message) {
      this.notificationService.notify(notificationType, message);
    } else {
      this.notificationService.notify(notificationType, 'An error occurred. Please try again.');
    }
  }
  // private titleSubject = new BehaviorSubject<String>('Packages');
  // public titleAction$ = this.titleSubject.asObservable();
  // public packages: Package[];
  // private subscriptions: Subscription[] = [];

  // constructor(private packageService: PackageService) { }

  // public changeTitle(title: String): void {
  //   this.titleSubject.next(title);
  // }

  // ngOnInit(): void {
  //   if (this.authe)
  // }

  // public getPackages(): void {
  //   this.subscriptions.push(
  //     this.packageService.getPackages().subscribe(
  //       (response: Package[]) => {
  //         this.packageService.addPackagesToLocalCache(response);
  //         this.packages = response;
  //       },
  //       (errorResponse: HttpErrorResponse) => {
  //         console.log(errorResponse.error.message);

  //       }
  //     )
  //   )


  // }

}
