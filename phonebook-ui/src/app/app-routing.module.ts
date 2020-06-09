import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterPageComponent } from "./auth/register-page/register-page.component";
import {AuthLayoutComponent} from "./common/layout/auth-layout/auth-layout.component";


const routes: Routes = [
  {
    path: '', component: AuthLayoutComponent, children: [
      { path: 'register', component: RegisterPageComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
