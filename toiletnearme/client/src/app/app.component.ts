import { Component, OnInit, inject } from '@angular/core';
import { Loader } from "@googlemaps/js-api-loader"
import { GoogleMapsConfigService } from './services/googlemapsconfig.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

  constructor() { 
  }
  
  ngOnInit(): void {
  }
  
}
