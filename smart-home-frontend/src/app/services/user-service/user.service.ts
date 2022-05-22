import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RealEstate } from 'src/app/model/RealEstate';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getMyRealEstates(): Observable<RealEstate[]> {
    return this.http.get<RealEstate[]>(`${environment.smartHomeApi}/users/my-real-estates`);
  }
}
