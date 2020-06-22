import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgotPasswordInfoPageComponent } from './forgot-password-info-page.component';

describe('ForgotPasswordInfoPageComponent', () => {
  let component: ForgotPasswordInfoPageComponent;
  let fixture: ComponentFixture<ForgotPasswordInfoPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForgotPasswordInfoPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForgotPasswordInfoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
