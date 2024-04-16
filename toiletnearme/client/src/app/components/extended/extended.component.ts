/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */

import '@googlemaps/extended-component-library/place_building_blocks/place_directions_button.js';
import '@googlemaps/extended-component-library/api_loader.js';
import '@googlemaps/extended-component-library/split_layout.js';
import '@googlemaps/extended-component-library/overlay_layout.js';
import '@googlemaps/extended-component-library/icon_button.js';
import '@googlemaps/extended-component-library/place_building_blocks/place_directions_button.js';
import '@googlemaps/extended-component-library/place_building_blocks/place_data_provider.js';
import '@googlemaps/extended-component-library/place_picker.js';
import '@googlemaps/extended-component-library/place_overview.js';

import {Directive, Component, ElementRef, OnInit, ViewChild, inject, AfterViewInit} from '@angular/core';
import {OverlayLayout} from '@googlemaps/extended-component-library/overlay_layout.js';
import {PlacePicker} from '@googlemaps/extended-component-library/place_picker.js';
import { ToiletService } from 'src/app/services/toilet.service';
import { Marker } from 'src/app/models';
import { MapAdvancedMarker, MapInfoWindow } from '@angular/google-maps';
import { AdvancedMarkerElement } from 'node_modules/@googlemaps/extended-component-library/lib/utils/googlemaps_types';
import { ActivatedRoute, Router } from '@angular/router';

const DEFAULT_CENTER = {
  lat: 1.4093769844197537,
  lng: 103.7935080799274
};

const DEFAULT_ZOOM = 12;
const DEFAULT_ZOOM_WITH_LOCATION = 16;

@Component({
  selector: 'app-root',
  templateUrl: './extended.component.html',
  styleUrls: ['./extended.component.css']
})

export class ExtendedComponent implements OnInit{

  geocoder!: google.maps.Geocoder
  geocoderRequest: google.maps.GeocoderRequest = {}
  responseDiv!: HTMLDivElement
  response!: HTMLPreElement
  mapOptions!: google.maps.MapOptions
  mapDiv!: HTMLDivElement
  map!: google.maps.Map

  readonly mapsApiKey = import.meta.env.NG_APP_MAPS_API_KEY;

  width: number= 1040
  height: number= 720

  address$!: Promise<string[]>
  addressList: string[] = []
  address!: string

  // TODO: revert to google.maps.places.Place when Maps JS typings updated.
  place?: any  // google.maps.places.Place;
  markerElem?: any

  private toiletSvc = inject(ToiletService)
  private activatedRoute = inject(ActivatedRoute)
  private router = inject(Router)

  sgLocations: Marker[] = [{ content: "", title: "Singpost Centre", lat: 1.3191389705135221, lng: 103.89404363104732 }]

  svgString: string = `<svg aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="#FF5733" stroke="#FFFFFF" viewBox="0 0 24 24">
    <circle cx="12" cy="12" r="10" fill="#FF5733" stroke="#FFFFFF" stroke-width="2" />
    <path d="M12 6c-1.656 0-3 1.344-3 3v6c0 1.656 1.344 3 3 3s3-1.344 3-3v-6c0-1.656-1.344-3-3-3zm1.5 9.75c0 .414-.336.75-.75.75s-.75-.336-.75-.75v-3c0-.414.336-.75.75-.75s.75.336.75.75v3zm-3-9h1.5v-1.5c0-.414-.336-.75-.75-.75s-.75.336-.75.75v1.5z" fill="#FFFFFF" />
    </svg>`

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

  onMarkerClick(location: string) {
    console.log(">>click:", location)

    this.router.navigate(['/toilets'])
    // this.infoWindow.position = { lat: location.lat, lng: location.lng }
    // this.infoWindow = location.content
    // this.infoWindow.open()
    // this.infoWindow.openAdvancedMarkerElement(location.content, location.title);
    // this.infoWindow.openAdvancedMarkerElement(this.marker.advancedMarker, this.marker.advancedMarker.title);
  }

  getAddress(): string[] {
    address$: this.toiletSvc.getGoogleMapAddress()
      .then(value => {
        // console.log('awaiting response from server')
        // console.log(">>> value:", value)
        for (let i = 0; i < value.length; i++) {
          this.loadGeocode(value[i]) 
        }
      })
      .catch(err => console.error(err))
      return this.addressList
  }

  loadGeocode(address: string) {
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

  get mapCenter() {
    return this.place?.location ?? DEFAULT_CENTER;
  }

  get mapZoom() {
    return this.place ? DEFAULT_ZOOM_WITH_LOCATION : DEFAULT_ZOOM;
  }

  selectPlace(e: Event) {
    this.place = (e.target as PlacePicker)?.value ?? undefined;
  }

  showReviews() {
    this.overlay.nativeElement.showOverlay();
  }

  hideReviews() {
    this.overlay.nativeElement.hideOverlay();
  }
}