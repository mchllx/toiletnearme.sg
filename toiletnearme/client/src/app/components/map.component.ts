import { Component, ElementRef, Inject, OnInit, ViewChild, inject } from '@angular/core';
import { GoogleMap } from '@angular/google-maps';
import { GoogleMapsConfigService } from '../services/googlemapsconfig.service';
import { SpringService } from '../services/spring.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrl: './map.component.css'
})

export class MapComponent implements OnInit {

  // JWTToken!: Promise<string>
  gmapAPIKeyPromise!: Promise<string>
  gmapAPIKey:string = ""
  mapURL!:string

  // private googleMapSvc = inject(GoogleMapsConfigService)
  // private springSvc = inject(SpringService)

  options: google.maps.MapOptions = {
    center: {lat: 1.3863827334246217, lng: 103.74673591332547},
    zoom: 4
  };

  ngOnInit(): void {
    // this.JWTToken = this.springSvc.getJWTToken();

    // google namespace script needs to be in index.html
    //   this.gmapAPIKeyPromise = this.googleMapSvc.getGoogleMapsApiKey();
    //   this.gmapAPIKeyPromise.then(
    //     value => {
    //       console.log('awaiting response from server')
    //       const YOUR_API_KEY = value
    //       this.gmapAPIKey = YOUR_API_KEY
          
    //       this.mapURL=
    //       "https://maps.googleapis.com/maps/api/js?key="
    //       .concat(YOUR_API_KEY)
    //       .concat("&libraries=visualization")
          
    //       console.log('displaying map', this.mapURL)
    //     })
    // }
  }
}
