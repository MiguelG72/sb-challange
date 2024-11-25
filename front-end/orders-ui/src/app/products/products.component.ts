import { CommonModule } from "@angular/common"
import { Component, EventEmitter, Output, resource } from "@angular/core"
import { Product } from "./products.model";

@Component({
    selector: 'products',
    templateUrl: './products.component.html',
    imports: [CommonModule]
})

export class ProductsComponent {
    products: Product[] = [];

    productQuantities: { [key: string]: number } = {};
    @Output() productAdded = new EventEmitter<string>();
    @Output() productRemoved = new EventEmitter<string>();

    ngOnInit(){
        this.loadProducts();
    }

    loadProducts() {
        fetch('http://localhost:8081/product')
            .then(response => response.json())
            .then(data => {
                console.info("response:", data);
                this.products = data.products || [];
            })
            .then(
                () => {
                    this.products.forEach(product => {
                        this.productQuantities[product.id] = 0;
                    });
                }
            )
            .catch(error => {
                console.error('Error fetching products:', error);
                this.products = [];
            });

    }

    addProduct(product: Product) {
        this.productQuantities[product.id]++;
        this.productAdded.emit(product.id);
    }

    removeProduct(product: Product) {
        if (this.productQuantities[product.id] > 0) {
            this.productQuantities[product.id]--;
            this.productRemoved.emit(product.id); 
        }
    }

}
