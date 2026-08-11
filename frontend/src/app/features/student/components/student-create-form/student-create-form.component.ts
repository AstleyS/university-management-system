import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StudentService } from '../../services/student.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Gender} from '../../../../core/enums/Gender';

@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './student-create-form.component.html',
  styleUrl: './student-create-form.component.scss'
})
export class StudentCreateFormComponent {

  studentForm: FormGroup;
  loading = false;
  errorMessage = '';

  genders = Object.values(Gender);

  constructor(
    private fb: FormBuilder,
    private studentService: StudentService,
    private router: Router
  ) {
    this.studentForm = this.fb.group({

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
    if (this.studentForm.invalid) {
      this.studentForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.studentService.createStudent(this.studentForm.value).subscribe({
      next: (student) => {
        console.log('Student created:', student);
        this.router.navigate(['/students']);
      },

      error: (error) => {
        console.error('Create student error:', error);

        this.loading = false;

        this.errorMessage =
          error?.error?.message ?? 'Failed to create student';
      }
    });
  }
}
