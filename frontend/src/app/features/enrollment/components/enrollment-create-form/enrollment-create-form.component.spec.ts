import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrollmentCreateFormComponent } from './enrollment-create-form.component';

describe('EnrollmentCreateFormComponent', () => {
  let component: EnrollmentCreateFormComponent;
  let fixture: ComponentFixture<EnrollmentCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EnrollmentCreateFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EnrollmentCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
