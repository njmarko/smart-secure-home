import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { ActivatedRoute } from '@angular/router';
import Csr from 'src/app/model/certificates/Csr';
import CsrSignData from 'src/app/model/certificates/CsrSignData';
import SignatureAlg from 'src/app/model/certificates/enum/SignatureAlg';
import AuthorityKeyIdentifier from 'src/app/model/extensions/AuthorityKeyIdentifier';
import BasicConstraints from 'src/app/model/extensions/BasicConstraints';
import ExtendedKeyUsageEnum from 'src/app/model/extensions/enum/ExtendedKeyUsageEnum';
import ExtensionTemplate from 'src/app/model/extensions/enum/ExtensionTemplate';
import KeyUsageEnum from 'src/app/model/extensions/enum/KeyUsageEnum';
import Extensions from 'src/app/model/extensions/Extensions';
import { SubjectKeyIdentifier } from 'src/app/model/extensions/SubjectKeyIdentifier';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-sign-csr',
  templateUrl: './sign-csr.component.html',
  styleUrls: ['./sign-csr.component.scss']
})
export class SignCsrComponent implements OnInit {

  csr!: Csr;
  newCert!: CsrSignData;
  signatureAlgs = Object.values(SignatureAlg);
  subjectName!: string;

  //extensions
  extensionTemplates = Object.values(ExtensionTemplate);
  keyUsages = Object.values(KeyUsageEnum);
  extendedKeyUsages = Object.values(ExtendedKeyUsageEnum);
  keyUsageEnumRef = KeyUsageEnum;
  ExtendedKeyUsageEnumRef = ExtendedKeyUsageEnum;
  showsan: boolean = false;
  showbc: boolean = true;
  showeku: boolean = false;

  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  constructor(private _route: ActivatedRoute, private _certificateService: CertificateService) { }

  signCsr() {
    this.newCert.validityStart = new Date(this.range.value.start);
    this.newCert.validityEnd = new Date(this.range.value.end);
    this._certificateService.signCsr(this.newCert);
  }

  changeTemplate(event: MatTabChangeEvent) {
    switch (event.tab.textLabel) {
      case ExtensionTemplate.CA: {
        this.newCert.extensions = {
          keyUsage: { keyUsages: [KeyUsageEnum.CERTIFICATE_SIGNING, KeyUsageEnum.CRL_SIGN], isUsed: true },
          authorityKeyIdentifier: { keyIdentifier: "", isUsed: false },
          basicConstraints: { subjectIsCa: true, isUsed: true },
          subjectKeyIdentifier: { keyIdentifier: "", isUsed: false },
          extendedKeyUsage: { keyUsages: [], isUsed: false },
          subjectAlternativeName: { name: "", isUsed: false },
        };
        this.showsan = false;
        this.showbc = true;
        this.showeku = false;
        break;
      }
      case ExtensionTemplate.SSL_SERVER: {
        this.newCert.extensions = {
          keyUsage: { keyUsages: [KeyUsageEnum.DIGITAL_SIGNATURE, KeyUsageEnum.KEY_ENCIPHERMENT], isUsed: true },
          authorityKeyIdentifier: { keyIdentifier: "", isUsed: false },
          basicConstraints: { subjectIsCa: false, isUsed: false },
          subjectKeyIdentifier: { keyIdentifier: "", isUsed: false },
          extendedKeyUsage: { keyUsages: [ExtendedKeyUsageEnum.TLS_WEB_SERVER_AUTHENTICATION], isUsed: true },
          subjectAlternativeName: { name: "", isUsed: false },
        }
        this.showsan = true;
        this.showbc = false;
        this.showeku = true;
        break;
      }
      case ExtensionTemplate.SSL_CLIENT: {
        this.newCert.extensions = {
          keyUsage: { keyUsages: [KeyUsageEnum.DIGITAL_SIGNATURE, KeyUsageEnum.KEY_ENCIPHERMENT], isUsed: true },
          authorityKeyIdentifier: { keyIdentifier: "", isUsed: false },
          basicConstraints: { subjectIsCa: false, isUsed: false },
          subjectKeyIdentifier: { keyIdentifier: "", isUsed: false },
          extendedKeyUsage: { keyUsages: [ExtendedKeyUsageEnum.TLS_WEB_CLIENT_AUTHENTICATION], isUsed: true },
          subjectAlternativeName: { name: "", isUsed: false },
        }
        this.showsan = false;
        this.showbc = false;
        this.showeku = true;
        break;
      }
      case ExtensionTemplate.CODE_SIGNING: {
        this.newCert.extensions = {
          keyUsage: { keyUsages: [KeyUsageEnum.DIGITAL_SIGNATURE, KeyUsageEnum.KEY_ENCIPHERMENT], isUsed: true },
          authorityKeyIdentifier: { keyIdentifier: "", isUsed: false },
          basicConstraints: { subjectIsCa: false, isUsed: false },
          subjectKeyIdentifier: { keyIdentifier: "", isUsed: false },
          extendedKeyUsage: { keyUsages: [ExtendedKeyUsageEnum.CODE_SIGNING], isUsed: true },
          subjectAlternativeName: { name: "", isUsed: false },
        }
        this.showsan = false;
        this.showbc = false;
        this.showeku = true;
        break;
      }
    }

  }

  updateCheckedKeyUsages(usage: string, event: MatCheckboxChange) {
    if (event.checked) {
      this.newCert.extensions.keyUsage.keyUsages.push(usage as KeyUsageEnum)
    } else {
      this.newCert.extensions.keyUsage.keyUsages = this.newCert.extensions.keyUsage.keyUsages.filter(elem => elem != usage)
    }
  }

  updateCheckedExtendedKeyUsages(usage: string, event: MatCheckboxChange) {
    if (event.checked) {
      this.newCert.extensions.extendedKeyUsage.keyUsages.push(usage as ExtendedKeyUsageEnum)
    } else {
      this.newCert.extensions.extendedKeyUsage.keyUsages = this.newCert.extensions.extendedKeyUsage.keyUsages.filter(elem => elem != usage)
    }
  }

  ngOnInit(): void {
    this._certificateService.loadAllCsrs();
    this.csr = this._certificateService.getCsrs().find(el => el.id == this._route.snapshot.params['id'])!;
    this.newCert = {
      extensions: {
        keyUsage: { keyUsages: [KeyUsageEnum.CERTIFICATE_SIGNING, KeyUsageEnum.CRL_SIGN], isUsed: true },
        authorityKeyIdentifier: { keyIdentifier: "", isUsed: false },
        basicConstraints: { subjectIsCa: true, isUsed: true },
        subjectKeyIdentifier: { keyIdentifier: "", isUsed: false },
        extendedKeyUsage: { keyUsages: [], isUsed: false },
        subjectAlternativeName: { name: "", isUsed: false },
      },
      signatureAlg: SignatureAlg.SHA_256_WITH_RSA,
      csr: this.csr,
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
