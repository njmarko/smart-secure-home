import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import CertificateData from 'src/app/model/certificates/CertificateData';
import Csr from 'src/app/model/certificates/Csr';
import SignatureAlg from 'src/app/model/certificates/enum/SignatureAlg';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-sign-csr',
  templateUrl: './sign-csr.component.html',
  styleUrls: ['./sign-csr.component.scss']
})
export class SignCsrComponent implements OnInit {

  csr!: Csr;
  newCert!: CertificateData;
  signatureAlgs = Object.values(SignatureAlg);
  subjectName!: string;

  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  constructor(private _route: ActivatedRoute, private _certificateService: CertificateService) { }

  signCsr(){
    this.newCert.validityStart = new Date(this.range.value.start);
    this.newCert.validityEnd = new Date(this.range.value.end);
    this._certificateService.signCsr(this.csr, this.newCert);
  }

  ngOnInit(): void {
    this._certificateService.loadAllCsrs();
    this.csr = this._certificateService.getCsrs().find(el => el.id == this._route.snapshot.params['id'])!;
    this.newCert = {
      extensions: [],
      issuerName: {},
      serialNumber: null,
      signatureAlg: SignatureAlg.SHA_256_WITH_RSA,
      subjectName: this.csr?.x500Name ? this.csr?.x500Name : {},
      validityStart: new Date(),
      validityEnd: (() => { let d = new Date(); d.setFullYear(d.getFullYear() + 1); return d; })(),
    }
    this.subjectName = [
      this.csr.x500Name.commonName,
      this.csr.x500Name.locale,
      this.csr.x500Name.state,
      this.csr.x500Name.country,
      this.csr.x500Name.email,
      this.csr.x500Name.organization,
      this.csr.x500Name.organizationUnit,
    ].join(", ")



  }

}
