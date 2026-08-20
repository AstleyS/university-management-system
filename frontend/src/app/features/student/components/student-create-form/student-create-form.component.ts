import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import { CommonModule } from '@angular/common';
import { StudentService } from '../../services/student.service';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {Gender} from '../../../../core/enums/Gender';
import {Course} from '../../../course/models/course';
import {CourseService} from '../../../course/services/course.service';
import {EnrollmentService} from '../../../enrollment/services/enrollment.service';
import {EnrollmentStatus} from '../../../../core/enums/EnrollmentStatus';
import {Semester} from '../../../semester/models/semester';
import {forkJoin} from 'rxjs';
import {SemesterService} from '../../../semester/services/semester.service';

@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './student-create-form.component.html',
  styleUrl: './student-create-form.component.scss'
})
export class StudentCreateFormComponent implements OnInit {

  studentForm: FormGroup;

  courses: Course[] = [];
  semesters: Semester[] = [];

  loading = false;
  errorMessage = '';

  genders = Object.values(Gender);

  constructor(
    private fb: FormBuilder,
    private studentService: StudentService,
    private courseService: CourseService,
    private enrollmentService: EnrollmentService,
    private semesterService: SemesterService,
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
      ],

      semesterId: [
        ''
      ]

    });
  }

  ngOnInit() {

    forkJoin({
      courses: this.courseService.getCourses(),
      semesters: this.semesterService.getSemesters()
    }).subscribe({

      next: ({courses, semesters}) => {

        this.courses = courses;
        this.semesters = semesters;

      },

      error: (error) => {

        console.error(error);

        this.errorMessage =
          'Failed to load courses and semesters';

      }

    });
  }

  toggleCourse(courseId: number, event: Event) {

    const checked =
      (event.target as HTMLInputElement).checked;

    const currentCourseIds: number[] =
      this.studentForm.get('courseIds')?.value ?? [];

    if (checked) {

      if (!currentCourseIds.includes(courseId)) {

        this.studentForm.patchValue({
          courseIds: [
            ...currentCourseIds,
            courseId
          ]
        });

      }

    } else {

      this.studentForm.patchValue({
        courseIds:
          currentCourseIds.filter(
            id => id !== courseId
          )
      });

    }
  }

  onSubmit() {

    if (this.studentForm.invalid) {

      this.studentForm.markAllAsTouched();

      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const formValue =
      this.studentForm.getRawValue();

    const courseIds: number[] =
      formValue.courseIds ?? [];

    const semesterId =
      formValue.semesterId
        ? Number(formValue.semesterId)
        : null;


    /*
     * A semester is required only when
     * the admin selected courses.
     */
    if (
      courseIds.length > 0 &&
      !semesterId
    ) {

      this.studentForm
        .get('semesterId')
        ?.markAsTouched();

      this.loading = false;

      this.errorMessage =
        'Please select a semester for the selected courses.';

      return;
    }


    /*
     * Don't send courseIds or semesterId
     * to the Student API.
     */
    const studentData = {
      firstName: formValue.firstName,
      lastName: formValue.lastName,
      gender: formValue.gender,
      dateOfBirth: formValue.dateOfBirth,
      email: formValue.email
    };


    this.studentService.createStudent(studentData).subscribe({

      next: (student) => {

        /*
         * No courses selected.
         * Student creation is complete.
         */
        if (
          courseIds.length === 0 ||
          !semesterId
        ) {

          this.loading = false;

          this.router.navigate(['/students']);

          return;
        }


        /*
         * Create one enrollment for
         * each selected course.
         */
        const enrollmentRequests =
          courseIds.map(courseId =>

            this.enrollmentService.createEnrollment({

              studentId: student.id,

              courseId: Number(courseId),

              semesterId: semesterId,

              enrollmentDate:
                new Date()
                  .toISOString()
                  .split('T')[0],

              enrollmentStatus:
              EnrollmentStatus.ACTIVE,

              grade: null

            })

          );


        /*
         * Wait until every enrollment
         * has been created.
         */
        forkJoin(enrollmentRequests).subscribe({

          next: (enrollments) => {

            console.log(
              'All enrollments created:',
              enrollments
            );

            this.loading = false;

            this.router.navigate([
              '/students'
            ]);

          },

          error: (error) => {

            this.loading = false;

            console.error(
              'Failed to create enrollments:',
              error
            );

            this.errorMessage =
              'Student was created, but some enrollments failed.';

          }

        });

      },

      error: (error) => {

        this.loading = false;

        console.error(
          'Create student error:',
          error
        );

        this.errorMessage =
          error?.error?.message ??
          'Failed to create student';

      }

    });
  }
}
