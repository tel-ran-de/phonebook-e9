
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthLayoutComponent } from "./common/layout/auth-layout/auth-layout.component";
import { RegisterPageComponent } from "./auth/register-page/register-page.component";
import { PendingPageComponent } from "./auth/pending-page/pending.component";
import { ActivationPageComponent } from "./auth/activation-page/activation-page.component";
import { ForgotPasswordPageComponent } from './auth/forgot-password-page/forgot-password-page.component';
import { ForgotPasswordInfoPageComponent } from './auth/forgot-password-info-page/forgot-password-info-page.component';
import { ResetPasswordPageComponent } from './auth/reset-password-page/reset-password-page.component';
import { ResetPasswordInfoPageComponent } from './auth/reset-password-info-page/reset-password-info-page.component';


const routes: Routes = [
  {
    path: '', component: AuthLayoutComponent, children: [
      // { path: '', redirectTo: '/login', pathMatch: 'full' },
      { path: '', redirectTo: 'user/register', pathMatch: 'full' },
      { path: 'user/register', component: RegisterPageComponent },
      { path: 'user/pending', component: PendingPageComponent },
      { path: 'user/activation/:token', component: ActivationPageComponent },
      { path: 'user/forgot-password', component: ForgotPasswordPageComponent },
      { path: 'user/forgot-password-info', component: ForgotPasswordInfoPageComponent },
      { path: 'user/reset-password', component: ResetPasswordPageComponent },
      { path: 'user/reset-password-info', component: ResetPasswordInfoPageComponent },
    ]
  },
  {
    path: '**', redirectTo: 'user/register'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
