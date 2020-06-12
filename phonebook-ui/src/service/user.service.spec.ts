import {TestBed} from '@angular/core/testing';

import { UserService } from './user.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe("Service: Search", () => {
  let service: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });
    // Returns a service with the MockBackend so we can test with dummy responses
    service = TestBed.get(UserService);
    // Inject the http service and test controller for each test
    httpTestingController = TestBed.get(HttpTestingController);
  });

  afterEach(() => {
    // After every test, assert that there are no more pending requests.
    httpTestingController.verify();
  });
});
