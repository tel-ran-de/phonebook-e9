import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegisterPageComponent} from './register-page.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {UserService} from "../../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../../common/model/user";

describe('Component: Login', () => {

  let component: RegisterPageComponent;
  let fixture: ComponentFixture<RegisterPageComponent>;
  let userServiceStub: Partial<UserService>;
  let userService;
  // httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
  userServiceStub = {
    register(user: User): Observable<User> {
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


  // userService: UserService = fixture.debugElement.injector.get(UserService);

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

    // create component and test fixture
    fixture = TestBed.createComponent(RegisterPageComponent);

    // get test component from the fixture
    component = fixture.componentInstance;

    // UserService from the root injector
    userService = TestBed.inject(UserService);


    component.ngOnInit();
  });


  it('form empty. form invalid', () => {
    expect(component.form.valid).toBeFalsy();
  });

  it('password field minlength 5, password.minlength. error and form invalid ', () => {
    let errors = {};
    let password = component.form.controls['password'];

    password.setValue("123456");
    errors = password.errors || {};
    expect(errors['minlength']).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('password field maxlength 30. password. maxlength.error and form invalid', () => {
    let errors = {};
    let password = component.form.controls['password'];

    password.setValue("123456789012345678901234567890");
    errors = password.errors || {};
    expect(errors['maxlength']).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('password field empty, email valid. email valid and form invalid', () => {
    let errors = {};
    let password = component.form.controls['password'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.ok")
    password.setValue("");
    errors = password.errors || {};
    expect(errors['required']).toBeTruthy();
    expect(component.form.valid).toBeFalsy();
  });

  it('password confirm not equals password, email valid. email valid and form invalid', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.ok")
    password.setValue("1234567890");
    passwordConfirm.setValue("12345678901");

    expect(component.form.valid).toBeFalsy();
  });

  it('password confirm equals password, email valid. email valid and form valid', () => {
    let password = component.form.controls['password'];
    let passwordConfirm = component.form.controls['passwordConfirm'];
    let email = component.form.controls['email'];

    email.setValue("email@valid.ok")
    password.setValue("12345678901");
    passwordConfirm.setValue("12345678901");

    expect(component.form.valid).toBeTruthy();
  });

  // it('email ("email@valid") invalid. form invalid', () => {
  //   let errors = {};
  //   let password = component.form.controls['password'];
  //   let passwordConfirm = component.form.controls['passwordConfirm'];
  //   let email = component.form.controls['email'];
  //
  //   email.setValue("email@valid")
  //   password.setValue("12345678901");
  //   passwordConfirm.setValue("12345678901");
  //
  //   expect(errors['email']).toBeTruthy();
  //   expect(component.form.valid).toBeFalsy();
  // });

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
