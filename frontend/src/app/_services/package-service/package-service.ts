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

    public getPackages(): Observable<Package[]> {
        return this.http.get<Package[]>(`${this.host}/api/packages`);
    }

    //   public addPackage(formData: FormData): Observable<Package | HttpErrorResponse> {
    //     return this.http.post<Package>(`${this.host}/user/add`, formData);
    //   }

    public addPackagesToLocalCache(packages: Package[]): void {
        localStorage.setItem('packages', JSON.stringify(packages));
    }


}