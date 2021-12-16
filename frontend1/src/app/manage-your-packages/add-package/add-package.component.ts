import { HttpErrorResponse } from '@angular/common/http';
import { Version } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NotificationType } from 'src/app/auth/enum/notification-type.enum';
import { AuthenticationService } from 'src/app/auth/service/authentication.service';
import { NotificationService } from 'src/app/auth/service/notification.service';
import { UserService } from 'src/app/auth/service/user.service';
import { Package } from 'src/app/model/package';
import { SystemRequirements } from 'src/app/model/systemRequirements';
import { PackageVersion } from 'src/app/model/version';
import { PackageService } from 'src/app/_services/package-service/package-service';

@Component({
  selector: 'app-add-package',
  templateUrl: './add-package.component.html',
  styleUrls: ['./add-package.component.css']
})
export class AddPackageComponent implements OnInit {
  newPackage: Package;
  newSystemRequirements: SystemRequirements;
  newVersion: PackageVersion;
  file: File;

  constructor(private authenticationService: AuthenticationService, private packageService: PackageService, private notificationService: NotificationService) { }

  ngOnInit(): void {
  }

  public onFileChange(file: File): void {
    this.file = file;
  }

  public onAddNewPackage(packageForm: NgForm): void {
    this.newPackage = new Package(packageForm.value.name, this.authenticationService.getUserFromLocalCache(), packageForm.value.intro);
    this.newSystemRequirements = new SystemRequirements(packageForm.value.processorType, packageForm.value.ram, packageForm.value.graphicsCard);
    this.newVersion = new PackageVersion(packageForm.value.versionName, packageForm.value.readme, packageForm.value.packageUrl);

    const formData = this.packageService.createPackageFormData(this.newPackage, this.newSystemRequirements, this.newVersion);
    this.packageService.addPackage(formData).subscribe(
      (response: Package) => {
        packageForm.reset();
        this.sendNotification(NotificationType.SUCCESS, `Package added succesfully`);
      },
      (errorResponse: HttpErrorResponse) => {
        this.sendNotification(NotificationType.ERROR, errorResponse.error);
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
