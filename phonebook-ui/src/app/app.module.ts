import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from "./app.component";
import {RegisterComponent} from "./auth/register/register.component";
import {ActivationComponent} from "./auth/activation/activation.component";
import {PendingComponent} from "./auth/pending/pending.component";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AuthLayoutComponent} from "./common/layout/auth-layout/auth-layout.component";


@NgModule({
  declarations: [
    AppComponent,
    AuthLayoutComponent,
    RegisterComponent,
    ActivationComponent,
    PendingComponent,
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
