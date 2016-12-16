package application.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kkurowska on 13.12.2016.
 */

@Entity
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "price")
    private double price;

    @Column(name = "sale")
    private boolean sale;

    protected Purchase() {
    }

    public Purchase(Product product, Store store, double price, boolean sale) {
        super();
        this.product = product;
        this.store = store;
        this.price = price;
        this.sale = sale;
    }

    @Override
    public String toString() {
        String issale;
        if (isSale()) {
            issale = "YES";
        } else {
            issale = "NO";
        }
        return product.toString() + ", " + store.toString() + ", " + getPrice() + ", " + issale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }
}
