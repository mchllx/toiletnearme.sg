import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FullComponent } from './layouts/full/full.component';
import { MapComponent } from './components/google-map/map.component';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  { path: '',
    component: FullComponent,
    children: [
      {path:"", redirectTo:"/home", pathMatch:"full"},
      {path:"home", component:MapComponent},
      {path:"dashboard", component:DashboardComponent}
    ]
   },
  { path: "", redirectTo: '/', pathMatch:'full' },
  { path: '**', redirectTo: '/', pathMatch:'full' }
]

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })]
  , exports: [RouterModule]
})

export class AppRoutingModule { }
