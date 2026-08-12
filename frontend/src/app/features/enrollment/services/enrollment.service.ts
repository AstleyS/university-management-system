import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Enrollment, EnrollmentCreateRequest} from '../models/enrollment';

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService {

  private apiURL = 'http://localhost:8080/api/enrollments'

  constructor(private http: HttpClient) { }

  getEnrollments() {
    return this.http.get<Enrollment[]>(
      this.apiURL
    )
  }

  createEnrollment(request: EnrollmentCreateRequest) {
    return this.http.post<Enrollment>(
      this.apiURL,
      request
    )
  }

}
