import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Package } from 'src/app/model/package';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})

export class PackageService {
    private host = environment.apiUrl;

    constructor(private http: HttpClient) { }

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
        return this.http.get<Package>(`${this.host}/api/packages/${packageId}`);
    }

    // public addPackageToLocalCache(selectedPackage: Package): void {
    //     localStorage.setItem('package', JSON.stringify(selectedPackage));
    // }

    public getPackageFromLocalCache(): Package {
        if(localStorage.getItem('package')) {
            return JSON.parse(localStorage.getItem('package'));
          }
          return null;
    }


}