import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private host = 'http://localhost:8080/';
  private registerUrl = `${this.host}api/v1/registration`;
  private registerConfirmUrl = `${this.host}api/v1/confirmation?token=`;

  constructor(private http: HttpClient) {
  }

  registerNewUser(user: User) {
    return this.http.post<User>(this.registerUrl, user);
  }

  sendRequestToRegisterConfirm(token: string) {
    return this.http.get(`${this.registerConfirmUrl}${token}`);
  }
}
