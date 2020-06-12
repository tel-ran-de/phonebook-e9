import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../service/user.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-pending',
  templateUrl: './pending.component.html',
  styleUrls: ['./pending.component.css']
})
export class PendingComponent implements OnInit, OnDestroy {
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

    const messageSuccess = 'Your email was successfully confirmed!';
    const messageError = 'Your email cannot be verified';

    this.subscription = this.userService.sendRequestToRegisterConfirm(token).subscribe(value => {
        this.errorStat = false;
        this.confirmMessage = messageSuccess;
      },
      error => {
        this.errorStat = true;
        this.confirmMessage = messageError;
      });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
