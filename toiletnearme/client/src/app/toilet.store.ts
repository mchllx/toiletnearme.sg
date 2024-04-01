import {Injectable} from "@angular/core";
import {ComponentStore} from "@ngrx/component-store";

import { Toilet } from "./models";

@Injectable({
    providedIn: 'root'
})
        
export class CartStore extends ComponentStore<Toilet> {

    // cart!: Cart[]

    // constructor() {
    //     super(
    //         {lineItems: []}
    //     )
    // }

    // readonly getAllItems = this.select<LineItem[]>(
    //     (slice: Cart) => slice.lineItems
    //     )

    // readonly getItemCount = this.select<number>(
    // (slice: Cart) => slice.lineItems.length
    // )

    // public readonly addToCart = this.updater<LineItem[]>(
    //         (slice: Cart, lineItems: LineItem[]) => 
    //         ({
    //         ...slice, lineItems,
    //   }));

}

    