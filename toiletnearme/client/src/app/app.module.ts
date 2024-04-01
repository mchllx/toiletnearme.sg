import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {RouterModule, Routes} from '@angular/router';

import {HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';

import { AppComponent } from './app.component';
import { ProductService } from './product.service';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MaterialModule } from './material/material/material.module';
import { CartStore } from './toilet.store';

// NOTE: you are free to modify this file

const appRoutes: Routes = [
  // { path: '', component: MainComponent },
  { path: '**', redirectTo: '/', pathMatch:'full' }
]

@NgModule({
  declarations: [
    AppComponent],

  imports: [
    BrowserModule, HttpClientModule, ReactiveFormsModule, MaterialModule,
    RouterModule.forRoot(appRoutes, { useHash: true })
  ],
  providers: [ ProductService, CartStore, provideAnimationsAsync() ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
