import {Injectable, inject} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable, lastValueFrom} from "rxjs";
import { Toilet } from "../models";


import { environment } from '../../environments/environment'

const URL = environment.url
const API_KEY_ENDPOINT = 'api/toilet/'

@Injectable()
export class ToiletService {

  private http = inject(HttpClient)

  // GET http://localhost:8080/api/toilet
  constructor(http: HttpClient) {}
  getGoogleMapToilets(): Observable<Toilet[]> { 
    try {
      return this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {})
    } catch (error) {
      console.error('Error fetching address', error)
      throw error
    }
  }

    // GET http://localhost:8080/api/toilet/address
  getGoogleMapAddress(): Promise<string[]> { 
    try {
      return lastValueFrom(this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`.concat("address"), {}))
    } catch (error) {
      console.error('Error fetching address', error)
      throw error
    }
  }

  // GET http://localhost:8080/api/toilet/address/{type}")
  getGoogleMapAddressByGender(gender: string): Observable<Toilet[]> {
    const params = new HttpParams()
    .set('gender', gender)

    try {
      return this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`.concat("address"), {params})
    } catch (error) {
      console.error('Error fetching address', error)
      throw error
    }
  }

  // GET http://localhost:8080/api/toilet/address/{region}")
  getGoogleMapAddressByRegion(region: string): Observable<Toilet[]> {
    const params = new HttpParams()
      .set('region', region)

    try {
      return this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`.concat("address"), {params})
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }

  // POST http://localhost:8080/api/toilet/{toilet}")
  postToilet(toilet: Toilet, jwtToken: string): Promise<Toilet> {
    const headers = new HttpHeaders()
    .set('Authorization', 'Bearer '.concat(jwtToken)) 

    try {
      return lastValueFrom(this.http.post<Toilet>(`${URL}/${API_KEY_ENDPOINT}`, {toilet}, {headers}))
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }

  // DELETE http://localhost:8080/api/toilet/{id}")
  deleteToiletById(id: string, jwtToken: string): Promise<Toilet> {
    const headers = new HttpHeaders()
    .set('Authorization', 'Bearer '.concat(jwtToken))
    
    const params = new HttpParams()
      .set('id', id)
    
    try {
      return lastValueFrom(this.http.delete<Toilet>(`${URL}/${API_KEY_ENDPOINT}+${params}`, {headers}))
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }
  
}