import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthorizedGuard } from './guards/authorized/authorized.guard';
import { LoginGuard } from './guards/login/login.guard';
import { LoginComponent } from './pages/login/login.component';
import { MessageViewComponent } from './pages/message-view/message-view.component';
import { MyObjectsComponent } from './pages/my-objects/my-objects.component';
import { ReportViewComponent } from './pages/report-view/report-view.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full',
    canActivate: [LoginGuard],
  },
  {
    path: '',
    component: MyObjectsComponent,
    pathMatch: 'full',
    canActivate: [AuthorizedGuard],
  },
  {
    path: 'real-estates/:id/messages',
    component: MessageViewComponent,
    canActivate: [AuthorizedGuard]
  },
  {
    path: 'report',
    component: ReportViewComponent,
    canActivate: [AuthorizedGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
