import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthMessage} from "../../common/message/auth-message";
import {AuthService} from "../../common/service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-reset-password-page',
  templateUrl: './reset-password-page.component.html',
  styleUrls: ['../register-page/register-page.component.css', './reset-password-page.component.css']
})
export class ResetPasswordPageComponent implements OnInit, OnDestroy {

  constructor(private authMessages: AuthMessage,
              private authService: AuthService,
              private router: Router) {
  }

  form: FormGroup
  authSub: Subscription
  message = this.authMessages
  classes: string = 'auth-form animate__animated animate__zoomIn'
  emailWasNotFound: boolean = false
  emailWasNotFoundMessage: string = this.authMessages.EMAIL_WAS_NOT_FOUND
  emailErrorMessage: string = this.authMessages.INVALID_EMAIL_ADDRESS

  ngOnInit(): void {
    this.form = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.email])
    })
  }

  ngOnDestroy() {
    if (this.authSub) {
      this.authSub.unsubscribe()
    }
  }

  OnSubmit() {
    if (this.form.valid) {
      this.form.disable()
      this.authService.resetPassword(this.form.value).subscribe(
        () => this.router.navigate(['/reset-password-message']),
        error => {
          this.emailWasNotFound = true
          this.classes = 'auth-form animate__animated animate__wobble'
          this.form.reset()
          this.form.enable()
        }
      )
    }
  }

}
