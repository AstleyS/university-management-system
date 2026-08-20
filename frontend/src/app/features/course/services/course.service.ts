import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Course, CourseCreateRequest} from '../models/course';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private apiURL = 'http://localhost:8080/api/courses';

  constructor(private http: HttpClient) { }

  getCourses() {
    return this.http.get<Course[]>(this.apiURL)
  }

  getCourseById(id: number) {
    return this.http.get<Course>(`${this.apiURL}/${id}`);
  }

  createCourse(request: CourseCreateRequest) {
    console.log('request: ', request)
    return this.http.post<Course>(
      this.apiURL,
      request
    )
  }

  deleteCourse(id: number) {
    return this.http.delete(`${this.apiURL}/${id}`)
  }


}
