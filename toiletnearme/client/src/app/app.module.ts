import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { MapComponent } from './components/map.component';
import { SpringService } from './services/spring.service';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { registerLocaleData } from '@angular/common';
import { MaterialModule } from './ui/material.module';
import { AgmCoreModule, LAZY_MAPS_API_CONFIG, LazyMapsAPILoaderConfigLiteral } from '@agm/core';

import en from '@angular/common/locales/en';
import { GoogleMapsInitializer } from './services/googlemapsinitialiser.service';
import { GoogleMapsModule } from '@angular/google-maps'
import { AppRoutingModule } from './app-routing.module';
import { GoogleMapsConfigService } from './services/googlemapsconfig.service';


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
    MapComponent
  ],

  // AGMCore clashes with router, core@1 fixes, but invalidates router module
  imports: [
    BrowserModule, HttpClientModule, ReactiveFormsModule, MaterialModule
    , GoogleMapsModule
    , AppRoutingModule
    // , AgmCoreModule.forRoot({apiKey: ''})
  ],

  providers: [
    SpringService, GoogleMapsConfigService, provideAnimationsAsync(), provideHttpClient(),
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
