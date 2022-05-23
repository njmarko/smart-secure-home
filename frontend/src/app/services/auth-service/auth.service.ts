import { Injectable } from "@angular/core";
import { HttpHeaders, HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthRequest } from "src/app/model/auth/AuthRequest";
import { AuthResponse } from "src/app/model/auth/AuthResponse";
import { environment } from "src/environments/environment";
import { RegisterRequest } from "src/app/model/auth/RegisterRequest";
import { RegisterResponse } from "src/app/model/auth/RegisterResponse";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(auth: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.adminAppUrl}auth/login`, auth);
  }

  register(register: RegisterRequest): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${environment.adminAppUrl}auth/register`, register);
  }
}