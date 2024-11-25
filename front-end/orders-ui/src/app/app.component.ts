import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { OrdersComponent } from './orders/orders.component';
import { ProductsComponent } from './products/products.component';

@Component({
  selector: 'app-root',
  imports: [OrdersComponent, ProductsComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'orders-ui';
}
