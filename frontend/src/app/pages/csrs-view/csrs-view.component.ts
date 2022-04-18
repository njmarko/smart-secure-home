import { Component, OnInit } from '@angular/core';
import Csr from 'src/app/model/certificates/Csr';
import { Observer } from 'rxjs';
import { CertificateService } from 'src/app/services/certificate.service';
import { MatTableDataSource } from '@angular/material/table';
import { ConfirmationService } from 'src/app/shared/confirmation-service/confirmation.service';
import { ErrorService } from 'src/app/shared/error-service/error.service';
import CertificatePurpose from 'src/app/model/certificates/enum/CerificatePurpose';

@Component({
  selector: 'app-csrs-view',
  templateUrl: './csrs-view.component.html',
  styleUrls: ['./csrs-view.component.scss'],
})
export class CsrsViewComponent implements OnInit {
  csrs!: Csr[];
  displayedColumns: string[] = [
    'Id',
    'CommonName',
    'OrganizationalUnit',
    'Organization',
    'Locality',
    'State',
    'Country',
    'Purpose',
    'Sign',
    'Delete',
  ];

  dataSource: MatTableDataSource<Csr> = new MatTableDataSource<Csr>();
  pageNum: number = 0;
  pageSize: number = 0;
  totalPages: number = 0;
  defaultPageSize: number = 10;
  totalElements: number = 0;
  waitingResults: boolean = true;

  constructor(
    private certificateService: CertificateService,
    private confirmationService: ConfirmationService,
    private errorService: ErrorService
  ) {}

  ngOnInit(): void {
    this.fetchData(0, this.defaultPageSize);
    this.certificateService.csrs$.subscribe((value) => (this.csrs = value));
  }

  fetchData(pageIdx: number, pageSize: number): void {
    this.waitingResults = true;
    this.certificateService.read(pageIdx, pageSize).subscribe((page) => {
      // this.csrs = page.content.map(element => {element.purpose = (CertificatePurpose as any)[element.purpose as string]; return element;})
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

  onDeleteCSR(csr: Csr): void {
    this.waitingResults = true;
    this.confirmationService
      .confirm({
        title: `CSR deletion`,
        message: `Are you sure you want to delete CSR ${csr.id}?`,
        yes: 'Yes',
        no: 'No',
      })
      .subscribe((confirmation) => {
        if (confirmation) {
          const nextPage =
            this.pageSize == 1 && this.pageNum > 0
              ? this.pageNum - 1
              : this.pageNum;
          this.certificateService
            .delete(csr.id)
            .subscribe(this.getDefaultEntityServiceHandler(nextPage));
        } else {
          this.waitingResults = false;
        }
      });
  }
}
