import {Injectable, inject} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable, lastValueFrom} from "rxjs";
import {} from "./models";

import { environment } from '../environments/environment'

const URL = environment.url

@Injectable()
export class ProductService {

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
}
