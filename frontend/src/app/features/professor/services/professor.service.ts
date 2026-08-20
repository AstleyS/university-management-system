import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Professor, ProfessorCreateRequest} from '../models/professor';

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

  createProfessor(request: ProfessorCreateRequest) {
    console.log('request: ', request)
    return this.http.post<Professor>(
      this.apiURL,
      request
    )
  }

  deleteProfessor(id: number) {
    return this.http.delete(`${this.apiURL}/${id}`)
  }
}
