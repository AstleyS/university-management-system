import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Course} from '../models/course';

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


}
