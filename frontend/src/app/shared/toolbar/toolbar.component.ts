import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { CurrentUserService } from 'src/app/services/currrent-user-service/current-user.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private currentUserService: CurrentUserService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.authService.logout().subscribe(_ => {
      this.currentUserService.removeCurrentUser();
      this.router.navigate(['/login']);
    });
  }

}
