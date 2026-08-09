import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Department} from '../models/department';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  private apiURL = 'http://localhost:8080/api/departments';

  constructor(private http: HttpClient) { }

  getDepartments() {
    return this.http.get<Department[]>(
      this.apiURL
    )
  }
}
