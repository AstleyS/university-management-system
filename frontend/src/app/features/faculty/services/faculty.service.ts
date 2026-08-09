import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Faculty} from '../models/faculty';

@Injectable({
  providedIn: 'root'
})
export class FacultyService {

  private apiURL = 'http://localhost:8080/api/faculties';

  constructor(private http: HttpClient) { }

  getFaculties() {
    return this.http.get<Faculty[]>(
      this.apiURL
    )
  }

}
