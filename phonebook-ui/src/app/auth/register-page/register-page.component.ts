import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../../common/model/user";
import {UserService} from "../../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit, OnDestroy {

  submitted: boolean;
  loading: boolean;
  errorMessage: string;

  form: FormGroup

  private userSubscription: Subscription;

  constructor(private userService: UserService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.form = new FormGroup({
        email: new FormControl(null,
          [
            Validators.required,
            Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9-]+\.[a-z]{2,4}$")
          ]),
        password: new FormControl(null, [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(20)
        ]),
        passwordConfirm: new FormControl(null, [
          Validators.required,
          Validators.minLength(8)
        ])
      }, {validators: this.checkPasswords}
    );
  }

  get f() {
    return this.form.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.loading = true;

    let user: User = new User();
    user.email = this.form.value.email;
    user.password = this.form.value.password;

    this.userSubscription = this.userService.register(user)
      .subscribe(_ => this.router.navigate(['../activation'], {relativeTo: this.route}),
        error => {
          this.loading = false;
          this.errorMessage = errorHandler(error);
        });

    function errorHandler(error: any): string {
      return 'Error: ' + error.statusText;
    }
  }

  checkPasswords(group: FormGroup) {
    let pass = group.get('password').value;
    let confirmPass = group.get('passwordConfirm').value;

    return pass === confirmPass ? null : {notSame: true}
  }

  ngOnDestroy(): void {
    if (this.userSubscription)
      this.userSubscription.unsubscribe();
  }
}
