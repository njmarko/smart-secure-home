import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';
import Csr from '../model/certificates/Csr';
import ReadCertificateResponse from '../model/certificates/ReadCertificateResponse';
import CheckValidityResponse from '../model/certificates/CheckValidityResponse';
import CertificatePurpose from '../model/certificates/enum/CerificatePurpose';
import CsrSignData from '../model/certificates/CsrSignData';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  private readonly _csrsSource = new BehaviorSubject<Csr[]>([]);

  readonly csrs$ = this._csrsSource.asObservable();

  getCsrs(): Csr[] {
    return this._csrsSource.getValue();
  }

  private _setCsrs(csrs: Csr[]): void {
    this._csrsSource.next(csrs);
  }

  constructor(private http: HttpClient) { }

  getCertificates() {
    // TODO: Ko ima volje nek podesi onaj proxy i/ili putanju u env xD
    return this.http.get<ReadCertificateResponse[]>('http://localhost:8082/api/certificates');
  }

  checkValidity(id: number) {
    return this.http.get<CheckValidityResponse>(`http://localhost:8082/api/certificates/${id}/validity`);
  }

  invalidate(id: number) {
    return this.http.post(`http://localhost:8082/api/certificates/${id}/invalidate`, {});
  }

  signCsr(csr: Csr, newCert: CsrSignData) {
    throw new Error('Method not implemented.');
  }

  loadAllCsrs() {
    // TO:DO
    // this.http.get(environment.adminAppUrl + 'certificates/all').subscribe((data : any) => {
    //   this._setCsrs(data);
    //  })

    //dummy
    this._setCsrs([
      { id:0, x500Name: { commonName: "Dusan Hajduk", country: "RS", locale: "Sid", state: "Sremski okrug", organization: "FTN", organizationUnit: "SW" }, purpose: CertificatePurpose.STANDARD_USER },
      { id:1, x500Name: { commonName: "Filip Zivanac", country: "RS", locale: "Titel", state: "Juznobacki okrug", organization: "FTN", organizationUnit: "SW" }, purpose: CertificatePurpose.STANDARD_USER },
      { id:2, x500Name: { commonName: "Matija Pojatar", country: "RS", locale: "Kikinda", state: "Severnobanatski okrug", organization: "FTN", organizationUnit: "SW" }, purpose: CertificatePurpose.ADVANCED_USER },
    ])
  }

  async generateCSR(csr: Csr | null, alertCallback: CallableFunction) {
    if (csr) {
      const httpResponse = await this.http.post(environment.adminAppUrl + "certificates/generateCSR", csr, { responseType: 'json', "observe": 'response' }).toPromise()
        .then((response: any) => {
          alertCallback(response);
        })
        .catch((response: any) => {
          alertCallback(response);
        });
    }
  }

  async sendCSR(csrPem: string | null, alertCallback: CallableFunction) {
    if (csrPem) {
      const httpResponse = await this.http.post(environment.adminAppUrl + "certificates/addCSR", csrPem, { responseType: 'json', "observe": 'response' }).toPromise()
        .then((response: any) => {
          alertCallback(response);
        })
        .catch((response: any) => {
          alertCallback(response);
        });
    }
  }
}
