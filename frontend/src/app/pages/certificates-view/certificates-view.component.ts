import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import ReadCertificateResponse from 'src/app/model/ReadCertificateResponse';
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

  constructor(private certificateService: CertificateService, private snackbar: MatSnackBar) { }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.certificateService.getCertificates().subscribe(response => {
      console.log(response);
      this.dataSource.data = response;
    });
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
    // TODO: Kasnije ubaciti neki dijalog za potvrdu ili tako nesto
    this.certificateService.invalidate(cert.serialNumber).subscribe(_ => {
      this.fetchData();
      this.snackbar.open("Certificate has been invalidated.", "Confirm");
    })
  }

}
