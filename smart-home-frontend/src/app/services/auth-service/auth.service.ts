import { Injectable } from "@angular/core";
import { HttpHeaders, HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthRequest } from "../../model/AuthRequest";
import { AuthResponse } from "../../model/AuthResponse";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(auth: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.smartHomeApi}/users/authenticate`, auth);
  }

  logout(): Observable<void> {
    return this.http.put<void>(`${environment.smartHomeApi}/users/logout`, {});
  }
}