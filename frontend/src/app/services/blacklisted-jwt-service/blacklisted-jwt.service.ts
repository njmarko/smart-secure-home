import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BlacklistedJwtService {

  constructor(private http: HttpClient) { }

  blacklist(token: string): Observable<void> {
    return this.http.put<void>(`${environment.adminAppUrl}auth/blacklist/${token}`, {});
  }
}
