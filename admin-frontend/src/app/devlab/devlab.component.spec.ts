/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DevlabComponent } from './devlab.component';

describe('DevlabComponent', () => {
  let component: DevlabComponent;
  let fixture: ComponentFixture<DevlabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DevlabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DevlabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
