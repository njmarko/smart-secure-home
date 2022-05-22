import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserResponse } from 'src/app/model/users/UserResponse';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUsersBellowMe(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(`${environment.adminAppUrl}users/bellow-my-role`);
  }
}
