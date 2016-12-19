package application.dto;

import java.util.Date;

/**
 * Created by kkurowska on 16.12.2016.
 */
public class PurchaseDTO {

    private Long id;
    private Long productId;
    private Long storeId;
    private double price;
    private boolean sale;
    private Date date;

    public PurchaseDTO() {
    }

    public PurchaseDTO(Long id, Long productId, Long storeId, double price, boolean sale, Date date) {
        this.productId = productId;
        this.storeId = storeId;
        this.price = price;
        this.sale = sale;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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
}
