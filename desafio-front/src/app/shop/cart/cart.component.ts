import { Component, OnInit } from '@angular/core';
import { ProductService } from "../../shared/services/product.service";
import { Product } from "../../shared/classes/product";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  public products: Product[] = [];
  responsestorage:any;
  newproducts = {}

  constructor(public productService: ProductService) {
     this.responsestorage = JSON.parse(sessionStorage.getItem('cart'));
    this.products = this.responsestorage.products;
    this.GroupProducts(this.responsestorage);
  }

  ngOnInit(): void {
  }
  // Increament
  increment(product, qty = 1) {

    this.productService.addproductocart(sessionStorage.getItem('idcart'),product).subscribe(
      (dataresponse:any)=>{
        console.log('add data card', dataresponse);
        sessionStorage.setItem('cart', JSON.stringify(dataresponse) );
        this.responsestorage = dataresponse;
        this.GroupProducts(dataresponse);

      },
      (error)=>{
        console.log('Error add card: data', error);
      });
  }

  // Decrement
  decrement(product, qty = -1) {
    this.productService.Removeproductocart(sessionStorage.getItem('idcart'),product).subscribe(
      (dataresponse:any)=>{
        console.log('remove dataresponse: ', dataresponse);
        sessionStorage.setItem('cart', JSON.stringify(dataresponse) );
        this.responsestorage = dataresponse;
        this.GroupProducts(dataresponse);
      },
      (error)=>{
        console.log('Error add card: data', error);
      });
  }

  GroupProducts(dataresponse){
    this.newproducts =[];
        dataresponse.products.forEach( x => {
          if( !this.newproducts.hasOwnProperty(x.id)){
            this.newproducts[x.id] = {
              productos: []
            }
          }
          this.newproducts[x.id].productos.push(x)
        });
        this.newproducts=Object.values(this.newproducts);
  }

}
