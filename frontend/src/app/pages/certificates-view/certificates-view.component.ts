import { Component, OnInit } from '@angular/core';
import { ErrorService } from 'src/app/shared/error-service/error.service';
import { Observer } from 'rxjs';
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
  styleUrls: ['./certificates-view.component.scss'],
})
export class CertificatesViewComponent implements OnInit {
  displayedColumns: string[] = [
    'serialNumber',
    'subjectName',
    'subjectOrg',
    'issuerName',
    'issuerOrg',
    'actions',
  ];

  dataSource: MatTableDataSource<ReadCertificateResponse> =
    new MatTableDataSource<ReadCertificateResponse>();
  pageNum: number = 0;
  pageSize: number = 0;
  totalPages: number = 0;
  defaultPageSize: number = 10;
  totalElements: number = 0;
  waitingResults: boolean = true;

  constructor(
    private certificateService: CertificateService,
    private snackbar: MatSnackBar,
    private dialog: MatDialog,
    private router: Router,
    private errorService: ErrorService
  ) {}

  ngOnInit(): void {
    this.fetchData(0, this.defaultPageSize);
  }

  fetchData(pageIdx: number, pageSize: number): void {
    this.waitingResults = true;
    this.certificateService
      .getCertificates(pageIdx, pageSize)
      .subscribe((page) => {
        this.pageNum = page.pageable.pageNumber;
        this.pageSize = page.pageable.pageSize;
        this.totalPages = page.totalPages;
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
        this.waitingResults = false;
      });
  }

  onSelectPage(event: any): void {
    this.fetchData(event.pageIndex, event.pageSize);
  }

  onDetailsClick(cert: ReadCertificateResponse): void {
    this.router.navigate(['certificates/details', cert.serialNumber]);
  }

  onCheckValidity(cert: ReadCertificateResponse): void {
    // TODO: Kasnije mozda prikazati neki lepsi modal, a ne samo snackbar
    this.certificateService
      .checkValidity(cert.serialNumber)
      .subscribe((validity) => {
        if (validity.valid) {
          this.snackbar.open('Certificate is valid.', 'Confirm');
        } else if (validity.expired) {
          this.snackbar.open('Certificate is expired.', 'Confirm');
        } else if (!validity.started) {
          this.snackbar.open(
            'Certificate has not yet started to be valid.',
            'Confirm'
          );
        }
      });
  }

  getDefaultEntityServiceHandler<TResponse = void>(
    page?: number
  ): Partial<Observer<TResponse>> {
    return {
      next: (_) => {
        this.fetchData(page ?? this.pageNum, this.pageSize);
      },
      error: (err) => {
        this.errorService.handle(err);
        this.waitingResults = false;
      },
    };
  }

  onInvalidate(cert: ReadCertificateResponse): void {
    this.waitingResults = true;
    const request: InvalidateCertificateRequest = {
      reason: 'UNSPECIFIED',
    };
    const dialogRef = this.dialog.open(CertInvalidationDialogComponent, {
      width: '250px',
      data: request,
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        const nextPage =
          this.pageSize == 1 && this.pageNum > 0
            ? this.pageNum - 1
            : this.pageNum;
        this.certificateService
          .invalidate(cert.serialNumber, request)
          .subscribe((_) => {
            this.getDefaultEntityServiceHandler(nextPage);
            this.snackbar.open('Certificate has been invalidated.', 'Confirm');
          });
      } else {
        this.waitingResults = false;
      }
    });
  }
}
