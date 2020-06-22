import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-forgot-password-info-page',
  templateUrl: './forgot-password-info-page.component.html',
  styleUrls: ['./forgot-password-info-page.component.css']
})
export class ForgotPasswordInfoPageComponent implements OnInit {

  constructor() { }

  classes: string = 'auth-info animate__animated animate__zoomIn'
  message: string = 'Link to restore your password was sent to your email.'

  ngOnInit(): void {
  }

}
