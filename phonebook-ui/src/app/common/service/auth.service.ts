import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../interface/user.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  resetPassword(user: User): Observable<User> {
    return this.http.post<User>('https://mysterious-river-75307.herokuapp.com/api/auth/reset-password', user)
  }

  sendNewPassword(user: User, token: string): Observable<User> {
    return this.http.post<User>(`https://mysterious-river-75307.herokuapp.com/api/auth/create-new-password?token=${token}`, user)
  }

}
