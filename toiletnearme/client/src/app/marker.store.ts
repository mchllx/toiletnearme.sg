import {Injectable} from "@angular/core";
import {ComponentStore} from "@ngrx/component-store";
import { Marker, MarkerStorage } from "./models";

@Injectable({
    providedIn: 'root'
})
        
export class LocationStore extends ComponentStore<MarkerStorage> {

    constructor() {
        super(
            {markers: []}
        )
    }

    readonly getAllMarkers = this.select<Marker[]>(
        (slice: MarkerStorage) => slice.markers
        )

    readonly getMarkerCount = this.select<number>(
    (slice: MarkerStorage) => slice.markers.length
    )

    public readonly addToStorage = this.updater<Marker[]>(
            (slice: MarkerStorage, markers: Marker[]) => 
            ({
            ...slice, markers,
      }));

}

    