import { HttpClient, HttpErrorResponse, HttpParams, HttpResponse } from '@angular/common/http';
import { Version } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/app/auth/service/authentication.service';
import { CustomHttpResponse } from 'src/app/model/custom-http-response';
import { Package } from 'src/app/model/package';

import { environment } from 'src/environments/environment';
import { Review } from '../model/review';

@Injectable({
    providedIn: 'root'
})

export class ReviewService {
    private host = environment.apiUrl;

    constructor(private http: HttpClient, private authService: AuthenticationService) { }

    public addVersionToPackage(idPackage: number, versionName: string, versionReadMe: string): Observable<Package> {
        return this.http.post<any>(`${this.host}/api/packages/add-version/${idPackage}`, {
            "name": versionName,
            "readme": versionReadMe
        });
    }

    public getReviewsOfPackage(packageId: number) {
        return this.http.get<Review[]>(`${this.host}/api/reviews/package/${packageId}`, { observe: 'response' });
    }
}