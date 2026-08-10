import {Component, OnInit} from '@angular/core';
import {CourseService} from '../../services/course.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {Department} from '../../../department/models/department';
import {DepartmentService} from '../../../department/services/department.service';

@Component({
  selector: 'app-course-form',
  standalone: true,
  imports:
  [ReactiveFormsModule],
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss'
})
export class CourseFormComponent implements OnInit {

  courseForm: FormGroup;
  departments?: Department[];

  loading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private courseService: CourseService,
    private departmentService: DepartmentService,
    private router: Router
    ) {

    this.courseForm = this.fb.group({

      departmentId: [
        '',
        [Validators.required]
      ],

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
          Validators.minLength(2),
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
          Validators.required,
          Validators.min(0)
        ]
      ]

    });
  }

  ngOnInit() {

    this.departmentService.getDepartments().subscribe({

      next: (data) => {
        console.log(data)
        this.departments = data
      },

      error: err => console.error(err)

    });

  }


  onSubmit() {

    if (this.courseForm.invalid) {
      this.courseForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.courseService.createCourse(this.courseForm.value).subscribe({

      next: (course) => {
        console.log('Course created:', course);

        this.router.navigate(['/courses']);
      },

      error: (error) => {
        console.error('Create course error:', error);

        this.loading = false;
        this.errorMessage =
          error?.error?.message ?? 'Failed to create course';
      }

    });
  }
}
