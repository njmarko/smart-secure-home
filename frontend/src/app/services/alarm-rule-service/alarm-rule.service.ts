import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AlarmRule } from 'src/app/model/log/AlarmRule';
import { CreateAlarmRuleRequest } from 'src/app/model/log/CreateAlarmRuleRequest';
import { PaginatedResponse } from 'src/app/shared/types/PaginatedResponse';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlarmRuleService {

  constructor(private http: HttpClient) { }

  create(request: CreateAlarmRuleRequest): Observable<AlarmRule> {
    return this.http.post<AlarmRule>(`${environment.adminAppUrl}alarm-rules`, request);
  }

  read(page: number, size: number): Observable<PaginatedResponse<AlarmRule>> {
    return this.http.get<PaginatedResponse<AlarmRule>>(`${environment.adminAppUrl}alarm-rules`, {
      params: {
        page: page,
        size: size
      }
    });
  }

  deactivate(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.adminAppUrl}alarm-rules/${id}`);
  }
}
