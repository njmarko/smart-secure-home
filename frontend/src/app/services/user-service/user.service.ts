import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserResponse } from 'src/app/model/users/UserResponse';
import { environment } from 'src/environments/environment';
import { PaginatedResponse } from 'src/app/shared/types/PaginatedResponse';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  getUsersBellowMe(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(
      `${environment.adminAppUrl}users/bellow-my-role`
    );
  }

  getUsersBellowMePaginated(page: number, size: number) {
    return this.http.get<PaginatedResponse<UserResponse>>(
      `${environment.adminAppUrl}users/bellow-my-role-paginated`,
      {
        params: {
          page: page,
          size: size,
          sort: 'id,asc',
        },
      }
    );
  }
}
