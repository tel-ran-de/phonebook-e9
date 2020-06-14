import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";
import {UserService} from "../../common/service/user.service";

@Component({
  selector: 'app-activation-page',
  templateUrl: './activation-page.component.html',
  styleUrls: ['./activation-page.component.css']
})
export class ActivationPageComponent implements OnInit, OnDestroy {

  private messageSuccess = 'Your email was successfully confirmed!';
  private messageError = 'Your email cannot be verified';
  private subscription: Subscription;

  constructor(private route: ActivatedRoute,
              private userService: UserService) {
  }

  errorStat: boolean;
  confirmMessage: string;

  ngOnInit(): void {
    this.sendToken()
  }

  sendToken() {
    const token = this.route.snapshot.paramMap.get('token');


    this.subscription = this.userService.sendRequestToRegisterConfirm(token).subscribe(_ => {
        this.errorStat = false;
        this.confirmMessage = this.messageSuccess;
      },
      _ => {
        this.errorStat = true;
        this.confirmMessage = this.messageError;
      });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
