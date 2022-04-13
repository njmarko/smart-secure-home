import { Component, OnInit } from '@angular/core';
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

  constructor(private certificateService: CertificateService) { }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.certificateService.getCertificates().subscribe(response => {
      console.log(response);
      this.dataSource.data = response;
    });
  }

}
