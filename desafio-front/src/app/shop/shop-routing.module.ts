import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MainContentComponent } from './main-content/main-content.component';

import { CartComponent } from './cart/cart.component';

const routes: Routes = [
  {
    path: 'tienda',
    component: MainContentComponent
  },
  {
    path: 'carrito',
    component: CartComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopRoutingModule { }
