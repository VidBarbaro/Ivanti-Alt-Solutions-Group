import { Component, OnInit } from '@angular/core';
import { Package } from 'src/app/model/package';
import { ActivatedRoute } from '@angular/router';
import { PackageVersion } from 'src/app/model/version';
import { PackageService } from 'src/app/_services/package-service/package-service';


@Component({
  selector: 'app-update-package-creator',
  templateUrl: './update-package-creator.component.html',
  styleUrls: ['./update-package-creator.component.css']
})
export class UpdatePackageCreatorComponent implements OnInit {

  public currentPackage: Package;
  public versions: PackageVersion[];

  constructor(private packageService: PackageService, private router: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.getPackage();
  }

  public getPackage(): void {
    this.packageService.getPackageById(this.router.snapshot.params.id).subscribe(
      (response: Package) => {
        this.currentPackage = response;
        this.versions = response.versions;
      }
    )

  }

}
