import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../interface/user.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  recoveryPassword(user: User): Observable<User> {
    return this.http.post<User>('/api/reset-password', user)
  }

  resetPassword(user: User, token: string): Observable<User> {
    return this.http.post<User>(`/api/reset-password?token=${token}`, user)
  }

}
