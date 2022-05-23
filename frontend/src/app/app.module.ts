import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTabsModule } from '@angular/material/tabs';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { ToolbarComponent } from './shared/toolbar/toolbar.component';
import { CsrFormComponent } from './pages/csr-form/csr-form.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatRadioModule } from '@angular/material/radio';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatTableModule } from '@angular/material/table';
import { CsrsViewComponent } from './pages/csrs-view/csrs-view.component';
import { CertificatesViewComponent } from './pages/certificates-view/certificates-view.component';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { SignCsrComponent } from './pages/sign-csr/sign-csr.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';
import { CertInvalidationDialogComponent } from './components/cert-invalidation-dialog/cert-invalidation-dialog.component';
import { SuchEmptyComponent } from './shared/such-empty/such-empty.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatPaginatorModule } from '@angular/material/paginator';
import { CommonModule } from '@angular/common';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { MatNativeDateModule } from '@angular/material/core';
import { MatChipsModule } from '@angular/material/chips';
import { ConfirmationDialogComponent } from './shared/confirmation-dialog/confirmation-dialog.component';
import { CertificateDetailsComponent } from './pages/certificate-details/certificate-details.component';
import { LoginComponent } from './pages/login/login.component';
import { JwtInterceptor } from './shared/interceptors/jwt.interceptor';
import { UsersViewComponent } from './pages/users-view/users-view.component';
import { CreateRealEstateComponent } from './pages/create-real-estate/create-real-estate.component';
import { BlacklistJwtComponent } from './pages/blacklist-jwt/blacklist-jwt.component';
import { RegisterComponent } from './pages/register/register.component';


@NgModule({
  declarations: [
    // page components
    AppComponent,
    HomeComponent,
    ToolbarComponent,
    CsrFormComponent,
    CsrsViewComponent,
    CertificatesViewComponent,
    SignCsrComponent,
    CertInvalidationDialogComponent,
    SuchEmptyComponent,
    ConfirmationDialogComponent,
    CertificateDetailsComponent,
    LoginComponent,
    UsersViewComponent,
    CreateRealEstateComponent,
    BlacklistJwtComponent,
    RegisterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,

    // cdk imports
    LayoutModule,

    // material imports
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    MatRadioModule,
    MatButtonToggleModule,
    MatTableModule,
    MatGridListModule,
    MatSelectModule,
    MatDialogModule,
    MatMomentDateModule,
    MatDatepickerModule,
    MatCheckboxModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    CommonModule,
    ReactiveFormsModule,
    MatExpansionModule,
    MatCardModule,
    MatMenuModule,
    MatDividerModule,
    MatNativeDateModule,
    MatTabsModule,
    MatChipsModule,
  ],
  bootstrap: [AppComponent],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
})
export class AppModule { }
