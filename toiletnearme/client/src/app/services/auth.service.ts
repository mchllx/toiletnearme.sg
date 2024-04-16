import {Injectable, inject} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, lastValueFrom} from "rxjs";
import { User } from "../models";


import { environment } from '../../environments/environment'

const URL = environment.url

@Injectable()
export class AuthService {

  private http = inject(HttpClient)

  // GET http://localhost:8080/api/authorization/jwt
  constructor(http: HttpClient) {}
  async getJWTToken(): Promise<string> {

    const headers = new HttpHeaders()
      .set('Authorization', 'Bearer ')

    console.info(headers)
    
    const KEY_ENDPOINT = 'api/authorization/jwt'

    try {
      const response = await lastValueFrom(this.http.get<any>(`${URL}/${KEY_ENDPOINT}`, {headers}))
      return response.apikey as string
    } catch (error) {
      console.error('Error fetching JWT Token:', error)
      return ''
    }
  }

  // POST http://localhost:8080/api/register/address/{toilet}")
  postUser(user: User): Promise<User> {
    const headers = new HttpHeaders()
    .set('Authorization', 'Bearer '.concat())
    
    const API_KEY_ENDPOINT = 'register'

    try {
      return lastValueFrom(this.http.post<User>(`${URL}/${API_KEY_ENDPOINT}`, {user}, {headers}))
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }
  
}

