import { AfterContentInit, AfterViewInit, Component, ElementRef, Inject, OnInit, ViewChild, inject } from '@angular/core';
import { GoogleMap, MapAdvancedMarker, MapInfoWindow, MapMarker } from '@angular/google-maps';
import { Marker, Toilet } from '../../models';
import { ToiletService } from '../../services/toilet.service';

import {OverlayLayout} from '@googlemaps/extended-component-library/overlay_layout.js';
import { Observable, of, switchMap, tap } from 'rxjs';
import { ToiletStore } from 'src/app/toilet.store';
import { ActivatedRoute } from '@angular/router';
import { MarkerService } from 'src/app/services/marker.service';
import { AuthService } from 'src/app/services/auth.service';
import { UserStore } from 'src/app/user.store';

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

  width: number= 250
  height: number= 500

  address$!: Promise<string[]>
  addressList: string[] = []
  address!: string

  toilet: Toilet[] = []
  toilet$!:Observable<Toilet[]>
  displayToilet: Toilet[] = []

  marker!: Marker[]

  private activatedRoute = inject(ActivatedRoute)
  private toiletSvc = inject(ToiletService)
  private toiletStore = inject(ToiletStore)
  private markerSvc = inject(MarkerService)
  private authSvc = inject(AuthService)
  private userStore = inject(UserStore)

  sgLocations: Marker[] = [{id:"test", lat: 1.3191389705135221, lng: 103.89404363104732, title: "Singpost Centre", content:""}]

  svgString: string = `<svg aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="#FF5733" stroke="#FFFFFF" viewBox="0 0 24 24">
    <circle cx="12" cy="12" r="10" fill="#FF5733" stroke="#FFFFFF" stroke-width="2" />
    <path d="M12 6c-1.656 0-3 1.344-3 3v6c0 1.656 1.344 3 3 3s3-1.344 3-3v-6c0-1.656-1.344-3-3-3zm1.5 9.75c0 .414-.336.75-.75.75s-.75-.336-.75-.75v-3c0-.414.336-.75.75-.75s.75.336.75.75v3zm-3-9h1.5v-1.5c0-.414-.336-.75-.75-.75s-.75.336-.75.75v1.5z" fill="#FFFFFF" />
    </svg>`

    // info marker
  @ViewChild(MapInfoWindow) infoWindow!: MapInfoWindow;

  constructor() {
    this.mapOptions = {
      center: { lat: 1.3191389705135221, lng: 103.89404363104732 },
      zoom: 12,
      mapId: 'fc1289eedacfb1a1'
    }
  }

  ngOnInit(): void {
    this.toiletSvc.getGoogleMapToilets().subscribe(
      (value: Toilet[]) => {
        if (value === undefined) {
          return;
        } else {
          for (let i = 0; i < value.length; i++) {
            const toilet = value[i];
            // console.info('>>>retrieving toilets', toilet);
            this.toilet.push(toilet);
            this.loadGeocode(this.toilet[i].address, i.toString())
          }
        }
      },
      (error) => {
        console.error('Error fetching toilets:', error);
      }
    )

    const parser = new DOMParser()

    this.sgLocations.forEach((location) => {
      location.content = parser.parseFromString(this.svgString, "image/svg+xml").documentElement
    })

    this.activatedRoute.params.subscribe(params => {
      this.address = params['title'] 
    })
  }

  onMarkerClick(marker: MapAdvancedMarker) {
    // console.log(">>>advmarker:", marker.advancedMarker)
    // console.log(">>>title:", marker.advancedMarker.title)
    this.infoWindow.openAdvancedMarkerElement(marker.advancedMarker, marker.advancedMarker.title)

    console.log(">>>mark:", marker.advancedMarker.title)
    this.marker.push({
      id: marker.advancedMarker.id
      , title: marker.advancedMarker.title
      , lat: marker.advancedMarker.position?.lat
      , lng: marker.advancedMarker.position?.lng
      , content: marker.advancedMarker.content
    })

    this.findToiletByAddress(marker.advancedMarker.title)
  }

  findToiletByAddress(address: string) {
    for (let index = 0; index < this.toilet.length; index++) {
      const toilet = this.toilet[index];

      if (toilet.address === address) {
        console.log(toilet)
        this.displayToilet.pop()
        this.displayToilet.push(toilet)
      } 
    }
  }

  save() {
    console.log('>>>saving to mongodb')
    this.userStore.getUser.subscribe(user => { 
      const token = user.jwtToken

      for (let index = 0; index < this.marker.length; index++) {
        const marker = this.marker[index]
        this.markerSvc.postMarker(marker, token)
        .then(
          value => {
            console.log('awaiting response from server')
    
            alert(`successful: ${value}`); 
            console.log('>>>ang: successful', value)
          })
          .catch(
            error => {
              console.log('server error', error)
              alert('server error: failed to save marker') 
            }
          )
      }
    }) 
  }

  delete() {
    console.log('>>>saving to mongodb')
    this.userStore.getUser.subscribe(user => { 
      const token = user.jwtToken

      for (let index = 0; index < this.marker.length; index++) {
        const marker = this.marker[index]
        this.markerSvc.deleteMarkerById(marker.id, token)
        .then(
          value => {
            console.log('awaiting response from server')
    
            alert(`successful: ${value}`); 
            console.log('>>>ang: successful', value)
          })
          .catch(
            error => {
              console.log('server error', error)
              alert('server error: failed to save marker') 
            }
          )
      }
    }) 
  }

  loadGeocode(address: string, count: string) {
    // console.log('>>>requesting google address')
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
            id: count,
            title:results[0].formatted_address,
            lat: lat,
            lng: lng,
            content: new DOMParser().parseFromString(this.svgString, "image/svg+xml").documentElement
          })
        // console.log(this.sgLocations)

      } else {
        console.log('Geocode was not successful for the following reason: ' + results)
        }
      })
    }

    // getAddress(): string[] {
    //   address$: this.toiletSvc.getGoogleMapAddress()
    //     .then(value => {
    //       // console.log('awaiting response from server')
    //       // console.log(">>> value:", value)
    //       for (let i = 0; i < value.length; i++) {
    //         this.loadGeocode(value[i]) 
    //       }
    //     })
    //     .catch(err => console.error(err))
    //     return this.addressList
    // }
}
