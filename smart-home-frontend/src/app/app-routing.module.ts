import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthorizedGuard } from './guards/authorized/authorized.guard';
import { LoginGuard } from './guards/login/login.guard';
import { LoginComponent } from './pages/login/login.component';
import { MyObjectsComponent } from './pages/my-objects/my-objects.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent, pathMatch: 'full', canActivate: [LoginGuard] },
  { path: '', component: MyObjectsComponent, pathMatch: 'full', canActivate: [AuthorizedGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
