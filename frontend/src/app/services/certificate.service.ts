import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';
import X500Name from '../model/Csr';
import Csr from '../model/Csr';
import ReadCertificateResponse from '../model/ReadCertificateResponse';

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

  loadAllCsrs() {
    // TO:DO
    // this.http.get(environment.adminAppUrl + 'certificates/all').subscribe((data : any) => {
    //   this._setCsrs(data);
    //  })

    //dummy
    this._setCsrs([
      {commonName: "Dusan Hajduk", country: "RS", keySize:2048, locality: "Sid", state: "Sremski okrug", organization: "FTN", organizationalUnit: "SW"},
      {commonName: "Filip Zivanac", country: "RS", keySize:2048, locality: "Titel", state: "Juznobacki okrug", organization: "FTN", organizationalUnit: "SW"},
      {commonName: "Matija Pojatar", country: "RS", keySize:2048, locality: "Kikinda", state: "Severnobanatski okrug", organization: "FTN", organizationalUnit: "SW"},
    ])
  }

  async generateCSR(x500Name: X500Name | null, alertCallback: CallableFunction) {
    if (x500Name) {
      const httpResponse = await this.http.post(environment.adminAppUrl + "certificates/generateCSR", x500Name, { responseType: 'json', "observe": 'response' }).toPromise()
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
