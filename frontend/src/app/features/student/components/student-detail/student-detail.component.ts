import {Component, OnInit} from '@angular/core';
import {Student, StudentRequest} from '../../models/student';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {StudentService} from '../../services/student.service';
import {EnrollmentService} from '../../../enrollment/services/enrollment.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Course} from '../../../course/models/course';
import {Semester} from '../../../semester/models/semester';
import {SemesterService} from '../../../semester/services/semester.service';
import {CourseService} from '../../../course/services/course.service';
import {forkJoin} from 'rxjs';
import {EnrollmentCreateRequest} from '../../../enrollment/models/enrollment';
import {Gender} from '../../../../core/enums/Gender';

@Component({
  selector: 'app-student-detail',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './student-detail.component.html',
  styleUrl: './student-detail.component.scss'
})
export class StudentDetailComponent implements OnInit {

  student?: Student;
  courses: Course[] = []
  semesters: Semester[] = []

  genders = Object.values(Gender)

  studentLoading = true;

  studentErrorMessage = '';
  enrollmentErrorMessage = ''

  editing = false;
  saving = false;
  enrollmentSaving = false;

  managingEnrollments = false;

  studentForm: FormGroup;
  enrollmentForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private studentService: StudentService,
    private courseService: CourseService,
    private semesterService: SemesterService,
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

    this.enrollmentForm = this.fb.group({
      courseId: ['', Validators.required],
      semesterId: ['', Validators.required],
      enrollmentDate: ['', Validators.required],
      enrollmentStatus: ['ACTIVE', Validators.required],
      grade: [null]
    });

  }

  ngOnInit() {

    const id = Number(this.route.snapshot.paramMap.get('id'))
    if (!id) {
      this.studentErrorMessage = 'Invalid student ID';
      this.studentLoading = false;
      return;
    }

    this.studentService.getStudentById(id).subscribe({

      next: (data) => {
        console.log(`Student with id ${id}: `, data);
        this.studentLoading = false;
        this.student = data;
      },

      error: (error) => {
        console.error(error);
        this.studentErrorMessage = 'Failed to load student';
        this.studentLoading = false;
      }
    });
  }

  startStudentInfoEdit() {

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

  cancelStudentInfoEdit() {

    this.editing = false;
    this.studentForm.reset();
  }

  saveStudent() {

    if (!this.student || this.studentForm.invalid) {
      this.studentForm.markAllAsTouched();
      return;
    }

    this.saving = true;
    this.studentErrorMessage = '';

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

          this.studentErrorMessage =
            error?.error?.message ?? 'Failed to update student';

          this.saving = false;
        }

      });
  }

  loadEnrollmentData() {

    forkJoin({
      courses: this.courseService.getCourses(),
      semesters: this.semesterService.getSemesters()
    }).subscribe({

      next: ({ courses, semesters }) => {
        this.courses = courses;
        this.semesters = semesters;
      },

      error: (error) => {
        console.error(error);
        this.enrollmentErrorMessage =
          'Failed to load enrollment options';
      }
    });

  }

  startManagingEnrollments() {

    this.managingEnrollments = true;
    this.enrollmentErrorMessage = '';

    this.loadEnrollmentData();
  }

  stopManagingEnrollments() {

    this.managingEnrollments = false;
    this.enrollmentForm.reset({
      enrollmentStatus: 'ACTIVE'
    });

  }

  addEnrollment() {

    if (!this.student) {
      return;
    }

    if (this.enrollmentForm.invalid) {
      this.enrollmentForm.markAllAsTouched();
      return;
    }

    this.enrollmentSaving = true;
    this.enrollmentErrorMessage = '';

    const formValue = this.enrollmentForm.getRawValue();

    const request: EnrollmentCreateRequest = {
      studentId: this.student.id,
      courseId: Number(formValue.courseId),
      semesterId: Number(formValue.semesterId),
      enrollmentDate: formValue.enrollmentDate,
      enrollmentStatus: formValue.enrollmentStatus,
      grade: formValue.grade
    };

    this.enrollmentService.createEnrollment(request).subscribe({

      next: (createdEnrollment) => {

        console.log('Enrollment created:', createdEnrollment);

        this.student!.enrollments.push(createdEnrollment);

        this.enrollmentForm.reset({
          enrollmentStatus: 'ACTIVE'
        });

        this.enrollmentSaving = false;
      },

      error: (error) => {

        console.error(error);

        this.enrollmentErrorMessage =
          error?.error?.message ?? 'Failed to create enrollment';

        this.enrollmentSaving = false;
      }

    });

  }

  removeEnrollment(id: number) {}

}
