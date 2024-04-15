import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AuthService } from './services/auth.service';
import { registerLocaleData } from '@angular/common';
import { MaterialModule } from './ui/material.module';

import en from '@angular/common/locales/en';
import { GoogleMapsModule } from '@angular/google-maps'
import { AppRoutingModule } from './app-routing.module';
import { GoogleMapsConfigService } from './services/googlemapsconfig.service';
import { ToiletService } from './services/toilet.service';
import { FeatherModule } from 'angular-feather';
import { allIcons } from 'angular-feather/icons';
import { FullComponent } from './layouts/full/full.component';
import { MapComponent } from './components/google-map/map.component';
import { ComponentsModule } from './components/components.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


registerLocaleData(en);

// export function initializeApp(googleConfigSvc: GoogleMapsConfigService) {
//   console.info(">>>initialising")
//   return () => googleConfigSvc.getGoogleMapsApiKey().then(apiKey => {
//     (window as any).apiKey = apiKey;
//   });
// }

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    FullComponent,
  ],

  // AGMCore clashes with router, core@1 fixes, but invalidates router module
  imports: [
    BrowserModule, HttpClientModule, BrowserAnimationsModule, ReactiveFormsModule, MaterialModule, DashboardModule
    , GoogleMapsModule, AppRoutingModule, ComponentsModule, FeatherModule.pick(allIcons),
    // , AgmCoreModule.forRoot({apiKey: ''})
  ],

  providers: [
    AuthService, ToiletService, GoogleMapsConfigService, provideHttpClient(),
    // {
    //   // APP_INITIALIZER is the Angular dependency injection token.
    //   provide: APP_INITIALIZER,
    //   // Pass in the AGM dependency injection token.
    //   // deps: [LAZY_MAPS_API_CONFIG, GoogleMapsInitializer],
    //   deps: [GoogleMapsConfigService],
    //   // Allow for multiple startup injectors if needed.
    //   multi: true,
    //   // UseFactory provides Angular with the function to invoke.
    //   useFactory: initializeApp
    //     // config: LazyMapsAPILoaderConfigLiteral
    //     // , initializer: GoogleMapsInitializer) => () => initializer.initialize(config),
    // }
  ],
  bootstrap: [ AppComponent ]
  
})

export class AppModule { }
