import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeviceMessage } from 'src/app/model/DeviceMessage';
import { SearchMessagesRequest } from 'src/app/model/SearchMessagesRequest';
import { PaginatedResponse } from 'src/app/types/PaginatedResponse';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DeviceMessageService {

  constructor(private http: HttpClient) { }

  read(request: SearchMessagesRequest, page: number, size: number): Observable<PaginatedResponse<DeviceMessage>> {
    return this.http.get<PaginatedResponse<DeviceMessage>>(`${environment.smartHomeApi}/device-messages`, {
      params: {
        page: page,
        size: size,
        sort: 'timestamp,desc',
        ...request
      }
    });
  }
}
