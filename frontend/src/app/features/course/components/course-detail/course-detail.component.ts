import { Component, OnInit } from '@angular/core';
import {
  ActivatedRoute,
  RouterLink
} from '@angular/router';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { Course } from '../../models/course';
import { CourseService } from '../../services/course.service';

import { Department } from '../../../department/models/department';
import { DepartmentService } from '../../../department/services/department.service';

import { Professor } from '../../../professor/models/professor';
import { ProfessorService } from '../../../professor/services/professor.service';

import {
  CourseInstructorService
} from '../../../courseInstructor/services/course-instructor.service';

import {
  CourseInstructorCreateRequest
} from '../../../courseInstructor/models/course-instructor';

@Component({
  selector: 'app-course-detail',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './course-detail.component.html',
  styleUrl: './course-detail.component.scss'
})
export class CourseDetailComponent implements OnInit {

  course?: Course;

  departments: Department[] = [];
  professors: Professor[] = [];

  courseForm: FormGroup;
  professorForm: FormGroup;

  loading = true;
  errorMessage = '';

  editingCourse = false;
  managingProfessors = false;

  savingCourse = false;
  savingProfessors = false;

  courseErrorMessage = '';
  professorErrorMessage = '';

  originalProfessorIds: number[] = [];

  constructor(
    private route: ActivatedRoute,
    private courseService: CourseService,
    private departmentService: DepartmentService,
    private professorService: ProfessorService,
    private courseInstructorService: CourseInstructorService,
    private fb: FormBuilder
  ) {

    this.courseForm = this.fb.group({

      code: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(10)
        ]
      ],

      name: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(30)
        ]
      ],

      description: [
        '',
        [
          Validators.maxLength(250)
        ]
      ],

      credits: [
        '',
        [
          Validators.required
        ]
      ],

      departmentId: [
        '',
        [
          Validators.required
        ]
      ]

    });


    this.professorForm = this.fb.group({

      professorIds: [[]]

    });

  }


  ngOnInit() {

    const id =
      Number(
        this.route.snapshot.paramMap.get('id')
      );

    if (!id) {

      this.errorMessage =
        'Invalid course ID';

      this.loading = false;

      return;

    }


    this.courseService
      .getCourseById(id)
      .subscribe({

        next: (data) => {

          this.course = data;

          this.initializeCourseForm();

          this.loading = false;

        },

        error: (error) => {

          console.error(error);

          this.errorMessage =
            'Failed to load course';

          this.loading = false;

        }

      });

  }


  private initializeCourseForm() {

    if (!this.course) {
      return;
    }

    this.courseForm.patchValue({

      code: this.course.code,

      name: this.course.name,

      description: this.course.description,

      credits: this.course.credits,

      departmentId:
        this.course.department?.id ?? null

    });

  }


  startEditingCourse() {

    this.initializeCourseForm();

    this.courseErrorMessage = '';

    this.editingCourse = true;

    this.loadDepartments();

  }


  cancelEditingCourse() {

    this.initializeCourseForm();

    this.courseErrorMessage = '';

    this.editingCourse = false;

  }


  loadDepartments() {

    this.departmentService
      .getDepartments()
      .subscribe({

        next: (departments) => {

          this.departments =
            departments;

        },

        error: (error) => {

          console.error(error);

          this.courseErrorMessage =
            'Failed to load departments';

        }

      });

  }

  /*
  updateCourse() {

    if (!this.course) {
      return;
    }

    if (this.courseForm.invalid) {

      this.courseForm.markAllAsTouched();

      return;

    }

    this.savingCourse = true;

    this.courseErrorMessage = '';

    const value =
      this.courseForm.getRawValue();


    const request = {

      code: value.code,

      name: value.name,

      description: value.description,

      credits: Number(value.credits),

      departmentId:
        Number(value.departmentId)

    };


    this.courseService.updateCourse(
        this.course.id,
        request
      )
      .subscribe({

        next: (updatedCourse) => {

          this.course =
            updatedCourse;

          this.savingCourse = false;

          this.editingCourse = false;

        },

        error: (error) => {

          console.error(error);

          this.courseErrorMessage =
            error?.error?.message ??
            'Failed to update course';

          this.savingCourse = false;

        }

      });

  }
  */

  startManagingProfessors() {

    this.managingProfessors = true;

    this.professorErrorMessage = '';

    this.originalProfessorIds =
      this.getAssignedProfessorIds();

    this.professorForm.patchValue({

      professorIds: [
        ...this.originalProfessorIds
      ]

    });

    this.loadProfessors();

  }


  cancelManagingProfessors() {

    this.professorForm.patchValue({

      professorIds: [
        ...this.originalProfessorIds
      ]

    });

    this.managingProfessors = false;

    this.professorErrorMessage = '';

  }


  loadProfessors() {

    this.professorService
      .getProfessors()
      .subscribe({

        next: (professors) => {

          this.professors =
            professors;

        },

        error: (error) => {

          console.error(error);

          this.professorErrorMessage =
            'Failed to load professors';

        }

      });

  }


  private getAssignedProfessorIds(): number[] {

    return this.course?.courseInstructors
      ?.filter(
        ci => ci.professor != null
      )
      .map(
        ci => ci.professor!.id
      ) ?? [];

  }


  isProfessorSelected(
    professorId: number
  ): boolean {

    const ids: number[] =
      this.professorForm
        .get('professorIds')
        ?.value ?? [];

    return ids.includes(professorId);

  }


  toggleProfessor(
    professorId: number,
    event: Event
  ) {

    const checkbox =
      event.target as HTMLInputElement;

    const ids: number[] =
      this.professorForm
        .get('professorIds')
        ?.value ?? [];


    if (checkbox.checked) {

      if (!ids.includes(professorId)) {

        this.professorForm.patchValue({

          professorIds: [
            ...ids,
            professorId
          ]

        });

      }

    } else {

      this.professorForm.patchValue({

        professorIds:
          ids.filter(
            id => id !== professorId
          )

      });

    }

  }


  hasProfessorChanges(): boolean {

    const currentIds: number[] =
      this.professorForm
        .get('professorIds')
        ?.value ?? [];


    if (
      currentIds.length !==
      this.originalProfessorIds.length
    ) {

      return true;

    }


    return currentIds.some(
      id =>
        !this.originalProfessorIds
          .includes(id)
    );

  }


  saveProfessorChanges() {

    if (!this.course) {
      return;
    }


    const currentIds: number[] =
      this.professorForm
        .get('professorIds')
        ?.value ?? [];


    const professorsToAdd =
      currentIds.filter(
        id =>
          !this.originalProfessorIds
            .includes(id)
      );


    const professorsToRemove =
      this.originalProfessorIds.filter(
        id =>
          !currentIds.includes(id)
      );


    if (
      professorsToAdd.length === 0 &&
      professorsToRemove.length === 0
    ) {

      this.managingProfessors = false;

      return;

    }


    this.savingProfessors = true;

    this.professorErrorMessage = '';

    /*
     * For the moment, we can perform additions
     * and removals individually.
     *
     * This can later be replaced with forkJoin.
     */
    let completed = 0;

    const totalChanges =
      professorsToAdd.length +
      professorsToRemove.length;


    const finishChange = () => {

      completed++;

      if (completed === totalChanges) {

        this.refreshCourse();

      }

    };


    for (
      const professorId of professorsToAdd
      ) {

      const request:
        CourseInstructorCreateRequest = {

        courseId:
        this.course.id,

        professorId:
        professorId

      };


      this.courseInstructorService
        .createCourseInstructor(request)
        .subscribe({

          next: () => {

            finishChange();

          },

          error: (error) => {

            console.error(error);

            this.professorErrorMessage =
              error?.error?.message ??
              'Failed to assign professor';

            this.savingProfessors = false;

          }

        });

    }


    for (
      const professorId of professorsToRemove
      ) {

      const courseInstructor =
        this.course.courseInstructors
          ?.find(
            ci =>
              ci.professor?.id ===
              professorId
          );


      if (!courseInstructor) {

        finishChange();

        continue;

      }


      this.courseInstructorService
        .deleteCourseInstructor(
          courseInstructor.id
        )
        .subscribe({

          next: () => {

            finishChange();

          },

          error: (error) => {

            console.error(error);

            this.professorErrorMessage =
              error?.error?.message ??
              'Failed to remove professor';

            this.savingProfessors = false;

          }

        });

    }

  }


  private refreshCourse() {

    if (!this.course) {
      return;
    }


    this.courseService
      .getCourseById(this.course.id)
      .subscribe({

        next: (updatedCourse) => {

          this.course =
            updatedCourse;

          this.originalProfessorIds =
            this.getAssignedProfessorIds();

          this.professorForm.patchValue({

            professorIds:
            this.originalProfessorIds

          });

          this.savingProfessors = false;

          this.managingProfessors = false;

        },

        error: (error) => {

          console.error(error);

          this.professorErrorMessage =
            'Changes were saved, but failed to refresh course';

          this.savingProfessors = false;

        }

      });

  }

}
