import { NgModule } from '@angular/core';
import { OrdersComponent } from './orders/orders.component';
import { AppComponent } from './app.component';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ProductsComponent } from './products/products.component';



@NgModule({
  declarations: [AppComponent, OrdersComponent, ProductsComponent],
  imports: [BrowserModule, CommonModule],
  bootstrap: [AppComponent],
  providers: [provideHttpClient()]
})
export class AppModule { }
