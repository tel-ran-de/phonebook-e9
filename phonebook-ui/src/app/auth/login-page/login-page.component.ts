import { Component, OnInit } from '@angular/core';
import {AuthMessages} from "../../common/messages/auth-messages";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['../register-page/register-page.component.css', './login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  constructor(private authMessages: AuthMessages) { }

  form: FormGroup
  message = this.authMessages

  ngOnInit(): void {
    this.form = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.email]),
      password: new FormControl(null, [Validators.required, Validators.minLength(8)])
    })
  }

  OnSubmit() {

  }

}
