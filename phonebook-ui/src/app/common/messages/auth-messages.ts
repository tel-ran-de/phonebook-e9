import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class AuthMessages {
  INVALID_EMAIL_ADDRESS: string = 'Invalid email address'
  INVALID_PASSWORD: string = 'Invalid password'
  INVALID_NAME: string = 'Invalid name'
}
