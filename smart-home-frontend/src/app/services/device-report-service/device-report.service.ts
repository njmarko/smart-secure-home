import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DeviceReport } from 'src/app/model/DeviceReport';
import { SearchDeviceReportRequest } from 'src/app/model/SearchDeviceReportRequest';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DeviceReportService {
  read(request: SearchDeviceReportRequest, pageIdx: number, pageSize: number) {
    return this.http.get<DeviceReport[]>(`${environment.smartHomeApi}/device-report`, {
      params: {
        page: pageIdx,
        size: pageSize,
        sort: 'name,desc',
        ...request
      }
    });
  }

  constructor(private http: HttpClient) { }
}
