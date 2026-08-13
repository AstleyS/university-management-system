import {Component, OnInit} from '@angular/core';
import {Student, StudentRequest} from '../../models/student';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {StudentService} from '../../services/student.service';
import {EnrollmentService} from '../../../enrollment/services/enrollment.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'app-student-detail',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './student-detail.component.html',
  styleUrl: './student-detail.component.scss'
})
export class StudentDetailComponent implements OnInit {

  student?: Student;

  loading = true;
  errorMessage = '';

  editing = false;
  saving = false;

  studentForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private studentService: StudentService,
    private enrollmentService: EnrollmentService
  ) {

    this.studentForm = this.fb.group({

      firstName: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(30)
        ]
      ],
      lastName: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
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

  ngOnInit() {

    const id = Number(this.route.snapshot.paramMap.get('id'))
    if (!id) {
      this.errorMessage = 'Invalid student ID';
      this.loading = false;
      return;
    }

    this.studentService.getStudentById(id).subscribe({

      next: (data) => {
        console.log(`Student with id ${id}: `, data);
        this.loading = false;
        this.student = data;
      },

      error: (error) => {
        console.error(error);
        this.errorMessage = 'Failed to load student';
        this.loading = false;
      }
    });
  }

  startEdit() {

    if (!this.student) {
      return;
    }

    this.studentForm.patchValue({
      firstName: this.student.firstName,
      lastName: this.student.lastName,
      gender: this.student.gender,
      dateOfBirth: this.student.dateOfBirth,
      email: this.student.email
    });

    this.editing = true;
  }

  cancelEdit() {

    this.editing = false;
    this.studentForm.reset();
  }

  saveStudent() {

    if (!this.student || this.studentForm.invalid) {
      this.studentForm.markAllAsTouched();
      return;
    }

    this.saving = true;
    this.errorMessage = '';

    const request: StudentRequest = this.studentForm.getRawValue();

    this.studentService.updateStudent(this.student.id, request).subscribe({

        next: (updatedStudent) => {

          console.log('Student updated:', updatedStudent);

          this.student = updatedStudent;
          this.editing = false;
          this.saving = false;
        },

        error: (error) => {

          console.error(error);

          this.errorMessage =
            error?.error?.message ?? 'Failed to update student';

          this.saving = false;
        }

      });
  }

}
