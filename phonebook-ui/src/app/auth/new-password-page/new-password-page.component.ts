import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AuthMessage} from "../../common/message/auth-message";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../common/service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-new-password-page',
  templateUrl: './new-password-page.component.html',
  styleUrls: ['../register-page/register-page.component.css', './new-password-page.component.css']
})
export class NewPasswordPageComponent implements OnInit {

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
      this.auth.sendNewPassword(this.form.value, this.route.snapshot.queryParams.token).subscribe(
        () => this.router.navigate(['/update-password-success']),
        error => {
          this.classes = 'auth-form  animate__animated animate__wobble'
          this.form.reset()
          this.form.enable()
        }
      )
    }
  }

}
