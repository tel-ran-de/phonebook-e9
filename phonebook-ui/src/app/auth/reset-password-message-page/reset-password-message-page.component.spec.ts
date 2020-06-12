import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPasswordMessagePageComponent } from './reset-password-message-page.component';

describe('ResetPasswordMessagePageComponent', () => {
  let component: ResetPasswordMessagePageComponent;
  let fixture: ComponentFixture<ResetPasswordMessagePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResetPasswordMessagePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPasswordMessagePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
