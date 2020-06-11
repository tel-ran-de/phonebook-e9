import { Component, OnInit } from '@angular/core';
import {AuthMessages} from "../../common/messages/auth-messages";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-new-password-page',
  templateUrl: './new-password-page.component.html',
  styleUrls: ['../register-page/register-page.component.css', './new-password-page.component.css']
})
export class NewPasswordPageComponent implements OnInit {

  constructor(private authMessages: AuthMessages) { }

  form: FormGroup
  message = this.authMessages

  ngOnInit(): void {
    this.form = new FormGroup({
      password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl(null, [Validators.required, Validators.minLength(8)])
    })
  }

  OnSubmit() {

  }

}
