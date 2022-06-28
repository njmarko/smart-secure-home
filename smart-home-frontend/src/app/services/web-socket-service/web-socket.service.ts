import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Alarm } from 'src/app/model/Alarm';
import { CurrentUserService } from '../currrent-user-service/current-user.service';
import { AuthResponse } from 'src/app/model/AuthResponse';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  url: string = 'https://localhost:8081/socket';
  private stompClient!: any;
  public isLoaded: boolean = false;
  alarmNotifier: EventEmitter<Alarm> = new EventEmitter<Alarm>();

  constructor(private http: HttpClient, private currentUserService: CurrentUserService) { }

  initializeWebSocketConnection() {
    const user = this.currentUserService.getCurrentUser();
    if (user == null) {
      return;
    }
    if (!this.isLoaded) {
      let ws = new SockJS(this.url);
      this.stompClient = Stomp.over(ws);
      this.stompClient.connect({}, () => {
        this.isLoaded = true;
        this.openGlobalSocket(user);
      });
    }
  }

  openGlobalSocket(user: AuthResponse) {
    if (this.isLoaded) {
      try {
        user.realEstates.forEach(realEstateId => {
          this.stompClient.subscribe(
            `/live-alarms/${realEstateId}`,
            (alarm: { body: string }) => {
              this.handleAlarm(alarm);
            }
          );
        })
      } catch {
        console.log('Connection has not been established yet... connecting...');
      }
    }
  }

  onAlarmOccured(): EventEmitter<Alarm> {
    return this.alarmNotifier;
  }

  handleAlarm(message: { body: string }): void {
    if (message.body) {
      const response = JSON.parse(message.body);
      this.alarmNotifier.emit(response);
    }
  }

  disconnect(): void {
    if (this.isLoaded) {
      this.stompClient.disconnect();
      this.isLoaded = false;
    }
  }
}
