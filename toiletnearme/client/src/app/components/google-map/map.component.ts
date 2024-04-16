import { AfterContentInit, AfterViewInit, Component, ElementRef, Inject, OnInit, ViewChild, inject } from '@angular/core';
import { GoogleMap, MapAdvancedMarker, MapInfoWindow, MapMarker } from '@angular/google-maps';
import { sgLocations } from '../../models';
import { ToiletService } from '../../services/toilet.service';

import {OverlayLayout} from '@googlemaps/extended-component-library/overlay_layout.js';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
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

  width: number= 1040
  height: number= 720

  address$!: Promise<string[]>
  addressList: string[] = []
  address!: string

  private toiletSvc = inject(ToiletService)
  sgLocations: sgLocations[] = [{ content: "", title: "Singpost Centre", lat: 1.3191389705135221, lng: 103.89404363104732 }]

  svgString: string = `<svg aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="#FF5733" stroke="#FFFFFF" viewBox="0 0 24 24">
    <circle cx="12" cy="12" r="10" fill="#FF5733" stroke="#FFFFFF" stroke-width="2" />
    <path d="M12 6c-1.656 0-3 1.344-3 3v6c0 1.656 1.344 3 3 3s3-1.344 3-3v-6c0-1.656-1.344-3-3-3zm1.5 9.75c0 .414-.336.75-.75.75s-.75-.336-.75-.75v-3c0-.414.336-.75.75-.75s.75.336.75.75v3zm-3-9h1.5v-1.5c0-.414-.336-.75-.75-.75s-.75.336-.75.75v1.5z" fill="#FFFFFF" />
    </svg>`

    // info marker
  @ViewChild(MapInfoWindow) infoWindow!: MapInfoWindow;

  @ViewChild('overlay') overlay!: ElementRef<OverlayLayout>;

  constructor() {
    this.mapOptions = {
      center: { lat: 1.3191389705135221, lng: 103.89404363104732 },
      zoom: 12,
      mapId: 'fc1289eedacfb1a1'
    }

    this.addressList = this.getAddress()
  }

  ngOnInit(): void {
    const parser = new DOMParser()

    this.sgLocations.forEach((location) => {
      location.content = parser.parseFromString(this.svgString, "image/svg+xml").documentElement
    })
    // console.log(">>updated:",this.sgLocations)
  }

  onMarkerClick(marker: MapAdvancedMarker) {
    // console.log(">>>advmarker:", marker.advancedMarker)
    // console.log(">>>title:", marker.advancedMarker.title)
    this.infoWindow.openAdvancedMarkerElement(marker.advancedMarker, marker.advancedMarker.title);
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
            lng: lng,
            content: new DOMParser().parseFromString(this.svgString, "image/svg+xml").documentElement
          })
        // console.log(this.sgLocations)

      } else {
        console.log('Geocode was not successful for the following reason: ' + results)
        // alert('Geocode was not successful for the following reason: ' + this.geocoderResults);
        }
      })
    }
}
