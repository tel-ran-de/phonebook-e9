import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { AuthMessage } from 'src/app/common/message/auth-message';
import { AuthService } from 'src/app/common/service/auth.service';

@Component({
  selector: 'app-forgot-password-page',
  templateUrl: './forgot-password-page.component.html',
  styleUrls: ['./forgot-password-page.component.css']
})
export class ForgotPasswordPageComponent implements OnInit {

  constructor(private authMessages: AuthMessage,
    private authService: AuthService,
    private router: Router) {
  }

  form: FormGroup
  authSub: Subscription
  message = this.authMessages
  classes: string = 'auth-form animate__animated animate__zoomIn'

  passwordRecoveryError: boolean = false
  passwordRecoveryErrorMessage: string = this.authMessages.EMAIL_WAS_NOT_FOUND
  emailValidationErrorMessage: string = this.authMessages.INVALID_EMAIL_ADDRESS

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

  onSubmit() {
    if (this.form.valid) {
      this.form.disable()
      this.authService.recoveryPassword(this.form.value).subscribe(
        () => this.router.navigate(['/user/forgot-password-info']),
        error => {
          this.passwordRecoveryError = true
          this.passwordRecoveryErrorMessage = this.authMessages.EMAIL_WAS_NOT_FOUND
          this.classes = 'auth-form animate__animated animate__wobble'
          this.form.reset()
          this.form.enable()
        }
      )
    }
  }


}

