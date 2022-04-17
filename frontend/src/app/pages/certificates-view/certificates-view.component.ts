import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { CertInvalidationDialogComponent } from 'src/app/components/cert-invalidation-dialog/cert-invalidation-dialog.component';
import InvalidateCertificateRequest from 'src/app/model/certificates/InvalidateCertificateRequest';
import ReadCertificateResponse from 'src/app/model/certificates/ReadCertificateResponse';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-certificates-view',
  templateUrl: './certificates-view.component.html',
  styleUrls: ['./certificates-view.component.scss']
})
export class CertificatesViewComponent implements OnInit {
  displayedColumns: string[] = [
    'serialNumber',
    'subjectName',
    'subjectOrg',
    'issuerName',
    'issuerOrg',
    'actions'
  ]

  dataSource: MatTableDataSource<ReadCertificateResponse> = new MatTableDataSource<ReadCertificateResponse>();

  constructor(
    private certificateService: CertificateService,
    private snackbar: MatSnackBar,
    private dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.certificateService.getCertificates().subscribe(response => {
      this.dataSource.data = response;
    });
  }

  onDetailsClick(cert: ReadCertificateResponse): void {
    this.router.navigate(['certificates/details', cert.serialNumber]);
  }

  onCheckValidity(cert: ReadCertificateResponse): void {
    // TODO: Kasnije mozda prikazati neki lepsi modal, a ne samo snackbar
    this.certificateService.checkValidity(cert.serialNumber).subscribe(validity => {
      if (validity.valid) {
        this.snackbar.open("Certificate is valid.", "Confirm");
      } else if (validity.expired) {
        this.snackbar.open("Certificate is expired.", "Confirm");
      } else if (!validity.started) {
        this.snackbar.open("Certificate has not yet started to be valid.", "Confirm");
      }
    })
  }

  onInvalidate(cert: ReadCertificateResponse): void {
    const request: InvalidateCertificateRequest = {
      reason: "UNSPECIFIED"
    }
    const dialogRef = this.dialog.open(CertInvalidationDialogComponent, {
      width: '250px',
      data: request,
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.certificateService.invalidate(cert.serialNumber, request).subscribe(_ => {
          this.fetchData();
          this.snackbar.open("Certificate has been invalidated.", "Confirm");
        })
      }
    })
  }

}
