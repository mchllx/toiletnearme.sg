export interface Product {
  prodId: string
  name: string
  brand: string
  price: number
  discountPrice: number
  image: string
  quantity: string
}

export interface LineItem {
  prodId: string
  quantity: number
  name: string
  price: number
}

export interface Cart {
  lineItems: LineItem[]
}

export interface Order {
  name: string
  address: string
  priority: boolean
  comments: string
  cart: Cart
}

