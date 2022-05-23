import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  @Input() error!: string | null;
  form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute
  ) {
    this.form = this.formBuilder.group({
      // https://stackoverflow.com/a/12019115/13066849
      username: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(
            '^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$'
          ),
        ]),
      ],
      // https://stackoverflow.com/a/21456918/13066849
      password: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(
            '^(?=.*[a-z])(?=.*[A-Z])(?=.*d)(?=.*[@$!%*?&])[A-Za-zd@$!%*?&]{8,30}$'
          ),
        ]),
      ],
      firstName: [
        '',
        Validators.compose([Validators.required, Validators.max(100)]),
      ],
      lastName: [
        '',
        Validators.compose([Validators.required, Validators.max(100)]),
      ],
      email: [
        '',
        Validators.compose([
          Validators.required,
          Validators.email,
          ,
          Validators.max(100),
        ]),
      ],
      role: [
        'ROLE_TENANT',
        Validators.compose([
          Validators.required,
          Validators.pattern('[a-zA-Z]+_[a-zA-Z]+'),
          Validators.max(100),
        ]),
      ],
    });
  }

  ngOnInit(): void {}

  onSubmit() {
    if (!this.form.valid) {
      return;
    }
    this.authService.register(this.form.value).subscribe({
      next: (response) => {
        const destination: string | null =
          this.route.snapshot.queryParamMap.get('to');
        if (destination) {
          this.router.navigate([destination]);
        } else {
          this.router.navigate(['/certificates']);
        }
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
