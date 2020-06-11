import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterPageComponent } from "./auth/register-page/register-page.component";
import {AuthLayoutComponent} from "./common/layout/auth-layout/auth-layout.component";
import {LoginPageComponent} from "./auth/login-page/login-page.component";
import {ConfirmationPageComponent} from "./auth/confirmation-page/confirmation-page.component";
import {SuccessPageComponent} from "./auth/success-page/success-page.component";
import {ResetPasswordPageComponent} from "./auth/reset-password-page/reset-password-page.component";
import {NewPasswordPageComponent} from "./auth/new-password-page/new-password-page.component";
import {UpdatedPasswordPageComponent} from "./auth/updated-password-page/updated-password-page.component";


let UpdatedPasswordPageComponentt;
const routes: Routes = [
  {
    path: '', component: AuthLayoutComponent, children: [
      { path: '', redirectTo: '/login', pathMatch: 'full' },
      { path: 'login', component: LoginPageComponent },
      { path: 'register', component: RegisterPageComponent },
      { path: 'confirmation', component: ConfirmationPageComponent },
      { path: 'success', component: SuccessPageComponent },
      { path: 'reset-password', component: ResetPasswordPageComponent },
      { path: 'new-password', component: NewPasswordPageComponent },
      { path: 'updated-password', component: UpdatedPasswordPageComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
