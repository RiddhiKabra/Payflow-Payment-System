import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  login(data:any):Observable<any>{
    return this.http.post(`${this.baseUrl}/users/login`,data);
  }

  getTransactions():Observable<any>{
    return this.http.get(`${this.baseUrl}/transactions/my`);
  }

  createTransaction(data:any):Observable<any>{
    return this.http.post(`${this.baseUrl}/transactions`,data);
  }

}
