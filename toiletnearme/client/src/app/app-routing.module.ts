import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FullComponent } from './layouts/full/full.component';
import { MapComponent } from './components/google-map/map.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ExtendedComponent } from './components/extended/extended.component';
import { FormsComponent } from './components/forms/forms.component';
import { ContactComponent } from './components/contact/contact.component';

const routes: Routes = [
  { path: '',
    component: FullComponent,
    children: [
      {path:"", redirectTo:"/home", pathMatch:"full"},
      {path:"home", component:ExtendedComponent},
      {path:"toilets", component:MapComponent},
      {path:"login", component:FormsComponent},
      {path:"contact", component:ContactComponent},
      {path:"community", component:DashboardComponent}
    ]
   },
  { path: "", redirectTo: '/home', pathMatch:'full' },
  { path: '**', redirectTo: '/home', pathMatch:'full' }
]

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })]
  , exports: [RouterModule]
})

export class AppRoutingModule { }
