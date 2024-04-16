import {Injectable} from "@angular/core";
import {ComponentStore} from "@ngrx/component-store";
import { Marker, MarkerStorage, User } from "./models";

@Injectable({
    providedIn: 'root'
})
        
export class UserStore extends ComponentStore<User> {
    user!: User

    constructor() {
        super({userId: "",
            username: "",
            email: "",
            password: "",
            createdOn: new Date,
            updatedOn: new Date,
            firstName: "",
            lastName: "",
            profileImage: "",
            bookmarks: [],
            reviews: [],
            jwtToken: ""})
    }

    readonly getUser = this.select<User>(
        (slice: User) => slice
        )

    public readonly addToStorage = this.updater<User>(
            (slice: User) => 
            ({
            ...slice,
      }));

}

    