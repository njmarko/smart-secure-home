import { Component, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BlacklistedJwtService } from 'src/app/services/blacklisted-jwt-service/blacklisted-jwt.service';
import { ErrorService } from 'src/app/shared/error-service/error.service';

@Component({
  selector: 'app-blacklist-jwt',
  templateUrl: './blacklist-jwt.component.html',
  styleUrls: ['./blacklist-jwt.component.scss']
})
export class BlacklistJwtComponent implements OnInit {

  form: FormGroup;

  constructor(
    private blacklistedJwtService: BlacklistedJwtService,
    private errorService: ErrorService,
    private snackbar: MatSnackBar,
    private formBuilder: FormBuilder
  ) {
    this.form = this.formBuilder.group({
      token: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (!this.form.valid) {
      return;
    }
    this.blacklistedJwtService.blacklist(this.form.value.token).subscribe({
      next: _ => {
        this.form.reset();
        this.snackbar.open("JWT has been blacklisted successfully.", "Confirm", { duration: 3000 });
      },
      error: err => this.errorService.handle(err)
    });
  }

}
