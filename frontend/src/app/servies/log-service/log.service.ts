import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Alarm } from 'src/app/model/log/Alarm';
import { Log } from 'src/app/model/log/Log';
import { SearchLogsRequest } from 'src/app/model/log/SearchLogsRequest';
import { PaginatedResponse } from 'src/app/shared/types/PaginatedResponse';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(private http: HttpClient) { }

  readLogs(request: SearchLogsRequest, page: number, size: number): Observable<PaginatedResponse<Log>> {
    return this.http.get<PaginatedResponse<Log>>(`${environment.adminAppUrl}logs`, {
      params: {
        page: page,
        size: size,
        sort: 'timestamp,desc',
        ...request
      }
    });
  }

  readAlarms(page: number, size: number): Observable<PaginatedResponse<Alarm>> {
    return this.http.get<PaginatedResponse<Alarm>>(`${environment.adminAppUrl}logs/alarms`, {
      params: {
        page: page,
        size: size,
        sort: 'timestamp,desc',
      }
    });
  }
}
