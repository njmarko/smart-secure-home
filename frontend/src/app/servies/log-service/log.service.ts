import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Log } from 'src/app/model/log/Log';
import { Pageable } from 'src/app/shared/types/Pageable';
import { PaginatedResponse } from 'src/app/shared/types/PaginatedResponse';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(private http: HttpClient) { }

  read(page: number, size: number): Observable<PaginatedResponse<Log>> {
    return this.http.get<PaginatedResponse<Log>>(`${environment.adminAppUrl}logs`, {
      params: {
        page: page,
          size: size,
          sort: 'timestamp,desc',
      }
    });
  }
}
