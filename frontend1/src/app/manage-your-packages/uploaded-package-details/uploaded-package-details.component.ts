import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Package } from 'src/app/model/package';
import { PackageService } from 'src/app/_services/package-service/package-service';

@Component({
  selector: 'app-uploaded-package-details',
  templateUrl: './uploaded-package-details.component.html',
  styleUrls: ['./uploaded-package-details.component.css']
})
export class UploadedPackageDetailsComponent implements OnInit {
  package: Package = null;

  constructor(private packageService: PackageService, private router: ActivatedRoute) { 
   }

  ngOnInit(): void {
    console.log(this.router.snapshot.params);
    this.getPackage();
    
  }

  public getPackage(): void {
    this.packageService.getPackageById(this.router.snapshot.params.id).subscribe(
      (response: Package) => {
        this.package = response;   
        console.log(this.package);
             
      }
    )
  }

}
