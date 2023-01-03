// Products
export interface Product {
    id?: number;
    _id?: string;
    brand?: string;
    description?: string;
    image?: string;
    price?: number;
    discount?:number;
    quantity?:number;
    tags?: any[];
}