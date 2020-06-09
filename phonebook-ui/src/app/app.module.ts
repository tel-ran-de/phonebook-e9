import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegisterPageComponent } from './auth/register-page/register-page.component';
import { AuthLayoutComponent } from './common/layout/auth-layout/auth-layout.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { LoginPageComponent } from './auth/login-page/login-page.component';
import { ConfirmationPageComponent } from './auth/confirmation-page/confirmation-page.component';
import { SuccessPageComponent } from './auth/success-page/success-page.component';
import { ResetPasswordPageComponent } from './auth/reset-password-page/reset-password-page.component';
import { NewPasswordPageComponent } from './auth/new-password-page/new-password-page.component';
import { UpdatedPasswordPageComponent } from './auth/updated-password-page/updated-password-page.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterPageComponent,
    AuthLayoutComponent,
    LoginPageComponent,
    ConfirmationPageComponent,
    SuccessPageComponent,
    ResetPasswordPageComponent,
    NewPasswordPageComponent,
    UpdatedPasswordPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
