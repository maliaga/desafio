import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ViewportScroller } from '@angular/common';
import { ProductService } from "../../shared/services/product.service";
import { Product } from '../../shared/classes/product';

@Component({
  selector: 'app-main-content-sidebar',
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit {
  
  public grid: string = 'col-xl-3 col-md-6';
  public layoutView: string = 'grid-view';
  public products: Product[] = [];
  public brands: any[] = [];
  public colors: any[] = [];
  public size: any[] = [];
  public minPrice: number = 0;
  public maxPrice: number = 1200;
  public tags: any[] = [];
  public category: string;
  public pageNo: number = 1;
  public paginate: any = {}; // Pagination use only
  public sortBy: string; // Sorting Order
  public mobileSidebar: boolean = false;
  public loader: boolean = true;

  constructor(private route: ActivatedRoute, private router: Router,
    private viewScroller: ViewportScroller, public productService: ProductService) {  

      this.route.queryParams.subscribe(params => {
        this.pageNo = params.page ? params.page : this.pageNo;
        this.productService.getproducts().subscribe(
          (data)=>{
            console.log('Data products: ', data);
            
            this.products = data;
            this.paginate = this.productService.getPager(this.products.length, +this.pageNo);     // get paginate object from service
            this.products = this.products.slice(this.paginate.startIndex, this.paginate.endIndex + 1); // get current page of items
  
          },
          (error)=>{
            console.log('Error');
          }
        );
      });
      
  }

  ngOnInit(): void {
  }
  receiveMessage($event){
    console.log($event);
  }

  // product Pagination
  setPage(page: number) {
    this.router.navigate([], { 
      relativeTo: this.route,
      queryParams: { page: page },
      queryParamsHandling: 'merge', // preserve the existing query params in the route
      skipLocationChange: false  // do trigger navigation
    }).finally(() => {
      this.viewScroller.setOffset([120, 120]);
      this.viewScroller.scrollToAnchor('products'); // Anchore Link
    });
  }

  updateGridLayout(value: string) {
    this.grid = value;
  }


  updateLayoutView(value: string) {
    this.layoutView = value;
    if(value == 'list-view')
      this.grid = 'col-lg-12';
    else
      this.grid = 'col-xl-3 col-md-6';
  }

}
