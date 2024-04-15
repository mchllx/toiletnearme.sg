import { Component, ElementRef, Inject, OnInit, ViewChild, inject } from '@angular/core';
import { GoogleMap } from '@angular/google-maps';
import { GoogleMapsConfigService } from '../services/googlemapsconfig.service';
import { ToiletService } from '../services/toilet.service';

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

  map!: google.maps.Map
  marker!: google.maps.Marker
  geocoder!: google.maps.Geocoder
  geocoderRequest!: google.maps.GeocoderRequest
  geocoderStatus!: google.maps.GeocoderStatus
  geocoderResults!: google.maps.GeocoderResult
  responseDiv!: HTMLDivElement
  response!: HTMLPreElement

  width: number= 1280
  height: number= 720

  address$!: Promise<string>
  addressList!: string[]
  address!: string

  options: google.maps.MapOptions = {
    center: {lat: 1.3191389705135221, lng: 103.89404363104732},
    zoom: 12,
    mapTypeControl: false
  };

  // private googleMapSvc = inject(GoogleMapsConfigService)
  private toiletSvc = inject(ToiletService)

  ngOnInit(): void {
    this.getAddress()

    for (let index = 0; index < this.addressList.length; index++) {
      this.address = this.addressList[index];
      this.loadGeocode(this.address)
    } 
    // this.JWTToken = this.springSvc.getJWTToken();
    
  }

  getAddress() {
    address$: this.toiletSvc.getGoogleMapAddress()
      .then(value => {
        console.log('awaiting response from server')
        this.addressList = value 
      })
      .catch(err => console.error(err))
  }

  loadGeocode(address: string) {
    this.geocoderRequest = {
      address: address,
      region: "SG"
    }

    this.geocoder = new google.maps.Geocoder()
    this.geocoder.geocode(this.geocoderRequest)
    console.log(this.geocoder)
  }

}
