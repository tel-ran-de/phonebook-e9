import {Component, OnDestroy, OnInit} from '@angular/core'
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { Subscription } from 'rxjs'
import { Router } from '@angular/router'
import { AuthMessage } from 'src/app/common/message/auth-message'
import { UserService } from '../../common/service/user.service'

@Component({
  selector: 'app-forgot-password-page',
  templateUrl: './forgot-password-page.component.html',
  styleUrls: ['./forgot-password-page.component.css']
})
export class ForgotPasswordPageComponent implements OnInit, OnDestroy {

  constructor(private authMessages: AuthMessage,
              private userService: UserService,
              private router: Router) {
  }

  form: FormGroup
  authSub: Subscription
  message = this.authMessages
  classes: string = 'auth-form animate__animated animate__zoomIn'

  passwordRecoveryError: boolean = false
  passwordRecoveryErrorMessage: string
  emailValidationErrorMessage: string = this.authMessages.INVALID_EMAIL_ADDRESS

  ngOnInit(): void {
    console.log(30)
    this.form = new FormGroup({
      email: new FormControl(null, [
        Validators.required,
        Validators.pattern("^[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,10}$")])
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
      this.userService.recoveryPassword(this.form.value).subscribe(
        () => this.router.navigate(['/user/forgot-password-info']),
        error => {
          this.passwordRecoveryError = true
          this.passwordRecoveryErrorMessage = this.getErrorMessage(error)
          this.classes = 'auth-form animate__animated animate__wobble'
          this.form.reset()
          this.form.enable()
        }
      )
      this.classes = 'auth-form animate__animated'
    }
  }

  getErrorMessage(error: any) {
    let message;
    if (error.status === 0)
      message = 'Server unavailable, try again later';
    else
      message = error.error.message;
    return message;
  }
}

