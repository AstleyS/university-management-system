import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Gender} from '../../../../core/enums/Gender';
import {ProfessorService} from '../../services/professor.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-professor-creat-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './professor-creat-form.component.html',
  styleUrl: './professor-creat-form.component.scss'
})
export class ProfessorCreatFormComponent {

  professorForm: FormGroup;


  loading = false;
  errorMessage = '';

  genders = Object.values(Gender)

  constructor(
    private fb: FormBuilder,
    private professorService: ProfessorService,
    private router: Router
  ) {

    this.professorForm = this.fb.group({

      firstName: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(30)
        ]
      ],

      lastName: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(30)
        ]
      ],

      gender: [
        '',
        [Validators.required]
      ],

      dateOfBirth: [
        '',
        [Validators.required]
      ],

      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ]
    });
  }

  onSubmit() {
    if (this.professorForm.invalid) {
      this.professorForm.markAllAsTouched();
    }

    this.loading = true;
    this.errorMessage = '';

    this.professorService.createProfessor(this.professorForm.value).subscribe({

      next: (professor) => {
        console.log('Professor created: ', professor)
        this.router.navigate(['professors'])
      },

      error: (error) => {
        console.error('Create professor error:', error);

        this.loading = false;

        this.errorMessage =
          error?.error?.message ?? 'Failed to create professor';
      }
    });
  }
}
