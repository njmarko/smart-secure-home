import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { CurrentUserService } from 'src/app/services/currrent-user-service/current-user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  @Input() error!: string | null;
  form: FormGroup;

  invalidUser: boolean = false;

  constructor(
    private authService: AuthService,
    private currentUserService: CurrentUserService,
    private formBuilder: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute
  ) {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (!this.form.valid) {
      return;
    }
    this.invalidUser = false;
    this.authService.login(this.form.value).subscribe({
      next: response => {
        this.currentUserService.setCurrentUser(response);
        const destination: string | null = this.route.snapshot.queryParamMap.get('to');
        if (destination) {
          this.router.navigate([destination]);
        } else {
          this.router.navigate([""]);
        }
      },
      error: _ => {
        this.invalidUser = true;
      }
    });
  }

}