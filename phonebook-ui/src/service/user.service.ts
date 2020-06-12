import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private host = 'http://localhost:8080/';

  constructor(private http: HttpClient) {
  }

  registerNewUser(user: User) {
    const url = `${this.host}api/v1/registration`;
    return this.http.post<User>(url, user);
  }

  sendRequestToRegisterConfirm(token: string) {
    const url = `${this.host}api/v1/confirmation?token=${token}`;
    return this.http.get(url);
  }
}
