import {Injectable, inject} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, lastValueFrom} from "rxjs";
import {} from "../models";


import { environment } from '../../environments/environment'

const URL = environment.url

@Injectable()
export class SpringService {

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

  // GET http://localhost:8080/api/authorization/jwt
  constructor(http: HttpClient) {}
  async getJWTToken(): Promise<string> {

    const headers = new HttpHeaders()
      .set('Authorization', 'Bearer ')

    console.info(headers)
    
    const API_KEY_ENDPOINT = 'api/authorization/jwt'
    try {
      const response = await lastValueFrom(this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {headers}))
      return response.apikey as string
    } catch (error) {
      console.error('Error fetching JWT Token:', error)
      return ''
    }
  }
  
}

