import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Student, StudentRequest} from '../models/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private apiURL = 'http://localhost:8080/api/students';

  constructor(private http: HttpClient) { }

  getStudents() {
    return this.http.get<Student[]>(this.apiURL);

  }

  getStudentById(id: number) {
    return this.http.get<Student>(`${this.apiURL}/${id}`);
  }

  createStudent(request: StudentRequest) {
    console.log('request: ', request)
    return this.http.post<Student>(
      this.apiURL,
      request
    )
  }

  deleteStudent(id: number) {

  }

}
