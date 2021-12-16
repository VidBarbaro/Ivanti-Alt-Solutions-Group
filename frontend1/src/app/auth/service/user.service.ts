import { HttpClient, HttpErrorResponse, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomHttpResponse } from 'src/app/model/custom-http-response';
import { User } from 'src/app/model/user';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private host = environment.apiUrl;

  constructor(private http: HttpClient) { }

  public getUsers(): Observable<User[] | HttpErrorResponse> {
    return this.http.get<User[]>(`${this.host}/user/list`);
  }

  public addUser(formData: FormData): Observable<User | HttpErrorResponse> {
    return this.http.post<User>(`${this.host}/user/add`, formData);
  }

  public updateUser(formData: FormData): Observable<User> {
    return this.http.post<User>(`${this.host}/api/user/update`, formData);
  }

  public updateProfileImage(formData: FormData): Observable<HttpEvent<User> | HttpErrorResponse> {
    return this.http.post<User>(`${this.host}/user/updateProfileImage`, formData,
    {reportProgress: true,
      observe: 'events'
    });
  }

  public resetPassword(formData: FormData): Observable<User> {
    return this.http.post<User>(`${this.host}/api/user/update/password`, formData);
  }


  public deleteUser(userId: number): Observable<CustomHttpResponse | HttpErrorResponse> {
    return this.http.delete<CustomHttpResponse>(`${this.host}/user/delete/${userId}`);
  }

  public addUsersToLocalCache(users: User[]): void {
    localStorage.setItem('users', JSON.stringify(users));
  }

  public getUsersToLocalCache(): User[] {
    if(localStorage.getItem('users')) {
      return JSON.parse(localStorage.getItem('users'));
    }
    return null;
  }

  public createUserFormData(user: User): FormData {
    const formData = new FormData();
    formData.append('username', user.username);
    formData.append('name', user.name);
    formData.append('email', user.email);
    formData.append('token', user.token);
    return formData;
  }

  public createUserFormDataUpdate(id: number, name: string, email: string): FormData {
    const formData = new FormData();
    formData.append('id', id.toString());
    formData.append('name', name);
    formData.append('email', email);
    return formData;
  }

  public createUserFormDataResetPassword(id: number, oldPassword: string, newPassword: string): FormData {
    const formData = new FormData();
    formData.append('id', id.toString());
    formData.append('oldpassword', oldPassword);
    formData.append('password', newPassword);
    return formData;
  }
}
