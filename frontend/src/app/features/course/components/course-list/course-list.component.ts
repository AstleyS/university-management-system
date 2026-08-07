import {Component, OnInit} from '@angular/core';
import {CourseService} from '../../services/course.service';
import {Course} from '../../models/course';

@Component({
  selector: 'app-course-list',
  standalone: true,
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.scss'
})
export class CourseListComponent implements OnInit {

  constructor(private courseService: CourseService) {}

  courses?: Course[]

  ngOnInit() {

    this.courseService.getCourses().subscribe({

      next: (data) => {
        this.courses = data;
      },

      error: err => console.error(err)

    });
  }

}
