import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CurrentUserService } from './services/currrent-user-service/current-user.service';
import { EventBusService } from './services/event-bus/event-bus.service';
import { WebSocketService } from './services/web-socket-service/web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'frontend';

  constructor(
    private currentUserService: CurrentUserService,
    private socketService: WebSocketService,
    private eventBus: EventBusService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.currentUserService.onCurrentUserChanged().subscribe(_ => this.onUserChanged());
    this.onUserChanged();
  }

  onUserChanged(): void {
    if (this.currentUserService.hasAuthority("READ_ALARMS")) {
      this.socketService.initializeWebSocketConnection();
      this.socketService.onAlarmOccured().subscribe(alarm => {
        this.eventBus.onAlarmOccurred.emit();
        const alarmMessage = this.snackBar.open(alarm.message, "View alarms", { duration: 3000 });
        alarmMessage.onAction().subscribe(_ => {
          this.router.navigate(['/alarms']);
        });
      })
    } else {
      this.socketService.disconnect();
    }
  }
}
