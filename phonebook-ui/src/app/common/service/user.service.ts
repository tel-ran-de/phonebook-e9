import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { User } from '../interface/user.interface'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private host = 'http://localhost:8080/'
  private registerUrl = `${this.host}api/v1/user/registration`
  private registerConfirmUrl = `${this.host}api/v1/user/confirmation?token=`

  constructor(private http: HttpClient) {
  }

  registerNewUser(user: User) {
    return this.http.post<User>(this.registerUrl, user)
  }

  sendRequestToRegisterConfirm(token: string) {
    return this.http.get(`${this.registerConfirmUrl}${token}`)
  }

  recoveryPassword(user: User): Observable<User> {
    return this.http.post<User>('/api/reset-password', user)
  }

  resetPassword(user: User, token: string): Observable<User> {
    return this.http.post<User>(`/api/reset-password?token=${token}`, user)
  }

}
