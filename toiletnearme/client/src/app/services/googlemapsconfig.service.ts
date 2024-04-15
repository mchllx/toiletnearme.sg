import {Injectable, inject} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable, lastValueFrom} from "rxjs";
import {} from "../models";

import { environment } from '../../environments/environment'

const URL = environment.url

@Injectable()
export class GoogleMapsConfigService {

  private http = inject(HttpClient)

  // GET http://localhost:8080/api/gmap/key
  constructor(http: HttpClient) {}
  async getGoogleMapsApiKey(): Promise<string> {

    const headers = new HttpHeaders()
      .set('Authorization', 'Bearer ')
    
    const API_KEY_ENDPOINT = 'api/gmap/key'
    try {
      const response = await lastValueFrom(this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {}))
      return response.apiKey as string
    } catch (error) {
      console.error('Error fetching Google Maps API key:', error)
      return ''
    }
  }
 
}