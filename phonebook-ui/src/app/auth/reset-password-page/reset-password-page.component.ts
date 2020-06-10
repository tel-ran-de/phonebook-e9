import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthMessages} from "../../common/messages/auth-messages";

@Component({
  selector: 'app-reset-password-page',
  templateUrl: './reset-password-page.component.html',
  styleUrls: ['../register-page/register-page.component.css', './reset-password-page.component.css']
})
export class ResetPasswordPageComponent implements OnInit {

  constructor(private authMessages: AuthMessages) { }

  form: FormGroup
  message = this.authMessages

  ngOnInit(): void {
    this.form = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.email])
    })
  }

  OnSubmit() {

  }

}
