import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { ResetPasswordProfileComponent } from './profile-page/reset-password-profile/reset-password-profile.component';
import { UpdateProfileInfoComponent } from './profile-page/update-profile-info/update-profile-info.component';
import { RegistrationComponent } from './registration/registration.component';
import { PackageDetailsComponent } from './store/package-details/package-details.component';
import { StoreComponent } from './store/store.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'store', component: StoreComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent },
  { 
    path: 'store',
    children: [
      {
        path: '', component: StoreComponent
      },
      {
        path: 'package',
        children: [
          { path: 'details/:id', component: PackageDetailsComponent }
        ]
      }]
  },
  {
    path : 'profile-page',
    children: [
      {
        path: '', component: ProfilePageComponent
      },
      {
        path: 'update-profile', component: UpdateProfileInfoComponent
      },
      {
        path: 'reset-password', component: ResetPasswordProfileComponent
      },]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
