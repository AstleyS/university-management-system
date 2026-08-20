import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';

import { EnrollmentService } from '../../services/enrollment.service';
import {
  EnrollmentCreateRequest
} from '../../models/enrollment';

import { Student } from '../../../student/models/student';
import { StudentService } from '../../../student/services/student.service';

import { Course } from '../../../course/models/course';
import { CourseService } from '../../../course/services/course.service';

import { Semester } from '../../../semester/models/semester';
import { SemesterService } from '../../../semester/services/semester.service';
import {EnrollmentStatus} from '../../../../core/enums/EnrollmentStatus';


@Component({
  selector: 'app-enrollment-create-form',
  standalone: true,
  imports: [ ReactiveFormsModule ],
  templateUrl: './enrollment-create-form.component.html',
  styleUrl: './enrollment-create-form.component.scss'

})
export class EnrollmentCreateFormComponent implements OnInit {

  enrollmentForm: FormGroup;

  students: Student[] = [];
  courses: Course[] = [];
  semesters: Semester[] = [];

  enrollmentStatuses =
    Object.values(EnrollmentStatus);

  loading = true;
  saving = false;

  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private enrollmentService: EnrollmentService,
    private studentService: StudentService,
    private courseService: CourseService,
    private semesterService: SemesterService,
    private router: Router
  ) {

    this.enrollmentForm = this.fb.group({

      studentId: [
        '',
        Validators.required
      ],

      courseId: [
        '',
        Validators.required
      ],

      semesterId: [
        '',
        Validators.required
      ],

      enrollmentDate: [
        '',
        Validators.required
      ],

      enrollmentStatus: [
        EnrollmentStatus.ACTIVE,
        Validators.required
      ],

      grade: [
        null
      ]

    });

  }


  ngOnInit(): void {

    this.loadFormData();

  }


  loadFormData(): void {

    this.loading = true;
    this.errorMessage = '';

    forkJoin({

      students:
        this.studentService.getStudents(),

      courses:
        this.courseService.getCourses(),

      semesters:
        this.semesterService.getSemesters()

    }).subscribe({

      next: ({
               students,
               courses,
               semesters
             }) => {

        this.students = students;
        this.courses = courses;
        this.semesters = semesters;

        this.loading = false;

      },

      error: (error) => {

        console.error(error);

        this.errorMessage =
          'Failed to load enrollment options';

        this.loading = false;

      }

    });

  }


  onSubmit(): void {

    if (this.enrollmentForm.invalid) {

      this.enrollmentForm.markAllAsTouched();

      return;

    }

    this.saving = true;
    this.errorMessage = '';

    const formValue =
      this.enrollmentForm.getRawValue();


    const request: EnrollmentCreateRequest = {

      studentId:
        Number(formValue.studentId),

      courseId:
        Number(formValue.courseId),

      semesterId:
        Number(formValue.semesterId),

      enrollmentDate:
      formValue.enrollmentDate,

      enrollmentStatus:
      formValue.enrollmentStatus,

      grade: null
    };


    console.log(
      'Creating enrollment:',
      request
    );


    this.enrollmentService
      .createEnrollment(request)
      .subscribe({

        next: (enrollment) => {

          console.log(
            'Enrollment created:',
            enrollment
          );

          this.saving = false;

          this.router.navigate([
            '/enrollments'
          ]);

        },

        error: (error) => {

          console.error(
            'Create enrollment error:',
            error
          );

          this.errorMessage =
            error?.error?.message ??
            'Failed to create enrollment';

          this.saving = false;

        }

      });

  }


  cancel(): void {

    this.router.navigate([
      '/enrollments'
    ]);

  }

}
