import { Component, OnInit } from '@angular/core';
import Csr from 'src/app/model/certificates/Csr';
import { CertificateService } from 'src/app/services/certificate.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-csrs-view',
  templateUrl: './csrs-view.component.html',
  styleUrls: ['./csrs-view.component.scss'],
})
export class CsrsViewComponent implements OnInit {
  csrs!: Csr[];
  displayedColumns: string[] = [
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

  constructor(private certificateService: CertificateService) {}

  ngOnInit(): void {
    this.fetchData(0, this.defaultPageSize);
    this.certificateService.csrs$.subscribe((value) => (this.csrs = value));
  }

  fetchData(pageIdx: number, pageSize: number): void {
    this.waitingResults = true;
    this.certificateService.read(pageIdx, pageSize).subscribe((page) => {
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
}
