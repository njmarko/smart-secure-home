import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BlacklistJwtComponent } from './pages/blacklist-jwt/blacklist-jwt.component';
import { CertificateDetailsComponent } from './pages/certificate-details/certificate-details.component';
import { CertificatesViewComponent } from './pages/certificates-view/certificates-view.component';
import { CreateRealEstateComponent } from './pages/create-real-estate/create-real-estate.component';
import { CsrFormComponent } from './pages/csr-form/csr-form.component';
import { CsrsViewComponent } from './pages/csrs-view/csrs-view.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { SignCsrComponent } from './pages/sign-csr/sign-csr.component';
import { UsersViewComponent } from './pages/users-view/users-view.component';
import { RoleGuard } from './guards/role/role.guard';
import { LoginGuard } from './guards/login/login.guard';
import { AuthorizedGuard } from './guards/authorized/authorized.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'new-csr', component: CsrFormComponent },
  { path: 'csrs', component: CsrsViewComponent },
  {
    path: 'certificates',
    component: CertificatesViewComponent,
    canActivate: [RoleGuard],
    data: { roles: ['ROLE_ADMIN', 'ROLE_SUPER_ADMIN'] },
  },
  {
    path: 'certificates/details/:id',
    component: CertificateDetailsComponent,
    canActivate: [RoleGuard],
    data: { roles: ['ROLE_ADMIN', 'ROLE_SUPER_ADMIN'] },
  },
  {
    path: 'csrs/sign-csr/:id',
    component: SignCsrComponent,
    canActivate: [RoleGuard],
    data: { roles: ['ROLE_ADMIN', 'ROLE_SUPER_ADMIN'] },
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [LoginGuard],
  },
  {
    path: 'users',
    component: UsersViewComponent,
    canActivate: [AuthorizedGuard],
  },
  {
    path: 'create-real-estate',
    component: CreateRealEstateComponent,
    canActivate: [RoleGuard],
    data: { roles: ['ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_OWNER'] },
  },
  {
    path: 'blacklist-jwt',
    component: BlacklistJwtComponent,
    canActivate: [RoleGuard],
    data: { roles: ['ROLE_ADMIN', 'ROLE_SUPER_ADMIN'] },
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [RoleGuard],
    data: { roles: ['ROLE_ADMIN', 'ROLE_SUPER_ADMIN'] },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
