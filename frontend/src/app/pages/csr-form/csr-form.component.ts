import { Component, OnInit, Inject } from '@angular/core';
import Csr from 'src/app/model/certificates/Csr';
import { CertificateService } from 'src/app/services/certificate.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import CertificatePurpose from 'src/app/model/certificates/enum/CerificatePurpose';

@Component({
  selector: 'app-csr-form',
  templateUrl: './csr-form.component.html',
  styleUrls: ['./csr-form.component.scss'],
})
export class CsrFormComponent implements OnInit {
  csr!: Csr;
  csrPem: string = '';
  certificatePurposes = Object.values(CertificatePurpose);

  constructor(
    private _certificateService: CertificateService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.csr = {
      id: 0,
      x500Name: {
        country: '',
        state: '',
        locale: '',
        organization: '',
        organizationUnit: '',
        commonName: '',
      },
      purpose: CertificatePurpose.STANDARD_USER,
    };
  }

  generateCSR(): void {
    // fill the rest of the data
    this.csr.x500Name.country = this.csr.x500Name.country
      ? this.csr.x500Name.country
      : 'RS';
    this.csr.x500Name.state = this.csr.x500Name.state
      ? this.csr.x500Name.state
      : 'Juznobacki Okrug';
    this.csr.x500Name.locale = this.csr.x500Name.locale
      ? this.csr.x500Name.locale
      : 'Novi Sad';
    this.csr.x500Name.organization = this.csr.x500Name.organization
      ? this.csr.x500Name.organization
      : 'Fakultet tehnickih nauka';
    this.csr.x500Name.organizationUnit = this.csr.x500Name.organizationUnit
      ? this.csr.x500Name.organizationUnit
      : 'Departman za elektrotehniku i racunarstvo';
    this.csr.x500Name.commonName = this.csr.x500Name.commonName
      ? this.csr.x500Name.commonName
      : 'Dusan Hajduk';

    this._certificateService.generateCSR(this.csr, this.alertResponse);

    this._snackBar.open('random message', 'Dismiss');
  }

  sendGeneratedCSR(): void {
    this.csrPem = this.csrPem
      ? this.csrPem
      : '-----BEGIN CERTIFICATE REQUEST-----\n' +
        'MIICxDCCAawCAQAwfzELMAkGA1UEBhMCVVMxETAPBgNVBAgMCElsbGlub2lzMRAw\n' +
        'DgYDVQQHDAdDaGljYWdvMQ4wDAYDVQQKDAVDb2RhbDELMAkGA1UECwwCTkExDjAM\n' +
        'BgNVBAMMBUNvZGFsMR4wHAYJKoZIhvcNAQkBFg9rYmF4aUBjb2RhbC5jb20wggEi\n' +
        'MA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDSrEF27VvbGi5x7LnPk4hRigAW\n' +
        '1feGeKOmRpHd4j/kUcJZLh59NHJHg5FMF7u9YdZgnMdULawFVezJMLSJYJcCAdRR\n' +
        'hSN+skrQlB6f5wgdkbl6ZfNaMZn5NO1Ve76JppP4gl0rXHs2UkRJeb8lguOpJv9c\n' +
        'tw+Sn6B13j8jF/m/OhIYI8fWhpBYvDXukgADTloCjOIsAvRonkIpWS4d014deKEe\n' +
        '5rhYX67m3H7GtZ/KVtBKhg44ntvuT2fR/wB1FlDws+0gp4edlkDlDml1HXsf4FeC\n' +
        'ogijo6+C9ewC2anpqp9o0CSXM6BT2I0h41PcQPZ4EtAc4ctKSlzTwaH0H9MbAgMB\n' +
        'AAGgADANBgkqhkiG9w0BAQsFAAOCAQEAqfQbrxc6AtjymI3TjN2upSFJS57FqPSe\n' +
        'h1YqvtC8pThm7MeufQmK9Zd+Lk2qnW1RyBxpvWe647bv5HiQaOkGZH+oYNxs1XvM\n' +
        'y5huq+uFPT5StbxsAC9YPtvD28bTH7iXR1b/02AK2rEYT8a9/tCBCcTfaxMh5+fr\n' +
        'maJtj+YPHisjxKW55cqGbotI19cuwRogJBf+ZVE/4hJ5w/xzvfdKjNxTcNr1EyBE\n' +
        '8ueJil2Utd1EnVrWbmHQqnlAznLzC5CKCr1WfmnrDw0GjGg1U6YpjKBTc4MDBQ0T\n' +
        '56ZL2yaton18kgeoWQVgcbK4MXp1kySvdWq0Bc3pmeWSM9lr/ZNwNQ==\n' +
        '-----END CERTIFICATE REQUEST-----\n';
    this._certificateService.sendCSR(this.csrPem, this.alertResponse);

    this._snackBar.open('random message', 'Dismiss');
  }

  alertResponse(response: any) {
    if (response.data) this._snackBar.open(response.data, 'Dismiss');
  }
}
