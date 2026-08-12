import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessorCreatFormComponent } from './professor-creat-form.component';

describe('ProfessorCreatFormComponent', () => {
  let component: ProfessorCreatFormComponent;
  let fixture: ComponentFixture<ProfessorCreatFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfessorCreatFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfessorCreatFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
