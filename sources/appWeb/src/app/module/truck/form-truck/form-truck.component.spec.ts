import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormTruckComponent } from './form-truck.component';

describe('FormCamionComponent', () => {
  let component: FormTruckComponent;
  let fixture: ComponentFixture<FormTruckComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormTruckComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormTruckComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
