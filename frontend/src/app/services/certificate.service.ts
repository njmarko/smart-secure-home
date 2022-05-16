import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import Csr from '../model/certificates/Csr';
import ReadCertificateResponse from '../model/certificates/ReadCertificateResponse';
import CheckValidityResponse from '../model/certificates/CheckValidityResponse';
import {CertificatePurpose} from '../model/certificates/enum/CerificatePurpose';
import CsrSignData from '../model/certificates/CsrSignData';
import InvalidateCertificateRequest from '../model/certificates/InvalidateCertificateRequest';
import { PaginatedResponse } from '../shared/types/PaginatedResponse';

@Injectable({
  providedIn: 'root',
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

  getCertificates(): Observable<ReadCertificateResponse[]> {
    // TODO: Ko ima volje nek podesi onaj proxy i/ili putanju u env xD
    return this.http.get<ReadCertificateResponse[]>(
      'http://localhost:8082/api/certificates'
    );
  }

  checkValidity(id: number): Observable<CheckValidityResponse> {
    return this.http.get<CheckValidityResponse>(
      `http://localhost:8082/api/certificates/${id}/validity`
    );
  }

  invalidate(id: number, request: InvalidateCertificateRequest) {
    return this.http.post(
      `http://localhost:8082/api/certificates/${id}/invalidate`,
      request
    );
  }

  signCsr(newCert: CsrSignData) {
    return this.http.post(
      `http://localhost:8082/api/certificates`, newCert
    );
  }

  delete(id: number): Observable<void> {
    console.log(id);
    return this.http.delete<void>(
      `${environment.adminAppUrl}certificates/csr/${id}`
    );
  }

  read(page: number, size: number) {
    return this.http.get<PaginatedResponse<Csr>>(
      `${environment.adminAppUrl}certificates/csr`,
      {
        params: {
          page: page,
          size: size,
          sort: 'id,asc',
        },
      }
    );
  }

  readOne(id: number): Observable<ReadCertificateResponse> {
    return this.http.get<ReadCertificateResponse>(`${environment.adminAppUrl}certificates/${id}`);
  }

  readCsr(id: number): Observable<Csr> {
    return this.http.get<Csr>(`${environment.adminAppUrl}certificates/csr/${id}`);
  }

  generateCSR(csr: Csr | null) {
    if (csr) {
      return this.http
        .post(environment.adminAppUrl + 'certificates/generateCSR', csr, {
          responseType: 'json',
          observe: 'response',
        })
    } else return null
  }

  sendCSR(csrPem: string | null) {
    if (csrPem) {
      return this.http
        .post(environment.adminAppUrl + 'certificates/addCSR', csrPem, {
          responseType: 'json',
          observe: 'response',
        })
    } else return null
  }


}
