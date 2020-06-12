import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegisterComponent} from "./auth/register/register.component";
import {ActivationComponent} from "./auth/activation/activation.component";
import {PendingComponent} from "./auth/pending/pending.component";
import {AuthLayoutComponent} from "./common/layout/auth-layout/auth-layout.component";


const routes: Routes = [
  {
    path: '', component: AuthLayoutComponent, children: [
      // { path: '', redirectTo: '/login', pathMatch: 'full' },
      {path: '', redirectTo: 'user/register', pathMatch: 'full'},
      {path: 'user/register', component: RegisterComponent},
      {path: 'user/activation', component: ActivationComponent},
      {path: 'user/pending/:token', component: PendingComponent},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
