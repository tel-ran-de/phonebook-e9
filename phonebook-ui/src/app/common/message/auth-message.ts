import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class AuthMessage {
  INVALID_EMAIL_ADDRESS: string = 'Invalid email address'
  INVALID_PASSWORD: string = 'Invalid password'
  INVALID_NAME: string = 'Invalid name'
  EMAIL_WAS_NOT_FOUND: string = 'The user with this email address was not found'
  PASSWORD_CANNOT_BE_EMPTY: string = 'The password can\'t be empty'
  PASSWORDS_MUST_MATCH: string = 'Passwords must match'
}
