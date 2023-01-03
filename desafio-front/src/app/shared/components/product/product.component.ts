import { Component, OnInit, Input, ViewChild } from '@angular/core';
//import { QuickViewComponent } from "../modal/quick-view/quick-view.component";
import { CartModalComponent } from "../modal/cart-modal/cart-modal.component";
import { Product } from "../../classes/product";
import { ProductService } from "../../services/product.service";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  @Input() product: Product;
  @Input() cartModal: boolean = false; // Default False
  @Input() loader: boolean = false;
  
  //@ViewChild("quickView") QuickView: QuickViewComponent;
  @ViewChild("cartModal") CartModal2: CartModalComponent;

  public ImageSrc : string

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    if(this.loader) {
      setTimeout(() => { this.loader = false; }, 2000); // Skeleton Loader
    }
  }
  actioncart(product){
    if(sessionStorage.getItem('idcart') != null){
      this.addToCart(product);
    }
    else{
      this.CreateCart(product);

    }

  }
  CreateCart(data){
    this.productService.CreateCart(data.id).subscribe(
      (dataresponse:any)=>{
        console.log('data create card',dataresponse);
        let idcart= dataresponse['id-shopping-cart'];
        console.log('idcart',idcart);
        sessionStorage.setItem('idcart', idcart );
        sessionStorage.setItem('cart', JSON.stringify(dataresponse) );
      
        this.CartModal2.openModal(data);
      },
      (error)=>{
        console.log('Error: data', error);
      });
  }
  addToCart(data) {
    this.productService.addproductocart(sessionStorage.getItem('idcart'),data.id).subscribe(
      (dataresponse)=>{
        console.log('add data card', dataresponse);
        sessionStorage.setItem('cart', JSON.stringify(dataresponse) );
        
        this.CartModal2.openModal(data);
      },
      (error)=>{
        console.log('Error add card: data', error);
      });
  }
  Removetocart(){

  }


}
