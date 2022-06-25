import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EventBusService {
  public onAlarmOccurred: EventEmitter<void> = new EventEmitter<void>();

  constructor() { }
}
