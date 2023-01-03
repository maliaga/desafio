import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from '../shared/shared.module';
import { ShopRoutingModule } from './shop-routing.module';

import { MainContentComponent } from './main-content/main-content.component';

import { GridComponent } from './widgets/grid/grid.component';
import { PaginationComponent } from './widgets/pagination/pagination.component';
import { CartComponent } from './cart/cart.component';

@NgModule({
  declarations: [
    MainContentComponent,
    GridComponent,
    PaginationComponent,
    CartComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    ShopRoutingModule,
  ]
})
export class ShopModule { }
