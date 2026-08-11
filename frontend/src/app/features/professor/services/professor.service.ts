import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Professor} from '../models/professor';

@Injectable({
  providedIn: 'root'
})
export class ProfessorService {

  private apiURL = 'http://localhost:8080/api/professors';

  constructor(private http: HttpClient) { }

  getProfessors() {
    return this.http.get<Professor[]>(this.apiURL);
  }

  getProfessorById(id: number) {
    return this.http.get<Professor>(`${this.apiURL}/${id}`);
  }

}
