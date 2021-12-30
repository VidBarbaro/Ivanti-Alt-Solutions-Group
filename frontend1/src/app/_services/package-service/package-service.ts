import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Version } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/app/auth/service/authentication.service';
import { UserService } from 'src/app/auth/service/user.service';
import { Package } from 'src/app/model/package';
import { SystemRequirements } from 'src/app/model/systemRequirements';
import { PackageVersion } from 'src/app/model/version';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})

export class PackageService {
    private host = environment.apiUrl;

    constructor(private http: HttpClient, private authService: AuthenticationService) { }

    public getPackages(): Observable<HttpResponse<Package[]>> {
        return this.http.get<Package[]>(`${this.host}/api/packages`, { observe: 'response' });
    }

    //   public addPackage(formData: FormData): Observable<Package | HttpErrorResponse> {
    //     return this.http.post<Package>(`${this.host}/user/add`, formData);
    //   }

    public addPackagesToLocalCache(packages: Package[]): void {
        localStorage.setItem('packages', JSON.stringify(packages));
    }

    public getPackageById(packageId: number): Observable<Package> {
        return this.http.get<Package>(`${this.host}/api/packages/get/${packageId}`);
    }

    // public addPackageToLocalCache(selectedPackage: Package): void {
    //     localStorage.setItem('package', JSON.stringify(selectedPackage));
    // }

    public getPackageFromLocalCache(): Package {
        if (localStorage.getItem('package')) {
            return JSON.parse(localStorage.getItem('package'));
        }
        return null;
    }

    // public addPackage(formData: FormData): Observable<Package> {
    //     return this.http.post<Package>(`${this.host}/api/packages/create`, formData);
    // }

    public createPackageFormData(newPackage: Package, newSystemRequirements: SystemRequirements, newVersion: PackageVersion): FormData {
        const formData = new FormData();
        formData.append('title', newPackage.title);
        formData.append('creator', JSON.stringify(this.authService.getUserFromLocalCache()));
        formData.append('intro', newPackage.intro);
        formData.append('processorType', newSystemRequirements.processorType);
        formData.append('ram', newSystemRequirements.ram);
        formData.append('graphicsCard', newSystemRequirements.graphicsCard);
        formData.append('name', newVersion.name);
        formData.append('readme', newVersion.readme);
        formData.append('url', newVersion.url);
        // formData.append('file', file);
        return formData;
    }

    public createNewPackageFormData(newPackage: Package, newVersion: PackageVersion, newSystemRequirements: SystemRequirements, file: File): FormData {
        var formdata = new FormData();
        formdata.append("rawPackage", `{\n    \"title\": \`${newPackage.title}\`,\n    \"creatorId\": \`${this.authService.getUserFromLocalCache().id}\`,\n    \"intro\": \"this is overview text of the package\"\n}`);
        formdata.append("rawVersion", `{\n    \"name\" : \`${newVersion.name}\`,\n    \"readme\" : \`${newVersion.readme}\`\n}`);
        formdata.append("file", file);
        formdata.append("rawRequirements", `{\n \"processorType\":\`${newSystemRequirements.processorType}\`,\n \"ram\":\`${newSystemRequirements.ram}\`,\n \"graphicsCard\":\`${newSystemRequirements.graphicsCard}\`\n}`);
        return formdata;
    }

    public createNewPackage(formData: FormData): Observable<Package> {
        var myHeaders = new Headers();
        myHeaders.append("Authorization", `Bearer ${this.authService.loadToken()}`);

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: formData,
            // redirect: 'follow'
        };

        // fetch("localhost:8080/api/packages/create", requestOptions)
        //     .then(response => response.text())
        //     .then(result => console.log(result))
        //     .catch(error => console.log('error', error));
        return this.http.post<Package>(`${this.host}/api/packages/create`, requestOptions);
    }



}