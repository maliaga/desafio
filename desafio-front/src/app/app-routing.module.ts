import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShopComponent } from './shop/shop.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: 'shop/tienda',
    pathMatch: 'full'
  },
  {
    path: 'shop',
    component: ShopComponent,
    loadChildren: () => import('./shop/shop.module').then(m => m.ShopModule)
  },
  {
    path: '**', // Navigate to Home Page if not found any page
    redirectTo: 'shop/tienda',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
