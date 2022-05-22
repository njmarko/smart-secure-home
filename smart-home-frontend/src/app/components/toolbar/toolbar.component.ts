import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CurrentUserService } from 'src/app/services/currrent-user-service/current-user.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {
  isLoggedIn: boolean = false;

  constructor(private currentUserService: CurrentUserService, private router: Router) { }

  ngOnInit(): void {
    this.isLoggedIn = this.currentUserService.getCurrentUser() != null;
  }

  logout(): void {
    this.currentUserService.removeCurrentUser();
    this.router.navigate(["/login"]);
  }

}
