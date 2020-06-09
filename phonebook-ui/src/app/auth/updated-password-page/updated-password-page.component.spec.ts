import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatedPaswordPageComponent } from './updated-password-page.component';

describe('UpdatedPaswordPageComponent', () => {
  let component: UpdatedPaswordPageComponent;
  let fixture: ComponentFixture<UpdatedPaswordPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdatedPaswordPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdatedPaswordPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
