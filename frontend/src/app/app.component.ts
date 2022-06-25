import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CurrentUserService } from './services/currrent-user-service/current-user.service';
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
    private snackBar: MatSnackBar
  ) {

  }

  ngOnInit(): void {
    this.currentUserService.onCurrentUserChanged().subscribe(_ => this.onUserChanged());
  }

  onUserChanged(): void {
    if (this.currentUserService.hasAuthority("READ_ALARMS")) {
      this.socketService.initializeWebSocketConnection();
      this.socketService.onAlarmOccured().subscribe(alarm => {
        this.snackBar.open(alarm.message, "Confirm", { duration: 3000 });
      })
    } else {
      this.socketService.disconnect();
    }
  }
}
