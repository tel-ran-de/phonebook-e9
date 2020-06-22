import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reset-password-info-page',
  templateUrl: './reset-password-info-page.component.html',
  styleUrls: ['./reset-password-info-page.component.css']
})
export class ResetPasswordInfoPageComponent implements OnInit {

  constructor() { }

  classes: string = 'auth-info animate__animated animate__zoomIn'
  message: string = 'Your password was successfully updated!'

  ngOnInit(): void {
  }

}
