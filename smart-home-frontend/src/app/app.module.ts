import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { LoginComponent } from './pages/login/login.component';
import { MyObjectsComponent } from './pages/my-objects/my-objects.component';
import { MaterialModule } from './material.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { MessageViewComponent } from './pages/message-view/message-view.component';
import { SuchEmptyComponent } from './components/such-empty/such-empty.component';
import { ReportViewComponent } from './pages/report-view/report-view.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MyObjectsComponent,
    ToolbarComponent,
    SuchEmptyComponent,
    MessageViewComponent,
    ReportViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MaterialModule
  ],
  bootstrap: [AppComponent],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
})
export class AppModule { }
