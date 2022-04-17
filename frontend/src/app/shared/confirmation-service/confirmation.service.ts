import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { ConfirmationOptions } from '../types/ConfirmationOptions';

@Injectable({
  providedIn: 'root',
})
export class ConfirmationService {
  constructor(private dialogService: MatDialog) {}

  confirm(options: ConfirmationOptions): Observable<any> {
    const dialogRef = this.dialogService.open(ConfirmationDialogComponent, {
      data: {
        title: options.title,
        message: options.message,
        yes: options.yes ?? 'Yes',
        no: options.no ?? 'No',
      },
    });
    return dialogRef.afterClosed();
  }
}
