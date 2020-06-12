import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-updated-password-page',
  templateUrl: './updated-password-page.component.html',
  styleUrls: ['../success-page/success-page.component.css', './updated-password-page.component.css']
})
export class UpdatedPasswordPageComponent implements OnInit {

  constructor() { }

  classes: string = 'auth-info animate__animated animate__zoomIn'

  ngOnInit(): void {
  }

}
