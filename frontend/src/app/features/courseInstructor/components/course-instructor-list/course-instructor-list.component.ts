import { Component, OnInit } from '@angular/core';
import { CourseInstructorService } from '../../services/course-instructor.service';
import { CourseInstructor } from '../../models/course-instructor';

@Component({
  selector: 'app-course-instructor-list',
  standalone: true,
  templateUrl: './course-instructor-list.component.html',
  styleUrl: './course-instructor-list.component.scss'
})
export class CourseInstructorListComponent implements OnInit {

  courseInstructors: CourseInstructor[] = [];

  constructor(
    private courseInstructorService: CourseInstructorService
  ) {}

  ngOnInit() {

    this.courseInstructorService.getCourseInstructors().subscribe({

      next: (data) => {
        this.courseInstructors = data;
        console.log(data);
      },

      error: err => console.error(err)

    });

  }

  deleteCourseInstructor(id: number) {

    this.courseInstructorService.deleteCourseInstructor(id).subscribe({

      next: () => {

        console.log('Enrollment deleted');
        this.courseInstructors = this.courseInstructors.filter(
          courseInstructors => courseInstructors.id !== id
        );
      },

      error: (error) => {
        console.error(error);
      }

    });
  }

}
