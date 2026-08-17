import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {CourseInstructor, CourseInstructorCreateRequest} from '../models/course-instructor';

@Injectable({
  providedIn: 'root'
})
export class CourseInstructorService {

  private apiURL = 'http://localhost:8080/api/course-instructors';

  constructor(private http: HttpClient) {}

  getCourseInstructors() {
    return this.http.get<CourseInstructor[]>(
      this.apiURL
    );
  }

  createCourseInstructor(request: CourseInstructorCreateRequest) {
    console.log('request courseInstructor: ', request)
    return this.http.post<CourseInstructor>(
      this.apiURL,
      request
    );
  }

  deleteCourseInstructor(id: number) {}

}
