import {Injectable, inject} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable, lastValueFrom} from "rxjs";
import { Review, Toilet } from "../models";


import { environment } from '../../environments/environment'

const URL = environment.url
const API_KEY_ENDPOINT = 'api/review/'

@Injectable()
export class ReviewService {

  private http = inject(HttpClient)

  // GET http://localhost:8080/api/review/
  constructor(http: HttpClient) {}
  getAllReviews(): Promise<Review[]> { 
    try {
      return lastValueFrom(this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {}))
    } catch (error) {
      console.error('Error fetching address', error)
      throw error
    }
  }

  // GET http://localhost:8080/api/review/{id}")
  getAllReviewsById(id: string): Observable<Review[]> {
    const params = new HttpParams()
    .set('id', id)

    try {
      return this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {params})
    } catch (error) {
      console.error('Error fetching address', error)
      throw error
    }
  }

  // GET http://localhost:8080/api/review/user/{region}")
  getReviewsByUserId(userId: string): Observable<Review[]> {
    const params = new HttpParams()
      .set('userId', userId)

    try {
      return this.http.get<any>(`${URL}/${API_KEY_ENDPOINT}`, {params})
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }

  // POST http://localhost:8080/api/review/")
  postReview(review: Review, jwtToken: string): Promise<Review> {
    const headers = new HttpHeaders()
    .set('Authorization', 'Bearer '.concat(jwtToken)) 

    try {
      return lastValueFrom(this.http.post<Review>(`${URL}/${API_KEY_ENDPOINT}`, {review}, {headers}))
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }

  // DELETE http://localhost:8080/api/toilet/address/{id}")
  deleteToiletById(id: string, jwtToken: string): Promise<Review> {
    const headers = new HttpHeaders()
    .set('Authorization', 'Bearer '.concat(jwtToken))
    
    const params = new HttpParams()
      .set('id', id)
    
    try {
      return lastValueFrom(this.http.delete<Review>(`${URL}/${API_KEY_ENDPOINT}+${params}`, {headers}))
    } catch (error) {
      console.error('Error fetching address:', error)
      throw error
    }
  }
  
}