import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
  { 
    path: '', 
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'home', 
    component: HomeComponent, 
    canActivate: [AuthGuard]
  },
  { 
    path: 'profile/:profileId', 
    component: ProfileComponent,
    canActivate: [AuthGuard]
  },
  { path: 'login', component: LoginComponent},
  { path: 'logout', component: LogoutComponent },
  { path: '**', component: PagenotfoundComponent },  // Wildcard route for a 404 page
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
