import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegisterPageComponent} from './register-page.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import {UserService} from "../../common/service/user.service";
import {User} from "../../common/model/user";

describe('Component: Login', () => {

  let component: RegisterPageComponent;
  let fixture: ComponentFixture<RegisterPageComponent>;
  let userServiceStub: Partial<UserService>;
  let userService;
  userServiceStub = {
    registerNewUser(user: User): Observable<User> {
      user.email = 'mail.user@mail.com';
      user.password = 'passuser1';
      return;
    }
  };

  const fakeActivatedRoute = {
    snapshot: {
      queryParams: {
        returnUrl: '/'
      }
    }
  };
  const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule, HttpClientTestingModule],
      declarations: [RegisterPageComponent],
      providers: [
        {provide: UserService, useValue: userServiceStub},
        {provide: Router, useValue: routerSpy},
        {provide: ActivatedRoute, useFactory: () => fakeActivatedRoute}
      ]
    });

    fixture = TestBed.createComponent(RegisterPageComponent);

    component = fixture.componentInstance;

    userService = TestBed.inject(UserService);

    component.ngOnInit();
  });


  it('form empty. Form invalid', () => {
    expect(component.form.valid).toBeFalsy();
  });

  it('password length 5. Password.minlength.error, form invalid ', () => {
    let errors = {};
    let password = component.form.controls['password'];

    password.setValue("123456");
    errors = password.errors || {};
    expect(errors['minlength']).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('password field length 30. Password.maxlength.error and form invalid', () => {
    let errors = {};
    let password = component.form.controls['password'];

    password.setValue("123456789012345678901234567890");
    errors = password.errors || {};
    expect(errors['maxlength']).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('password field empty, email valid. Email valid, form invalid', () => {
    let errors = {};
    let password = component.form.controls['password'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.ok")
    password.setValue("");
    errors = password.errors || {};
    expect(errors['required']).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('password confirm not equals password, email valid. Email valid, password valid, passwordConfirm invalid, form invalid', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.ok")
    password.setValue("1234567890");
    passwordConfirm.setValue("12345678901");

    expect(email.valid).toBeTruthy();
    expect(password.valid).toBeTruthy();
    expect(component.checkPasswords(component.form)).toEqual({notSame: true});
    expect(component.form.invalid).toBeTruthy();
  });

  it('password confirm equals password, email valid. Email valid, password valid, passwordConfirm valid, form valid', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.ok")
    password.setValue("12345678901");
    passwordConfirm.setValue("12345678901");

    expect(email.valid).toBeTruthy();
    expect(password.valid).toBeTruthy();
    expect(passwordConfirm.valid).toBeTruthy();
    expect(component.form.valid).toBeTruthy();
  });

  it('password confirm equals password, email invalid. Email invalid, password valid, passwordConfirm valid, form invalid', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.")
    password.setValue("12345678901");
    passwordConfirm.setValue("12345678901");

    expect(email.invalid).toBeTruthy();
    expect(password.valid).toBeTruthy();
    expect(passwordConfirm.valid).toBeTruthy();
    expect(component.form.invalid).toBeTruthy();
  });

  it('email without domain name and . ("email@valid") invalid. Email invalid, password valid, passwordConfirm valid, form invalid', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid")
    password.setValue("12345678901");
    passwordConfirm.setValue("12345678901");

    expect(email.invalid).toBeTruthy();
    expect(password.valid).toBeTruthy();
    expect(passwordConfirm.valid).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('email without domain name ("email@valid.") invalid. Email invalid, password valid, passwordConfirm valid, form invalid', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.")
    password.setValue("12345678901");
    passwordConfirm.setValue("12345678901");

    expect(email.invalid).toBeTruthy();
    expect(password.valid).toBeTruthy();
    expect(passwordConfirm.valid).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('email with too short domain name ("email@valid.c") invalid. Email invalid, password valid, passwordConfirm valid, form invalid', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.")
    password.setValue("12345678901");
    passwordConfirm.setValue("12345678901");

    expect(email.invalid).toBeTruthy();
    expect(password.valid).toBeTruthy();
    expect(passwordConfirm.valid).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('email wirt domain names("email@valid.com.com") invalid. Email invalid, password valid, passwordConfirm valid, form invalid', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.")
    password.setValue("12345678901");
    passwordConfirm.setValue("12345678901");

    expect(email.invalid).toBeTruthy();
    expect(password.valid).toBeTruthy();
    expect(passwordConfirm.valid).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('email domain too long ("email@valid.comcomcomcomcomcom") invalid. Email invalid, password valid, passwordConfirm valid, form invalid', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.comcomcomcomcomcom")
    password.setValue("12345678901");
    passwordConfirm.setValue("12345678901");

    expect(email.invalid).toBeTruthy();
    expect(password.valid).toBeTruthy();
    expect(passwordConfirm.valid).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('on submit', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.ok")
    password.setValue("12345678901");
    passwordConfirm.setValue("1234567890");
    component.loading = false;
    component.onSubmit()

    expect(component.submitted).toBeTrue();
    expect(component.form.valid).toBeFalsy();
    expect(component.loading).toBeFalse();
  });
});
