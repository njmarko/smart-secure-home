import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateRealEstateRequest } from 'src/app/model/real-esatate/CreateRealEstateRequest';
import RealEstate from 'src/app/model/users/RealEstate';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RealEstateService {

  constructor(private http: HttpClient) { }

  create(request: CreateRealEstateRequest): Observable<RealEstate> {
    return this.http.post<RealEstate>(`${environment.adminAppUrl}real-estates`, request);
  }
}
