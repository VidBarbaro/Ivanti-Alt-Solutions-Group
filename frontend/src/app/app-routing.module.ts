import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PackageDetailsComponent } from './store/package-details/package-details.component';
import { StoreComponent } from './store/store.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  {
    path: 'store',
    children: [
      {
        path: '', component: StoreComponent
      },
      {
        path: 'package-details', component: PackageDetailsComponent
      },]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
