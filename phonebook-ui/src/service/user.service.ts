import {Injectable} from '@angular/core';
import {User} from "../app/common/model/user";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private host = 'http://localhost:8080/';

  constructor(private http: HttpClient) {
  }

  register(user: User) {
    const url = `${this.host}api/v1/registration`;
    return this.http.post<User>(url, user);
  }

  sendToken(token: string) {
    const url = `${this.host}api/v1/validation`;
    return this.http.post(url, token);
  }
}
