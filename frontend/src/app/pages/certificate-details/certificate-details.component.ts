import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import ReadCertificateResponse from 'src/app/model/certificates/ReadCertificateResponse';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrls: ['./certificate-details.component.scss']
})
export class CertificateDetailsComponent implements OnInit {
  loaded: boolean = false;
  certificate!: ReadCertificateResponse;

  constructor(private route: ActivatedRoute, private certificateService: CertificateService) { }

  ngOnInit(): void {
    this.fetchCertificate();
  }

  fetchCertificate(): void {
    this.loaded = false;
    this.certificateService.readOne(this.route.snapshot.params['id']).subscribe(response => {
      this.certificate = response;
      this.loaded = true;
    });
  }

}
