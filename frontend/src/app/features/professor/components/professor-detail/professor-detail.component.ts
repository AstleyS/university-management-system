import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule
} from '@angular/forms';
import { forkJoin } from 'rxjs';

import { Professor } from '../../models/professor';
import { ProfessorService } from '../../services/professor.service';

import { Course } from '../../../course/models/course';
import { CourseService } from '../../../course/services/course.service';

import { CourseInstructorService } from '../../../courseInstructor/services/course-instructor.service';
import {
  CourseInstructor,
  CourseInstructorCreateRequest
} from '../../../courseInstructor/models/course-instructor';

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

  /*
   * Courses assigned when management mode was opened.
   * Used to compare against the current selection.
   */
  originalCourseIds: number[] = [];


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

        this.professor = data;

        this.professorForm.patchValue({
          courseIds: this.getAssignedCourseIds()
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


  private getAssignedCourseIds(): number[] {

    return this.professor?.courses
      ?.filter(
        courseInstructor =>
          courseInstructor.course != null
      )
      .map(
        courseInstructor =>
          courseInstructor.course!.id
      ) ?? [];

  }


  loadCourseData() {

    this.courseService.getCourses().subscribe({

      next: (courses) => {

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
     * Store the state BEFORE editing.
     */
    this.originalCourseIds = [
      ...this.getAssignedCourseIds()
    ];

    /*
     * Initialize the form with the current
     * assignments.
     */
    this.professorForm.patchValue({
      courseIds: [
        ...this.originalCourseIds
      ]
    });

    this.loadCourseData();

  }


  stopManagingCourses() {

    /*
     * Cancel:
     * restore the original selection.
     */
    this.professorForm.patchValue({
      courseIds: [
        ...this.originalCourseIds
      ]
    });

    this.courseErrorMessage = '';

    this.managingCourses = false;

  }


  toggleCourse(
    courseId: number,
    event: Event
  ) {

    const checkbox =
      event.target as HTMLInputElement;

    const courseIds: number[] =
      this.professorForm.get('courseIds')?.value ?? [];


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
          courseIds.filter(
            id => id !== courseId
          )
      });

    }

  }


  isCourseSelected(
    courseId: number
  ): boolean {

    const courseIds: number[] =
      this.professorForm.get('courseIds')?.value ?? [];

    return courseIds.includes(courseId);

  }


  hasCourseChanges(): boolean {

    const currentCourseIds: number[] =
      this.professorForm.get('courseIds')?.value ?? [];

    /*
     * Different number of courses = change.
     */
    if (
      currentCourseIds.length !==
      this.originalCourseIds.length
    ) {

      return true;

    }

    /*
     * Check whether every current course
     * existed in the original selection.
     */
    return currentCourseIds.some(
      courseId =>
        !this.originalCourseIds.includes(courseId)
    );

  }


  saveCourseChanges() {

    if (!this.professor) {
      return;
    }

    const currentCourseIds: number[] =
      this.professorForm.get('courseIds')?.value ?? [];


    /*
     * Courses that need to be added.
     */
    const coursesToAdd =
      currentCourseIds.filter(
        courseId =>
          !this.originalCourseIds.includes(courseId)
      );


    /*
     * Courses that need to be removed.
     */
    const coursesToRemove =
      this.originalCourseIds.filter(
        courseId =>
          !currentCourseIds.includes(courseId)
      );


    /*
     * Nothing changed.
     */
    if (
      coursesToAdd.length === 0 &&
      coursesToRemove.length === 0
    ) {

      this.managingCourses = false;

      return;

    }


    this.courseSaving = true;
    this.courseErrorMessage = '';


    /*
     * Build all ADD requests.
     */
    const addRequests =
      coursesToAdd.map(courseId => {

        const request: CourseInstructorCreateRequest = {

          professorId: this.professor!.id,

          courseId: courseId

        };

        return this.courseInstructorService
          .createCourseInstructor(request);

      });


    /*
     * Build all REMOVE requests.
     *
     * We need the CourseInstructor ID,
     * not the Course ID.
     */
    const removeRequests =
      coursesToRemove
        .map(courseId => {

          const courseInstructor =
            this.professor!.courses.find(
              ci =>
                ci.course?.id === courseId
            );

          if (!courseInstructor) {
            return null;
          }

          return this.courseInstructorService
            .deleteCourseInstructor(
              courseInstructor.id
            );

        })
        .filter(
          request => request !== null
        );


    /*
     * Execute everything together.
     */
    forkJoin([

      ...addRequests,

      ...removeRequests

    ]).subscribe({

      next: (results) => {

        console.log(
          'Course changes saved:',
          results
        );


        /*
         * Reload the professor so the local
         * CourseInstructor objects are completely
         * synchronized with the backend.
         */
        this.professorService
          .getProfessorById(this.professor!.id)
          .subscribe({

            next: (updatedProfessor) => {

              this.professor =
                updatedProfessor;


              this.originalCourseIds =
                this.getAssignedCourseIds();


              this.professorForm.patchValue({

                courseIds:
                  this.getAssignedCourseIds()

              });


              this.courseSaving = false;

              this.managingCourses = false;

            },

            error: (error) => {

              console.error(error);

              this.courseErrorMessage =
                'Changes were saved, but failed to refresh professor data';

              this.courseSaving = false;

            }

          });

      },

      error: (error) => {

        console.error(error);

        this.courseErrorMessage =
          error?.error?.message ??
          'Failed to save course changes';

        this.courseSaving = false;

      }

    });
  }
}
