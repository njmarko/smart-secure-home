import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { MyObjectsComponent } from './pages/my-objects/my-objects.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: MyObjectsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
