import { Component, OnInit } from '@angular/core';
import Csr from 'src/app/model/Csr';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-csrs-view',
  templateUrl: './csrs-view.component.html',
  styleUrls: ['./csrs-view.component.scss']
})
export class CsrsViewComponent implements OnInit {

  csrs!: Csr[];
  displayedColumns: string[] = ['CommonName', 'OrganizationalUnit', 'Organization', 'Locality', 'State', 'Country', 'KeySize', 'Sign', 'Delete'];

  constructor(private certificateService: CertificateService,) { }

  ngOnInit(): void {
    this.certificateService.loadAllCsrs();
    this.certificateService.csrs$.subscribe((value) => (this.csrs = value));

  }

}
