export interface Toilet {
    id: string,
    name: string,
    price: number,
    gender: string,
    type: string,
    remarks: string,
    openingHours: OpeningHours,
    closingHours: ClosingHours,
    updatedOn: Date,
    images: string[],
    website: string,
    region: string,
    author: string,
    rating: number,
    reviews: Review[],
    amenities: Amenities,
    footTraffic: FootTraffic
}

// id same as toilet id (retrieved)
export interface Marker {
    id: string,
    title: string,
    lat: any,
    lng: any,
    content: any
}

export interface FootTraffic {
    level: number,
    timing: Date
}

export interface OpeningHours {
    sunday: Date,
    monday: Date,
    tuesday: Date,
    wednesday: Date,
    thursday: Date,
    friday: Date,
    saturday: Date
}

export interface ClosingHours {
    sunday: Date,
    monday: Date,
    tuesday: Date,
    wednesday: Date,
    thursday: Date,
    friday: Date,
    saturday: Date
}

export interface User {
    username: string,
    email: string,
    password: string,
    createdOn: Date,
    updatedOn: Date,
    firstName: string,
    lastName: string,
    profileImage: string,
    bookmarks: Toilet[],
    reviews: Review[],
    jwtToken: string
}

export interface Review {
    id: number,
    name: string,
    header: string,
    body: string,
    createdOn: Date,
    lastUpdate: Date,
    toiletId: string,
    rating: number,
    images: string
}

export interface Amenities {
    bidet: boolean,
    dryer: boolean,
    reserved: boolean,
    locked: boolean,
    unisex: boolean
}
 
export interface MarkerStorage {
    markers: Marker[]
}