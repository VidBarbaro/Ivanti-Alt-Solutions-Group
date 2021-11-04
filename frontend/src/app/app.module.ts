import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { DealsComponent } from './home/deals/deals.component';
import { IntroComponent } from './home/intro/intro.component';
import { HomeComponent } from './home/home.component';
import { ContactComponent } from './home/contact/contact.component';
import { AboutComponent } from './home/about/about.component';
import { StoreComponent } from './store/store.component';
import { PackageComponent } from './store/package/package.component';
import { PackageDetailsComponent } from './store/package-details/package-details.component';
import { StoreIntroComponent } from './store/store-intro/store-intro.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    DealsComponent,
    IntroComponent,
    HomeComponent,
    ContactComponent,
    AboutComponent,
    StoreComponent,
    PackageComponent,
    PackageDetailsComponent,
    StoreIntroComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
