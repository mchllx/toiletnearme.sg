import {Injectable} from "@angular/core";
import {ComponentStore} from "@ngrx/component-store";
import { Amenities, ClosingHours, FootTraffic, OpeningHours, Review, Toilet } from "./models";

@Injectable({
    providedIn: 'root'
})
        
export class ToiletStore extends ComponentStore<Toilet> {

    constructor() {
        super()
    }

    readonly getAllReviews = this.select<Review[]>(
        (slice: Toilet) => slice.reviews
        )

    readonly getReviewCount = this.select<number>(
    (slice: Toilet) => slice.reviews.length
    )

    public readonly addReviewsToToilet = this.updater<Review[]>(
            (slice: Toilet, reviews: Review[]) => 
            ({
            ...slice, reviews,
      }));

    public readonly addOpeningToToilet = this.updater<OpeningHours>(
    (slice: Toilet, openingHours: OpeningHours) => 
    ({
        ...slice, openingHours,
    }));

    public readonly addClosingToToilet = this.updater<ClosingHours>(
        (slice: Toilet, closingHours: ClosingHours) => 
        ({
        ...slice, closingHours,
    }));

    public readonly addAmenitiesToToilet = this.updater<Amenities>(
        (slice: Toilet, amenities: Amenities) => 
        ({
        ...slice, amenities,
    }));

    public readonly addFootTrafficToToilet = this.updater<FootTraffic>(
        (slice: Toilet, footTraffic: FootTraffic) => 
        ({
        ...slice, footTraffic,
    }));

}

    