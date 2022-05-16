import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { ActivatedRoute, Router } from '@angular/router';
import Csr from 'src/app/model/certificates/Csr';
import CsrSignData from 'src/app/model/certificates/CsrSignData';
import { certificatePurposeLabels } from 'src/app/model/certificates/enum/CerificatePurpose';
import {SignatureAlg, signatureAlgLabels} from 'src/app/model/certificates/enum/SignatureAlg';
import AuthorityKeyIdentifier from 'src/app/model/extensions/AuthorityKeyIdentifier';
import BasicConstraints from 'src/app/model/extensions/BasicConstraints';
import {ExtendedKeyUsageEnum, ekuLabels} from 'src/app/model/extensions/enum/ExtendedKeyUsageEnum';
import {ExtensionTemplate, extensionTemplateLabels} from 'src/app/model/extensions/enum/ExtensionTemplate';
import {KeyUsageEnum, kuLabels} from 'src/app/model/extensions/enum/KeyUsageEnum';
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
  SignatureAlgRef = SignatureAlg;
  signatureAlgLabelsRef = signatureAlgLabels;
  certificatePurposeLabelsRef = certificatePurposeLabels;
  subjectName!: string;

  //extensions
  ExtensionTemplateRef = ExtensionTemplate;
  extensionTemplateLabelsRef = extensionTemplateLabels;
  ExtendedKeyUsageRef = ExtendedKeyUsageEnum;
  ekuLabelsRef = ekuLabels;
  KeyUsageRef = KeyUsageEnum;
  kuLabelsRef = kuLabels;

  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  constructor(private _route: ActivatedRoute, private _certificateService: CertificateService, private router: Router) { }

  signCsr() {
    this.newCert.validityStart = new Date(this.range.value.start);
    this.newCert.validityEnd = new Date(this.range.value.end);
    this.checkExtensionsUsed()
    this._certificateService.signCsr(this.newCert).subscribe(()=>{
      this.router.navigate(['/certificates'])
    });
  }

  checkExtensionsUsed() {
    //aki
    if (this.newCert.extensions.authorityKeyIdentifier.keyIdentifier) {
      this.newCert.extensions.authorityKeyIdentifier.isUsed = true;
    } else {
      this.newCert.extensions.authorityKeyIdentifier.isUsed = false;
    }
    //bc
    if (this.newCert.extensions.basicConstraints.subjectIsCa) {
      this.newCert.extensions.basicConstraints.isUsed = true
    } else {
      this.newCert.extensions.basicConstraints.isUsed = false;
    }
    //eku
    if (this.newCert.extensions.extendedKeyUsage.keyUsages) {
      this.newCert.extensions.extendedKeyUsage.isUsed = true
    } else {
      this.newCert.extensions.extendedKeyUsage.isUsed = false;
    }
    //ku
    if (this.newCert.extensions.keyUsage.keyUsages) {
      this.newCert.extensions.keyUsage.isUsed = true
    } else {
      this.newCert.extensions.keyUsage.isUsed = false;
    }
    //san
    if (this.newCert.extensions.subjectAlternativeName.name) {
      this.newCert.extensions.subjectAlternativeName.isUsed = true
    } else {
      this.newCert.extensions.subjectAlternativeName.isUsed = false;
    }
    //ski
    if (this.newCert.extensions.subjectKeyIdentifier.keyIdentifier) {
      this.newCert.extensions.subjectKeyIdentifier.isUsed = true
    } else {
      this.newCert.extensions.subjectKeyIdentifier.isUsed = false;
    }

  }

  changeTemplate(event: MatTabChangeEvent) {
    switch (event.tab.position) {
      case ExtensionTemplate.CA: {
        this.newCert.extensions = {
          keyUsage: { keyUsages: [KeyUsageEnum.CERTIFICATE_SIGNING, KeyUsageEnum.CRL_SIGN], isUsed: true },
          authorityKeyIdentifier: { keyIdentifier: "", isUsed: false },
          basicConstraints: { subjectIsCa: true, isUsed: true },
          subjectKeyIdentifier: { keyIdentifier: "", isUsed: false },
          extendedKeyUsage: { keyUsages: [], isUsed: false },
          subjectAlternativeName: { name: "", isUsed: false },
        };
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
        break;
      }
    }

  }

  updateCheckedKeyUsages(i: number, event: MatCheckboxChange) {
    if (event.checked) {
      this.newCert.extensions.keyUsage.keyUsages.push(i)
    } else {
      this.newCert.extensions.keyUsage.keyUsages = this.newCert.extensions.keyUsage.keyUsages.filter(elem => elem != i)
    }
  }

  updateCheckedExtendedKeyUsages(i: number, event: MatCheckboxChange) {
    if (event.checked) {
      this.newCert.extensions.extendedKeyUsage.keyUsages.push(i)
    } else {
      this.newCert.extensions.extendedKeyUsage.keyUsages = this.newCert.extensions.extendedKeyUsage.keyUsages.filter(elem => elem != i)
    }
  }

  ngOnInit(): void {
    this._certificateService.readCsr(this._route.snapshot.params['id']).subscribe((response) => {
      this.csr = response
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
    });
  }

}
