import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CertificateDetailsComponent } from './pages/certificate-details/certificate-details.component';
import { CertificatesViewComponent } from './pages/certificates-view/certificates-view.component';
import { CsrFormComponent } from './pages/csr-form/csr-form.component';
import { CsrsViewComponent } from './pages/csrs-view/csrs-view.component';
import { HomeComponent } from './pages/home/home.component';
import { SignCsrComponent } from './pages/sign-csr/sign-csr.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'new-csr', component: CsrFormComponent },
  { path: 'csrs', component: CsrsViewComponent },
  { path: 'certificates', component: CertificatesViewComponent },
  { path: 'certificates/details/:id', component: CertificateDetailsComponent },
  { path: 'csrs/sign-csr/:id', component: SignCsrComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
