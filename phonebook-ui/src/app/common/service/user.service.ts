import {Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs'
import {User} from '../interface/user.interface'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  registerNewUser(user: User) {
    return this.http.post<User>('/api/user/registration', user)
  }

  sendRequestToRegisterConfirm(token: string) {
    return this.http.get(`/api/user/confirmation?token=${token}`)
  }

  recoveryPassword(user: User): Observable<User> {
    return this.http.post<User>('/api/user/reset-password', user)
  }

  resetPassword(user: User, token: string): Observable<User> {
    return this.http.put<User>(`/api/user/password?token=${token}`, user)
  }
}
