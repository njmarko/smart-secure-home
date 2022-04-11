import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CertificatesViewComponent } from './pages/certificates-view/certificates-view.component';
import { CsrFormComponent } from './pages/csr-form/csr-form.component';
import { CsrsViewComponent } from './pages/csrs-view/csrs-view.component';
import { HomeComponent } from './pages/home/home.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'new-csr', component: CsrFormComponent},
  {path: 'csrs', component: CsrsViewComponent},
  {path: 'certificates', component: CertificatesViewComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
