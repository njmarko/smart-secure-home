import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CertificateDetailsComponent } from './pages/certificate-details/certificate-details.component';
import { CertificatesViewComponent } from './pages/certificates-view/certificates-view.component';
import { CreateRealEstateComponent } from './pages/create-real-estate/create-real-estate.component';
import { CsrFormComponent } from './pages/csr-form/csr-form.component';
import { CsrsViewComponent } from './pages/csrs-view/csrs-view.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { SignCsrComponent } from './pages/sign-csr/sign-csr.component';
import { UsersViewComponent } from './pages/users-view/users-view.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'new-csr', component: CsrFormComponent },
  { path: 'csrs', component: CsrsViewComponent },
  { path: 'certificates', component: CertificatesViewComponent },
  { path: 'certificates/details/:id', component: CertificateDetailsComponent },
  { path: 'csrs/sign-csr/:id', component: SignCsrComponent },
  { path: 'login', component: LoginComponent },
  { path: 'users', component: UsersViewComponent },
  { path: 'create-real-estate', component: CreateRealEstateComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
