import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Course } from '../../models/course';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.scss'
})
export class CourseListComponent implements OnInit {

  courses: Course[] = [];

  searchTerm = '';

  loading = true;
  errorMessage = '';

  constructor(
    private courseService: CourseService
  ) {}

  ngOnInit() {

    this.loadCourses();

  }

  loadCourses() {

    this.courseService.getCourses().subscribe({

      next: (courses) => {

        this.courses = courses;

        this.loading = false;

      },

      error: (error) => {

        console.error(error);

        this.errorMessage =
          'Failed to load courses';

        this.loading = false;

      }

    });

  }

  get filteredCourses(): Course[] {

    const search =
      this.searchTerm
        .trim()
        .toLowerCase();

    if (!search) {
      return this.courses;
    }

    return this.courses.filter(course =>
      course.code.toLowerCase().includes(search) ||
      course.name.toLowerCase().includes(search)
    );

  }

  deleteCourse(id: number) {

    const course =
      this.courses.find(
        course => course.id === id
      );

    if (!course) {
      return;
    }

    const confirmed =
      confirm(
        `Delete course "${course.name}" (${course.code})?`
      );

    if (!confirmed) {
      return;
    }

    this.courseService.deleteCourse(id).subscribe({

      next: () => {

        this.courses =
          this.courses.filter(
            course => course.id !== id
          );

      },

      error: (error) => {

        console.error(error);

        this.errorMessage =
          error?.error?.message ??
          'Failed to delete course';

      }

    });

  }

}
