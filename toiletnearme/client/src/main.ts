import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';
import { GoogleMapsConfigService } from './app/services/googlemapsconfig.service';

// declare const window: any;

platformBrowserDynamic().bootstrapModule(AppModule)
  // const would not display dynamically, restrict API usage instd
  // .then((ref) => {
  //   const googleMapSvc = ref.injector.get(GoogleMapsConfigService);
  //     const gmapAPIKey = googleMapSvc.getGoogleMapsApiKey()
  //       gmapAPIKey.then(
  //         value => {
  //           console.log('awaiting response from server')
  //           window.apiKey = value
      
  //           // const mapURL =
  //           // "https://maps.googleapis.com/maps/api/js?key="
  //           // .concat(apiKey)
  //           // .concat("&libraries=visualization")

  //         })
  // })
  .catch(err => console.error(err))