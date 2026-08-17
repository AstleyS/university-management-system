import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Gender} from '../../../../core/enums/Gender';
import {ProfessorService} from '../../services/professor.service';
import {Router} from '@angular/router';
import {Course} from '../../../course/models/course';
import {CourseService} from '../../../course/services/course.service';
import {CourseInstructorService} from '../../../courseInstructor/services/course-instructor.service';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'app-professor-creat-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './professor-create-form.component.html',
  styleUrl: './professor-create-form.component.scss'
})
export class ProfessorCreateFormComponent implements OnInit {

  professorForm: FormGroup;
  courses: Course[] = [];

  loading = false;
  errorMessage = '';

  genders = Object.values(Gender)

  constructor(
    private fb: FormBuilder,
    private professorService: ProfessorService,
    private courseService: CourseService,
    private courseInstructorService: CourseInstructorService,
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
      ],

      courseIds: [[]]

    });
  }

  ngOnInit() {
    this.courseService.getCourses().subscribe({
      next: (data) => {
        this.courses = data;
      },
      error: (error) => console.error('Failed to load courses', error)
    });
  }

  toggleCourse(courseId: number, event: Event) {

    const checked = (event.target as HTMLInputElement).checked;

    const currentCourseIds: number[] =
      this.professorForm.get('courseIds')?.value ?? [];

    if (checked) {
      this.professorForm.patchValue({
        courseIds: [...currentCourseIds, courseId]
      });
    } else {
      this.professorForm.patchValue({
        courseIds: currentCourseIds.filter(id => id !== courseId)
      });
    }
  }

  onSubmit() {

    if (this.professorForm.invalid) {
      this.professorForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const formValue = this.professorForm.value;

    const courseIds: number[] = formValue.courseIds ?? [];

    // Remove frontend-only field before sending to Professor API
    const professorData = {
      ...formValue
    };

    delete professorData.courseIds;

    this.professorService.createProfessor(professorData).subscribe({

      next: (professor) => {

        // No courses selected
        if (courseIds.length === 0) {
          this.loading = false;
          this.router.navigate(['/professors']);
          return;
        }

        // Create an Observable for every selected course
        const courseInstructorRequests = courseIds.map(courseId =>
          this.courseInstructorService.createCourseInstructor({
            professorId: professor.id,
            courseId: Number(courseId)
          })
        );

        // Wait for every association to be created
        forkJoin(courseInstructorRequests).subscribe({

          next: () => {
            this.loading = false;
            this.router.navigate(['/professors']);
          },

          error: (error) => {
            this.loading = false;
            console.error('Failed to assign professor to courses', error);
            this.errorMessage =
              'Professor was created, but course assignments failed.';
          }

        });

      },

      error: (error) => {
        this.loading = false;
        console.error('Create professor error:', error);
        this.errorMessage =
          error?.error?.message ?? 'Failed to create professor';
      }

    });
  }
}
