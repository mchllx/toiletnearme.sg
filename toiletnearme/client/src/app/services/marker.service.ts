import {Injectable, inject} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable, lastValueFrom} from "rxjs";
import { Marker, Toilet } from "../models";


import { environment } from '../../environments/environment'

const URL = environment.url
const API_KEY_ENDPOINT = 'api/marker/'

@Injectable()
export class MarkerService {

  private http = inject(HttpClient)

  // GET http://localhost:8080/api/marker
  constructor(http: HttpClient) {}
  getMarkers(): Promise<Marker[]> { 
    try {
      return lastValueFrom(this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {}))
    } catch (error) {
      console.error('Error fetching address', error)
      throw error
    }
  }

  // GET http://localhost:8080/api/marker/{id}")
  getGoogleMapAddressByGender(id: string): Observable<Marker[]> {
    const params = new HttpParams()
    .set('id', id)

    try {
      return this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {params})
    } catch (error) {
      console.error('Error fetching address', error)
      throw error
    }
  }

  // POST http://localhost:8080/api/marker/{marker}")
  postMarker(marker: Marker, jwtToken: string): Promise<Marker> {
    const headers = new HttpHeaders()
    .set('Authorization', 'Bearer '.concat(jwtToken)) 

    try {
      return lastValueFrom(this.http.post<Marker>(`${URL}/${API_KEY_ENDPOINT}`, {marker}, {headers}))
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }

  // DELETE http://localhost:8080/api/marker/{id}")
  deleteMarkerById(id: string, jwtToken: string): Promise<Marker> {
    const headers = new HttpHeaders()
    // .set('Authorization', 'Bearer '.concat(jwtToken))
    
    try {
      return lastValueFrom(this.http.delete<Marker>(`${URL}/${API_KEY_ENDPOINT}+${id}`, {headers}))
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }
  
}