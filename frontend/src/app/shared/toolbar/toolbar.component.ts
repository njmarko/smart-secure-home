import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { CurrentUserService } from 'src/app/services/currrent-user-service/current-user.service';
import { WebSocketService } from 'src/app/services/web-socket-service/web-socket.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  @Input()
  loggedIn: boolean = false;
  @Input()
  isAdmin: boolean = false;
  @Input()
  isManager: boolean = false;
  @Input()
  name: string | undefined = '';
  @Input()
  surname: string | undefined = '';

  constructor(
    private authService: AuthService,
    public currentUserService: CurrentUserService,
    private router: Router,
    private socketService: WebSocketService
  ) { }

  ngOnInit(): void {
    this.currentUserService.onCurrentUserChanged().subscribe(_ => this.onUserChanged());
  }

  onUserChanged(): void {
    if (this.currentUserService.hasAuthority("READ_ALARMS")) {
      this.socketService.initializeWebSocketConnection();
      this.socketService.onAlarmOccured().subscribe(alarm => {
        console.log(alarm);
      })
    } else {
      this.socketService.disconnect();
    }
  }

  logout(): void {
    this.authService.logout().subscribe(_ => {
      this.currentUserService.removeCurrentUser();
      this.router.navigate(['/login']);
    });
  }

}
