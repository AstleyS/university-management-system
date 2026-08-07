import { Injectable, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AcademicStats} from '../models/dashboard';

@Injectable({
  providedIn: 'root'
})
export class DashboardService  {

  private apiURL = 'http://localhost:8080/api/academics';

  constructor(
    private http: HttpClient
  ) { }

  getAcademicStats() {

    return this.http.get<AcademicStats>(
      `${this.apiURL}/counts`
    )

  }
}
