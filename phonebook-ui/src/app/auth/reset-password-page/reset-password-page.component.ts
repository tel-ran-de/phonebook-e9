import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/common/service/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthMessage } from 'src/app/common/message/auth-message';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-reset-password-page',
  templateUrl: './reset-password-page.component.html',
  styleUrls: ['./reset-password-page.component.css']
})
export class ResetPasswordPageComponent implements OnInit {

  constructor(private auth: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private authMessages: AuthMessage
  ) {
  }

  form: FormGroup
  aSub: Subscription
  message: string = this.authMessages.INVALID_PASSWORD
  classes: string = 'auth-form animate__animated animate__zoomIn'
  passwordMustMatch: string = this.authMessages.PASSWORDS_MUST_MATCH

  ngOnInit(): void {
    this.form = new FormGroup({
      password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl(null, [Validators.required, Validators.minLength(8)])
    })
  }

  ngOnDestroy() {
    if (this.aSub) {
      this.aSub.unsubscribe()
    }
  }

  onSubmit() {
    if (this.form.valid && (this.form.get('password').value === this.form.get('confirmPassword').value)) {
      this.form.disable()
      this.auth.resetPassword(this.form.value, this.route.snapshot.queryParams.token).subscribe(
        () => this.router.navigate(['/user/reset-password-info']),
        error => {
          this.classes = 'auth-form  animate__animated animate__wobble'
          this.form.reset()
          this.form.enable()
        }
      )
    }
  }

}
