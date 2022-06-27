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
import { ManageRealEstatesComponent } from './pages/manage-real-estates/manage-real-estates.component';
import { ViewRealEstatesComponent } from './pages/view-real-estates/view-real-estates.component';
import { ConfigureDevicesComponent } from './pages/configure-devices/configure-devices.component';
import { LogsViewComponent } from './pages/logs-view/logs-view.component';
import { AlarmsViewComponent } from './pages/alarms-view/alarms-view.component';
import { ManageAlarmRulesComponent } from './pages/manage-alarm-rules/manage-alarm-rules.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'new-csr', component: CsrFormComponent },
  {
    path: 'csrs',
    component: CsrsViewComponent,
    canActivate: [RoleGuard],
    data: { roles: ['READ_CSRS'] },
  },
  {
    path: 'certificates',
    component: CertificatesViewComponent,
    canActivate: [RoleGuard],
    data: { roles: ['READ_CERTIFICATES'] },
  },
  {
    path: 'certificates/details/:id',
    component: CertificateDetailsComponent,
    canActivate: [RoleGuard],
    data: { roles: ['READ_CSR_DETAILS'] },
  },
  {
    path: 'csrs/sign-csr/:id',
    component: SignCsrComponent,
    canActivate: [RoleGuard],
    data: { roles: ['SIGN_CSR'] },
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
    data: { roles: ['CREATE_REAL_ESTATE'] },
  },
  {
    path: 'real-estates',
    component: ViewRealEstatesComponent,
    canActivate: [RoleGuard],
    data: { roles: ['CONFIGURE_DEVICES'] }
  },
  {
    path: 'real-estates/:id/configure-devices',
    component: ConfigureDevicesComponent,
    canActivate: [RoleGuard],
    data: { roles: ['CONFIGURE_DEVICES'] }
  },
  {
    path: 'blacklist-jwt',
    component: BlacklistJwtComponent,
    canActivate: [RoleGuard],
    data: { roles: ['BLACKLIST_JWT'] },
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [RoleGuard],
    data: { roles: ['REGISTER_USERS'] },
  },
  {
    path: 'manage-real-estates/:id',
    component: ManageRealEstatesComponent,
    canActivate: [RoleGuard],
    data: { roles: ['ADD_REAL_ESTATE_TO_USER'] },
  },
  {
    path: 'logs',
    component: LogsViewComponent,
    canActivate: [RoleGuard],
    data: { roles: ['READ_LOGS'] },
  },
  {
    path: 'alarms',
    component: AlarmsViewComponent,
    canActivate: [RoleGuard],
    data: { roles: ['READ_ALARMS'] },
  },
  {
    path: 'alarm-rules',
    component: ManageAlarmRulesComponent,
    canActivate: [RoleGuard],
    data: { roles: ['MANAGE_ALARM_RULES'] },
  },
  {
    path: 'device-alarm-rules',
    component: ManageAlarmRulesComponent,
    canActivate: [RoleGuard],
    data: { roles: ['MANAGE_DEVICE_ALARM_RULES'] },
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
