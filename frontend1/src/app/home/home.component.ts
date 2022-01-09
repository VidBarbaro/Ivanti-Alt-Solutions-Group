import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../auth/service/authentication.service';
import { User } from '../model/user';
import { ModalService } from '../_modal';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private modalService: ModalService, private authService: AuthenticationService) { }

  user : User;

  ngOnInit(): void {
    this.user = this.authService.getUserFromLocalCache();
  }

  checkIfUserIsNew(): boolean{
    if(this.user.firstTime == 0){
      //this.isFirstTime = true;
      return true;
    }
    else{
      return false;
    }
  }

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
     this.modalService.close(id);
  } 

}
