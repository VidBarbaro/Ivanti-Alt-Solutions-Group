import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-package-details',
  templateUrl: './package-details.component.html',
  styleUrls: ['./package-details.component.css']
})
export class PackageDetailsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  showOverview() {
    let packageInfo = document.getElementById("package-info-description")
    if (packageInfo !== null) {
      packageInfo.innerHTML = "Overview: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur.Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim idest laborum.";
    }
    let title = document.getElementById("package-info-title");
    if (title !== null) {
      title.innerHTML = "Overview";
    }
  }

  showReviews() {
    let packageInfo = document.getElementById("package-info-description")
    if (packageInfo !== null) {
      packageInfo.innerHTML = "Reviews: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur.Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim idest laborum.";
    }
    let title = document.getElementById("package-info-title");
    if (title !== null) {
      title.innerHTML = "Reviews";
    }
  }

  showSystemRequirements() {
    let packageInfo = document.getElementById("package-info-description")
    if (packageInfo !== null) {
      packageInfo.innerHTML = "System requirements: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur.Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim idest laborum.";
    }
    let title = document.getElementById("package-info-title");
    if (title !== null) {
      title.innerHTML = "System Requirements";
    }
  }

  showRelated() {
    let packageInfo = document.getElementById("package-info-description")
    if (packageInfo !== null) {
      packageInfo.innerHTML = "Related: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur.Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim idest laborum.";
    }
    let title = document.getElementById("package-info-title");
    if (title !== null) {
      title.innerHTML = "Related";
    }
  }

}
