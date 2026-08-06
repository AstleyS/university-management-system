import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private apiURL = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getHello() {
    return this.http.get(this.apiURL, {
      responseType: 'text'
    });
  }

}
