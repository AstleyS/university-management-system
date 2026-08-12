import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StudentService } from '../../services/student.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Gender} from '../../../../core/enums/Gender';
import {Course} from '../../../course/models/course';
import {CourseService} from '../../../course/services/course.service';
import {EnrollmentService} from '../../../enrollment/services/enrollment.service';
import {EnrollmentCreateRequest} from '../../../enrollment/models/enrollment';
import {EnrollmentStatus} from '../../../../core/enums/EnrollmentStatus';
import {Semester} from '../../../semester/models/semester';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './student-create-form.component.html',
  styleUrl: './student-create-form.component.scss'
})
export class StudentCreateFormComponent implements OnInit {

  studentForm: FormGroup;
  courses?: Course[];
  semesters?: Semester[];

  loading = false;
  errorMessage = '';

  genders = Object.values(Gender);

  constructor(
    private fb: FormBuilder,
    private studentService: StudentService,
    private courseService: CourseService,
    private enrollmentService: EnrollmentService,
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
      ],

      courseIds: [
        []
      ]

    });
  }

  toggleCourse(courseId: number, event: Event) {

    const checked = (event.target as HTMLInputElement).checked;

    const currentCourseIds: number[] =
      this.studentForm.get('courseIds')?.value ?? [];

    if (checked) {
      this.studentForm.patchValue({
        courseIds: [...currentCourseIds, courseId]
      });
    } else {
      this.studentForm.patchValue({
        courseIds: currentCourseIds.filter(id => id !== courseId)
      });
    }
  }

  ngOnInit() {

    this.courseService.getCourses().subscribe({

      next: (courses) => {
        console.log('courses: ', courses);
        this.courses = courses;
      },

      error: err => console.error(err)

    })

  }

  onSubmit() {

    if (this.studentForm.invalid) {
      this.studentForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const formValue = this.studentForm.value;
    const courseIds: number[] = formValue.courseIds ?? [];

    // Don't send courseIds to the Student API
    const studentData = {
      ...formValue
    };

    delete studentData.courseIds;

    this.studentService.createStudent(studentData).subscribe({

      next: (student) => {

        // No courses selected → just create student
        if (courseIds.length === 0) {
          this.loading = false;
          this.router.navigate(['/students']);
          return;
        }

        // Create an Observable for each enrollment
        const enrollmentRequests = courseIds.map(courseId =>
          this.enrollmentService.createEnrollment({
            studentId: student.id,
            courseId: Number(courseId),
            semesterId: 1, // temporary
            enrollmentDate: new Date().toISOString().split('T')[0],
            enrollmentStatus: EnrollmentStatus.ACTIVE
          })
        );

        // Wait until ALL requests finish
        forkJoin(enrollmentRequests).subscribe({

          next: (enrollments) => {
            console.log('All enrollments created:', enrollments);

            this.loading = false;
            this.router.navigate(['/students']);
          },

          error: (error) => {
            this.loading = false;
            console.error('Failed to create enrollments:', error);
            this.errorMessage = 'Student was created, but some enrollments failed.';
          }
        });
      },

      error: (error) => {
        this.loading = false;
        console.error('Create student error:', error);
        this.errorMessage =
          error?.error?.message ?? 'Failed to create student';
      }
    });
  }
}
