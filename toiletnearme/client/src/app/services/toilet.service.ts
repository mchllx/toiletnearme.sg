import {Injectable, inject} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable, lastValueFrom} from "rxjs";
import {} from "../models";


import { environment } from '../../environments/environment'

const URL = environment.url

@Injectable()
export class ToiletService {

  private http = inject(HttpClient)

  // getProductCategories(): Observable<string[]> {
  //   return this.http.get<string[]>('/api/categories')
  // }

  // getProductsByCategory(category: string): Observable<Product[]> {
  //   return this.http.get<Product[]>(`/api/category/${category}`)
  // }

  //   // POST http://localhost:8080/api/order
  // checkout(order: Order): Promise<Order> {
  //   return lastValueFrom(this.http.post<Order>(`${URL}/api/order`, {order}))
  // }

  // GET http://localhost:8080/api/toilet/address
  constructor(http: HttpClient) {}
  getGoogleMapAddress(): Promise<string[]> { 
    const API_KEY_ENDPOINT = 'api/toilet/address'
    try {
      return lastValueFrom(this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {}))
    } catch (error) {
      console.error('Error fetching address', error)
      throw error
    }
  }

  // GET http://localhost:8080/api/toilet/address/{region}")
  getGoogleMapAddressByRegion(region: string): Promise<string[]> {

    const params = new HttpParams()
      .set('region', region)

    console.info(params)
    
    const API_KEY_ENDPOINT = 'api/toilet/address'
    try {
      return lastValueFrom(this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {params}))
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }
  
}