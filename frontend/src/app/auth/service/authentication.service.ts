import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from "@auth0/angular-jwt";
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public host = environment.apiUrl;
  private token !: string;
  private loggedInUsername !: string;
  currentUser!: User;
  currentToken !: void; //becasue of this Angular version its impossible to return the exact token string, so we need a transitional object
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient) { }

  public login(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>(`${this.host}/user/login`, user, { observe: 'response' });
  }

  public register(user: User): Observable<User> {
    return this.http.post<User>(`${this.host}/user/register`, user);
  } 

  public logOut(): void {
    this.token = "";
    this.loggedInUsername = "";
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    localStorage.removeItem('users');
  }

  public saveToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  public addUserToLocalCache(user ?: User): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUserFromLocalCache(): User {
    const userJson = localStorage.getItem('user');
    this.currentUser = userJson !== null ? JSON.parse(userJson) : new User();
    return this.currentUser;
  }

  public loadToken(): void {
    const tokenJson = localStorage.getItem('token');
    this.currentToken = tokenJson !== null ? JSON.parse(tokenJson) : new User();
    return this.currentToken;
  }

  public getToken(): string {
    return this.token;
  }

  public isUserLoggedIn(): boolean {
    this.loadToken();
    if (this.token != null && this.token !== ''){
      if (this.jwtHelper.decodeToken(this.token).sub != null || '') {
        if (!this.jwtHelper.isTokenExpired(this.token)) {
          this.loggedInUsername = this.jwtHelper.decodeToken(this.token).sub;
          return true;
        }
      }
    } else {
      this.logOut();
      return false;
    }
    return false;
  }
}

