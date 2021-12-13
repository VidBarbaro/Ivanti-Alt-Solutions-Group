import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-package',
  templateUrl: './package.component.html',
  styleUrls: ['./package.component.css']
})
export class PackageComponent implements OnInit {
  @Input() public title: string;
  @Input() public description: string;

  constructor() { }

  ngOnInit(): void {
  }

}
