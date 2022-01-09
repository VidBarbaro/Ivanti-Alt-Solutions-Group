import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationType } from '../auth/enum/notification-type.enum';
import { AuthenticationService } from '../auth/service/authentication.service';
import { NotificationService } from '../auth/service/notification.service';
import { User } from '../model/user';
import { ModalService } from '../_modal';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private notificationService: NotificationService, private router: Router, private authService: AuthenticationService, private modalService: ModalService) {
  }

  loggedIn: boolean = false;
  contentCreator: boolean = false;
  //isUserIncremented: boolean;
  user : User;


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

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
     this.modalService.close(id);
  } 

  isFirstTime(): boolean{
    if (this.user.firstTime == 0){
      return true;
    }
    else{
      return false;
    }
  }

}
