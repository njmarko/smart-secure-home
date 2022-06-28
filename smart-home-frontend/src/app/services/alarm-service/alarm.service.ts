import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Alarm } from 'src/app/model/Alarm';
import { PaginatedResponse } from 'src/app/types/PaginatedResponse';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {
  constructor(private http: HttpClient) { }
}
