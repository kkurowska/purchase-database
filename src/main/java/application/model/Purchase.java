package application.model;


import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kkurowska on 13.12.2016.
 */

@Entity
public class Purchase{

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

    @Column(name = "data")
    private Date date;

    public Purchase() {
    }

    public Purchase(Product product, Store store, double price, boolean sale, Date date) {
        this.product = product;
        this.store = store;
        this.price = price;
        this.sale = sale;
        this.date = date;
    }

    @Override
    public String toString() {
        String issale;
        if (isSale()) {
            issale = "YES";
        } else {
            issale = "NO";
        }
        return getProduct().toString() + ", " + getStore().toString() + ", " + getPrice() + ", " + issale + ", " + getDate().toLocaleString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormatedDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(getDate());
    }
}
