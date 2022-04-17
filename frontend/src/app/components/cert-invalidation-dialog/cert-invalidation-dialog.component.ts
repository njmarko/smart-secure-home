import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import InvalidateCertificateRequest from 'src/app/model/certificates/InvalidateCertificateRequest';

@Component({
  selector: 'app-cert-invalidation-dialog',
  templateUrl: './cert-invalidation-dialog.component.html',
  styleUrls: ['./cert-invalidation-dialog.component.scss']
})
export class CertInvalidationDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<CertInvalidationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public request: InvalidateCertificateRequest
  ) { }

  ngOnInit(): void {
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

}
