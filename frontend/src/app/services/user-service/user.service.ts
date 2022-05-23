import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserResponse } from 'src/app/model/users/UserResponse';
import { environment } from 'src/environments/environment';
import { PaginatedResponse } from 'src/app/shared/types/PaginatedResponse';
import { UserDetails } from 'src/app/model/users/UserDetails';
import { UpdateUserRealEstates } from 'src/app/model/users/UpdateUserRealEstates';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) { }

  getUserDetails(id: number): Observable<UserDetails> {
    return this.http.get<UserDetails>(`${environment.adminAppUrl}users/${id}/details`);
  }

  updateRealEstates(id: number, request: UpdateUserRealEstates): Observable<void> {
    return this.http.put<void>(`${environment.adminAppUrl}users/${id}/real-estates`, request);
  }

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

  modifyRole(username: string, roleName: string) {
    return this.http.post(`${environment.adminAppUrl}users/${username}/modifyRole/${roleName}`, {})
  }

  deleteUser(username: string) {
    return this.http.delete(`${environment.adminAppUrl}users/${username}`)
  }
}
