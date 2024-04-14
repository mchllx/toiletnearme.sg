import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MapComponent } from './components/map.component';

const routes: Routes = [
  { path: '', component: MapComponent },
  { path: '**', redirectTo: '/', pathMatch:'full' }
]

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })]
  ,exports: [RouterModule]
})
export class AppRoutingModule { }
