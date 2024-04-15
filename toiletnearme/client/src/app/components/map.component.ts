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
  geocoderResults!: google.maps.GeocoderResult[]
  responseDiv!: HTMLDivElement
  response!: HTMLPreElement

  width: number= 1280
  height: number= 720

  address$!: Promise<string[]>
  addressList: string[] = []
  address!: string

  options: google.maps.MapOptions = {
    center: {lat: 1.3191389705135221, lng: 103.89404363104732},
    zoom: 12,
    mapTypeControl: false
  };

  private toiletSvc = inject(ToiletService)

  ngOnInit(): void {
    this.addressList = this.getAddress()
    console.log(">>> addresses:", this.addressList)

    for (let i = 0; i < this.addressList.length; i++) {
      this.loadGeocode(this.addressList[i])
    }  
  }

  getAddress(): string[] {
    address$: this.toiletSvc.getGoogleMapAddress()
      .then(value => {
        console.log('awaiting response from server')
        // console.log(">>> value:", value)
        for (let i = 0; i < value.length; i++) {
          this.addressList.push(value[i]) 
        }
      })
      .catch(err => console.error(err))
      return this.addressList
  }

  loadGeocode(address: string) {
    this.geocoderRequest = {
      address: address,
      region: "SG"
    }

  this.geocoder = new google.maps.Geocoder()
  this.geocoder.geocode(this.geocoderRequest, 
    (results: google.maps.GeocoderResult[] | null, status: google.maps.GeocoderStatus) => {
    if (this.geocoderStatus === google.maps.GeocoderStatus.OK) {
      this.map.setCenter(this.geocoderResults[0].geometry.location);

      var marker = new google.maps.Marker({
        map: this.map,
        position: this.geocoderResults[0].geometry.location
      })
      console.log(marker)

    } else {
      alert('Geocode was not successful for the following reason: ' + this.geocoderResults);
      }
    })
  }
}
