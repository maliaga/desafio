import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { Product } from '../classes/product';

const state = {
  products: JSON.parse(sessionStorage.products || '[]'),
  cart: JSON.parse(sessionStorage.cartItems || '[]')
};

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  public urlApi = 'http://localhost:8080/api/v1/';
   httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'

    })
  };

  constructor(private http: HttpClient,
              private toastrService: ToastrService) {
      // this.setproducstorage();
    }

  // Get  all Products
   getproducts() {
    return this.http.get<Product[]>(this.urlApi + 'products');
  }
// Create cart
  CreateCart(idProduct){
    return this.http.post(this.urlApi + 'cart/product/' + idProduct, {}, );
  }
  // Add item cart
  addproductocart(idcart, idProduct){
    return this.http.post(this.urlApi + 'cart/' + idcart + '/product/' + idProduct, this.httpOptions);
  }
  // Remove item cart
  Removeproductocart(idcart, idProduct){
    return this.http.delete(this.urlApi + 'cart/' + idcart + '/product/' + idProduct, this.httpOptions);
  }

  public getPager(totalItems: number, currentPage: number = 1, pageSize: number = 16) {
    // Calculate total pages
    const totalPages = Math.ceil(totalItems / pageSize);

    const paginateRange = 3;


    if (currentPage < 1) {
      currentPage = 1;
    } else if (currentPage > totalPages) {
      currentPage = totalPages;
    }

    let startPage: number;
    let endPage: number;
    if (totalPages <= 5) {
      startPage = 1;
      endPage = totalPages;
    } else if (currentPage < paginateRange - 1){
      startPage = 1;
      endPage = startPage + paginateRange - 1;
    } else {
      startPage = currentPage - 1;
      endPage =  currentPage + 1;
    }


    const startIndex = (currentPage - 1) * pageSize;
    const endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);
    const pages = Array.from(Array((endPage + 1) - startPage).keys()).map(i => startPage + i);

    return {
      totalItems,
      currentPage,
      pageSize,
      totalPages,
      startPage,
      endPage,
      startIndex,
      endIndex,
      pages
    };
  }

}
