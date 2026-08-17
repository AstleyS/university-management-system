import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule
} from '@angular/forms';

import { Professor } from '../../models/professor';
import { ProfessorService } from '../../services/professor.service';

import { Course } from '../../../course/models/course';
import { CourseService } from '../../../course/services/course.service';

import { CourseInstructorService } from '../../../courseInstructor/services/course-instructor.service';
import { CourseInstructorCreateRequest } from '../../../courseInstructor/models/course-instructor';

@Component({
  selector: 'app-professor-detail',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './professor-detail.component.html',
  styleUrl: './professor-detail.component.scss'
})
export class ProfessorDetailComponent implements OnInit {

  professor?: Professor;
  courses: Course[] = [];

  professorForm: FormGroup;

  loading = true;
  errorMessage = '';

  managingCourses = false;

  courseSaving = false;
  courseErrorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private professorService: ProfessorService,
    private courseService: CourseService,
    private courseInstructorService: CourseInstructorService,
    private fb: FormBuilder
  ) {

    this.professorForm = this.fb.group({
      courseIds: [[]]
    });
  }

  ngOnInit() {

    const id = Number(
      this.route.snapshot.paramMap.get('id')
    );

    if (!id) {

      this.errorMessage = 'Invalid professor ID';
      this.loading = false;

      return;
    }

    this.professorService.getProfessorById(id).subscribe({

      next: (data) => {

        console.log(
          `Professor with id ${id}: `,
          data
        );

        this.professor = data;

        /*
         * Initialize the form with the professor's
         * currently assigned courses.
         */
        this.professorForm.patchValue({
          courseIds: data.courses
            ?.filter(courseInstructor => courseInstructor.course != null)
            .map(courseInstructor =>
              courseInstructor.course.id
            ) ?? []
        });

        this.loading = false;

      },

      error: (error) => {

        console.error(error);

        this.errorMessage =
          'Failed to load professor';

        this.loading = false;

      }
    });
  }

  loadCourseData() {

    this.courseService.getCourses().subscribe({

      next: (courses) => {
        console.log('courses: ', courses)
        this.courses = courses;
      },

      error: (error) => {

        console.error(error);

        this.courseErrorMessage =
          'Failed to load course options';

      }
    });
  }

  startManagingCourses() {

    this.managingCourses = true;
    this.courseErrorMessage = '';

    /*
     * Reset the form to the current state of the professor.
     * This means simply opening management doesn't create
     * any changes.
     */
    this.professorForm.patchValue({
      courseIds: this.professor?.courses
        .filter(courseInstructor => courseInstructor.course != null)
        .map(courseInstructor =>
          courseInstructor.course.id
        ) ?? []
    });

    this.loadCourseData();

  }


  stopManagingCourses() {

    this.managingCourses = false;
    this.courseErrorMessage = '';

    /*
     * Discard any selections that haven't been submitted.
     */
    this.professorForm.patchValue({
      courseIds: this.professor?.courses
        .filter(courseInstructor => courseInstructor.course != null)
        .map(courseInstructor =>
          courseInstructor.course.id
        ) ?? []
    });
  }

  toggleCourse(
    courseId: number,
    event: Event
  ) {

    const checkbox = event.target as HTMLInputElement;

    const courseIds: number[] =
      this.professorForm.get('courseIds')?.value ?? [];


    /*
     * Already assigned courses cannot be removed.
     */
    if (this.isCourseAlreadyAssigned(courseId)) {

      checkbox.checked = true;
      return;
    }


    if (checkbox.checked) {

      if (!courseIds.includes(courseId)) {

        this.professorForm.patchValue({
          courseIds: [
            ...courseIds,
            courseId
          ]
        });

      }

    } else {

      this.professorForm.patchValue({
        courseIds:
          courseIds.filter(id => id !== courseId)
      });

    }

  }


  isCourseAlreadyAssigned(courseId: number): boolean {

    return this.professor?.courses?.some(
      courseInstructor =>
        courseInstructor.course?.id === courseId
    ) ?? false;

  }


  hasNewCoursesSelected(): boolean {

    if (!this.professor) {
      return false;
    }

    const selectedCourseIds: number[] =
      this.professorForm.get('courseIds')?.value ?? [];

    return selectedCourseIds.some(
      courseId =>
        !this.professor!.courses.some(
          courseInstructor =>
            courseInstructor.course?.id === courseId
        )
    );

  }


  addSelectedCourses() {

    if (!this.professor) {
      return;
    }

    const selectedCourseIds: number[] =
      this.professorForm.get('courseIds')?.value ?? [];

    /*
     * Only add courses that aren't already assigned.
     */
    const newCourseIds =
      selectedCourseIds.filter(
        courseId =>
          !this.professor!.courses.some(
            courseInstructor =>
              courseInstructor.course?.id === courseId
          )
      );

    /*
     * Don't allow an empty addition.
     */
    if (newCourseIds.length === 0) {
      return;
    }

    this.courseSaving = true;
    this.courseErrorMessage = '';

    let completed = 0;

    for (const courseId of newCourseIds) {

      const request: CourseInstructorCreateRequest = {

        professorId: this.professor.id,
        courseId: courseId

      };

      this.courseInstructorService.createCourseInstructor(request).subscribe({

        next: (createdCourseInstructor) => {

          console.log(
            'Course assigned:',
            createdCourseInstructor
          );

          this.professor!.courses.push(
            createdCourseInstructor
          );

          completed++;

          /*
           * All course assignments succeeded.
           */
          if (
            completed ===
            newCourseIds.length
          ) {

            this.professorForm.patchValue({

              courseIds:
                this.professor!.courses
                  .filter(courseInstructor => courseInstructor.course != null)
                  .map(courseInstructor =>
                    courseInstructor.course.id
                  )

            });

            this.courseSaving = false;

            // Same behaviour as clicking Done.
            this.managingCourses = false;

          }

        },

        error: (error) => {

          console.error(error);

          this.courseErrorMessage =
            error?.error?.message ??
            'Failed to assign course';

          this.courseSaving = false;

        }
      });
    }
  }
}
