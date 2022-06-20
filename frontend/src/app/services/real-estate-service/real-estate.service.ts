import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateRealEstateRequest } from 'src/app/model/real-esatate/CreateRealEstateRequest';
import { Device } from 'src/app/model/real-esatate/Device';
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

  read(): Observable<RealEstate[]> {
    return this.http.get<RealEstate[]>(`${environment.adminAppUrl}real-estates`);
  }

  readDevices(id: number): Observable<Device[]> {
    return this.http.get<Device[]>(`${environment.adminAppUrl}real-estates/${id}/devices`);
  }

  configureDevice(id: number, device: Device): Observable<void> {
    return this.http.post<void>(`${environment.adminAppUrl}real-estates/${id}/devices`, device);
  }
}
