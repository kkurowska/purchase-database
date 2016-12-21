package application.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private String date;

    public PurchaseDTO() {
    }

    public PurchaseDTO(Long id, Long productId, Long storeId, double price, boolean sale, String date) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//    @Override
//    public String toString(){
//        return id + ", " + productId + ", " + storeId + ", " + price + ", " + sale + ", " + date;
//    }
}
