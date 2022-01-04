import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationType } from '../auth/enum/notification-type.enum';
import { AuthenticationService } from '../auth/service/authentication.service';
import { NotificationService } from '../auth/service/notification.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private notificationService: NotificationService, private router: Router, private authService: AuthenticationService) {
  }

  loggedIn: boolean = false;
  contentCreator: boolean = false;

  ngOnInit(): void {
    if (localStorage.getItem("user")) {
      this.loggedIn = true;
    }
    else {
      this.loggedIn = false;
    }
    this.contentCreator = this.authService.isUserContentCreator();
  }


  showMenu: boolean = true;

  toggle() {
    if (this.showMenu) { this.showMenu = false }
    else { this.showMenu = true }
  }

  logOut(){
      this.authService.logOut();
      this.router.navigate(['/login']);
      this.sendNotification(NotificationType.SUCCESS, 'You have been logged out successfully');
  }

  private sendNotification(notificationType: NotificationType, message: string): void {
    if (message) {
      this.notificationService.notify(notificationType, message);
    } else {
      this.notificationService.notify(notificationType, 'An error occurred. Please try again.');
    }
  }

}
