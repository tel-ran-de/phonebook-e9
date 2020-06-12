import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reset-password-message-page',
  templateUrl: './reset-password-message-page.component.html',
  styleUrls: ['./reset-password-message-page.component.css']
})
export class ResetPasswordMessagePageComponent implements OnInit {

  constructor() { }

  classes: string = 'auth-info animate__animated animate__zoomIn'

  ngOnInit(): void {
  }

}
