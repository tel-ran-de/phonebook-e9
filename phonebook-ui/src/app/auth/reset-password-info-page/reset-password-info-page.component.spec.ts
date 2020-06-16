import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPasswordInfoPageComponent } from './reset-password-info-page.component';

describe('ResetPasswordInfoPageComponent', () => {
  let component: ResetPasswordInfoPageComponent;
  let fixture: ComponentFixture<ResetPasswordInfoPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResetPasswordInfoPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPasswordInfoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
