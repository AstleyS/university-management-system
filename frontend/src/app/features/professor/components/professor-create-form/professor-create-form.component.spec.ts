import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessorCreateFormComponent } from './professor-create-form.component';

describe('ProfessorCreateFormComponent', () => {
  let component: ProfessorCreateFormComponent;
  let fixture: ComponentFixture<ProfessorCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfessorCreateFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfessorCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
