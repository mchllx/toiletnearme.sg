import { AfterViewInit, Component, ElementRef, Inject, OnInit, ViewChild, inject } from '@angular/core';
import { GoogleMap, MapAdvancedMarker, MapMarker } from '@angular/google-maps';
import { sgLocations } from '../models';
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

  geocoder!: google.maps.Geocoder
  geocoderRequest: google.maps.GeocoderRequest = {}
  responseDiv!: HTMLDivElement
  response!: HTMLPreElement
  mapOptions!: google.maps.MapOptions
  mapDiv!: HTMLDivElement
  map!: google.maps.Map

  width: number= 1280
  height: number= 720

  address$!: Promise<string[]>
  addressList: string[] = []
  address!: string

  private toiletSvc = inject(ToiletService)
  sgLocations: sgLocations[] = [{ title: "Singpost Centre", lat: 1.3191389705135221, lng: 103.89404363104732 }]

  constructor() {
    this.mapOptions = {
      center: { lat: 1.3191389705135221, lng: 103.89404363104732 },
      zoom: 12,
      mapId: 'fc1289eedacfb1a1'
    }
  }

  ngOnInit(): void {
    // console.log(">>> addresses:", this.addressList)
    this.addressList = this.getAddress()
  }

  getAddress(): string[] {
    address$: this.toiletSvc.getGoogleMapAddress()
      .then(value => {
        console.log('awaiting response from server')
        // console.log(">>> value:", value)
        for (let i = 0; i < value.length; i++) {
          this.loadGeocode(value[i]) 
        }
      })
      .catch(err => console.error(err))
      return this.addressList
  }

  loadGeocode(address: string) {
    console.log('>>>requesting google address')
    this.geocoderRequest = {
      address: address
    }

    this.geocoder = new google.maps.Geocoder()
    this.geocoder.geocode(this.geocoderRequest, 
      (results: google.maps.GeocoderResult[] | null, status: google.maps.GeocoderStatus) => {
      if (status === google.maps.GeocoderStatus.OK && results && results.length > 0) {
        var lat = results[0].geometry.location.lat()
        var lng = results[0].geometry.location.lng()
        this.sgLocations.push(
          {
            title:results[0].formatted_address,
            lat: lat,
            lng: lng
          })
        // console.log(this.sgLocations)

      } else {
        console.log('Geocode was not successful for the following reason: ' + results)
        // alert('Geocode was not successful for the following reason: ' + this.geocoderResults);
        }
      })
    }
}
