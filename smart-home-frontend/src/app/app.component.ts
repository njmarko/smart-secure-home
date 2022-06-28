import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CurrentUserService } from './services/currrent-user-service/current-user.service';
import { WebSocketService } from './services/web-socket-service/web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'smart-home-frontend';

  constructor(
    private socketService: WebSocketService,
    private currentUserService: CurrentUserService,
    private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.initSockets();
    this.currentUserService.onCurrentUserChanged().subscribe(_ => this.initSockets());
  }

  initSockets(): void {
    if (this.currentUserService.hasUser()) {
      this.socketService.initializeWebSocketConnection();
      this.socketService.onAlarmOccured().subscribe(alarm => {
        this.snackBar.open(alarm.message, "Confirm", { duration: 3000 });
      })
    } else {
      this.socketService.disconnect();
    }
  }
}
