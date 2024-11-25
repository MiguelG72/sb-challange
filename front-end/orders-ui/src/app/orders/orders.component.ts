import { CommonModule } from '@angular/common';
import { Component, resource } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Order } from './order.model';
import { ProductsComponent } from '../products/products.component';

@Component({
  selector: 'orders',
  templateUrl: './orders.component.html',
  imports: [CommonModule, FormsModule, ProductsComponent],
})
export class OrdersComponent {
  orders: Order[] = [];
  newOrder = { address: '', products: [] as string[] };
  apiUrl = 'http://localhost:8080/order';

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders() {
    fetch(this.apiUrl)
      .then((response) => response.json())
      .then((data) => {
        console.info('response:', data);
        this.orders = data.orders || [];
      })
      .catch((error) => {
        console.error('Error fetching orders:', error);
        this.orders = []; // Ensure no null/undefined values
      });
  }

  addProduct(product: string) {
    this.newOrder.products.push(product);
  }

  removeProduct(product: string) {
    const index = this.newOrder.products.indexOf(product);
    if (index !== -1) {
      this.newOrder.products.splice(index, 1);
    }
  }

  async createOrder() {
    console.log('Order to be sent:', this.newOrder);
    const requestBody = JSON.stringify(this.newOrder);
    fetch('http://localhost:8080/order', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(this.newOrder),
    })
      .then((response) => {
        if (response.ok) {
          alert('Order created successfully!');
          this.newOrder = { address: '', products: [] }; // Reset the order
        } else {
          alert('Failed to create order');
        }
      })
      .then(() => {
        this.loadOrders();
        this.newOrder = { address: '', products: [] as string[] };
      });
  }
}
