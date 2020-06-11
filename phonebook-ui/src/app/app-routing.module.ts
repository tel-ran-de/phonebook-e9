import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterPageComponent } from "./auth/register-page/register-page.component";
import {AuthLayoutComponent} from "./common/layout/auth-layout/auth-layout.component";
import {LoginPageComponent} from "./auth/login-page/login-page.component";
import {ConfirmationPageComponent} from "./auth/confirmation-page/confirmation-page.component";
import {SuccessPageComponent} from "./auth/success-page/success-page.component";


const routes: Routes = [
  {
    path: '', component: AuthLayoutComponent, children: [
      { path: '', redirectTo: '/login', pathMatch: 'full' },
      { path: 'login', component: LoginPageComponent },
      { path: 'user/register', component: RegisterPageComponent },
      { path: 'user/activation', component: ConfirmationPageComponent },
      { path: 'user/pending/:token', component: SuccessPageComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
