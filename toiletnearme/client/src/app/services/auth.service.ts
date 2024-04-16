import {Injectable, inject} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, lastValueFrom} from "rxjs";
import { User } from "../models";


import { environment } from '../../environments/environment'

const URL = environment.url
const API_KEY_ENDPOINT = 'api/jwt'

@Injectable()
export class AuthService {

  private http = inject(HttpClient)

  // GET http://localhost:8080/api/jwt
  constructor(http: HttpClient) {}
  async getJWTToken(): Promise<string> {

    const headers = new HttpHeaders()
      .set('Authorization', 'Bearer ')

    console.info(headers)

    try {
      const response = await lastValueFrom(this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {headers}))
      return response.apikey as string
    } catch (error) {
      console.error('Error fetching JWT Token:', error)
      return ''
    }
  }

  // POST http://localhost:8080/api/jwt/login/")
  postLogin(user: User): Promise<User> {
    const RESOURCE = 'login'

    try {
      return lastValueFrom(this.http.post<User>(`${URL}/${API_KEY_ENDPOINT}/${RESOURCE}`, {user}))
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }

  // POST http://localhost:8080/api/jwt/register/")
  postRegister(user: User): Promise<User> {
    const RESOURCE = 'register'

    try {
      return lastValueFrom(this.http.post<User>(`${URL}/${API_KEY_ENDPOINT}/${RESOURCE}`, {user}))
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }
  
}

