import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from "./app.component";
import { AppRoutingModule } from "./app-routing.module";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { AuthLayoutComponent } from "./common/layout/auth-layout/auth-layout.component";
import { RegisterPageComponent } from "./auth/register-page/register-page.component";
import { PendingPageComponent } from "./auth/pending-page/pending.component";
import { ActivationPageComponent } from "./auth/activation-page/activation-page.component";
import { ForgotPasswordPageComponent } from './auth/forgot-password-page/forgot-password-page.component';
import { ForgotPasswordInfoPageComponent } from './auth/forgot-password-info-page/forgot-password-info-page.component';
import { ResetPasswordPageComponent } from './auth/reset-password-page/reset-password-page.component';
import { ResetPasswordInfoPageComponent } from './auth/reset-password-info-page/reset-password-info-page.component';


@NgModule({
  declarations: [
    AppComponent,
    AuthLayoutComponent,
    RegisterPageComponent,
    PendingPageComponent,
    ActivationPageComponent,
    ForgotPasswordPageComponent,
    ForgotPasswordInfoPageComponent,
    ResetPasswordPageComponent,
    ResetPasswordInfoPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
