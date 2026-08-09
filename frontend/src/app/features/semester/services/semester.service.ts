import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Semester} from '../models/semester';

@Injectable({
  providedIn: 'root'
})
export class SemesterService {

  private apiURL = 'http://localhost:8080/api/semesters'

  constructor(private http: HttpClient) { }

  getSemesters() {
    return this.http.get<Semester[]>(
      this.apiURL
    )
  }


}
