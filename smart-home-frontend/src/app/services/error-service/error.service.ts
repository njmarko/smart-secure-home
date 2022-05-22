import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class ErrorService {
  constructor(private snackBar: MatSnackBar) {}

  handle(error: HttpErrorResponse): void {
    this.show(error.error.message);
  }

  show(error: string): void {
    this.snackBar.open(error, 'Dissmiss', {
      duration: 5000,
      verticalPosition: 'top',
    });
  }
}
