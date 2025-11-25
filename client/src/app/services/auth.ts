import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/admin/login'; // change to your API

  constructor(private http: HttpClient) {}

  login(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }
}
