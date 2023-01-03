import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LazyLoadImageModule, scrollPreset } from 'ng-lazyload-image';
import { NgxSkeletonLoaderModule } from 'ngx-skeleton-loader';
// Header and Footer Components
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
// Components
import { BreadcrumbComponent } from './components/breadcrumb/breadcrumb.component';
import { ProductComponent } from './components/product/product.component';
// Modals Components
import { CartModalComponent } from './components/modal/cart-modal/cart-modal.component';
// Skeleton Loader Components
import { SkeletonComponent } from './components/skeleton/skeleton.component';
@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    BreadcrumbComponent,
    ProductComponent,
    CartModalComponent,
    SkeletonComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgxSkeletonLoaderModule,
    LazyLoadImageModule.forRoot({
      // preset: scrollPreset // <-- tell LazyLoadImage that you want to use scrollPreset
    }),
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    LazyLoadImageModule,
    HeaderComponent,
    FooterComponent,
    BreadcrumbComponent,
    ProductComponent,
    CartModalComponent,
    SkeletonComponent,
    NgxSkeletonLoaderModule,
  ]
})
export class SharedModule { }
