import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from "./app.component";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AuthLayoutComponent} from "./common/layout/auth-layout/auth-layout.component";
import {RegisterPageComponent} from "./auth/register-page/register-page.component";
import {ActivationPageComponent} from "./auth/activation-page/activation-page.component";
import {PendingPageComponent} from "./auth/pending-page/pending-page.component";


@NgModule({
  declarations: [
    AppComponent,
    AuthLayoutComponent,
    RegisterPageComponent,
    ActivationPageComponent,
    PendingPageComponent,
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
