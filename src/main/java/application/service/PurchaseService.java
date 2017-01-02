package application.service;

import application.dto.PurchaseDTO;
import application.dto.StoreDTO;
import application.exception.*;
import application.model.Product;
import application.model.Purchase;
import application.model.Store;
import application.repository.ProductRepository;
import application.repository.PurchaseRepository;
import application.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static application.exception.ErrorMessages.*;

/**
 * Created by kkurowska on 15.12.2016.
 */

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Long addPurchase(PurchaseDTO dto){
        validate(dto);
        validateDataBase(dto);
        Purchase purchase = new Purchase();
        purchase.setProduct(productRepository.findOne(dto.getProductId()));
        purchase.setStore(storeRepository.findOne(dto.getStoreId()));
        purchase.setPrice(dto.getPrice());
        purchase.setSale(dto.isSale());
        try {
            Date date = dateFormat.parse(dto.getDate());
            purchase.setDate(date);
        } catch (ParseException e){
          //  throw new WrongDateFormatException("Wrong date format, expected yyyy-MM-dd HH:mm:ss");
        }
        return purchaseRepository.save(purchase).getId();
    }

//    public Long addPurchaseByName(PurchaseDTO dto, String productName, String producer, String storeName){
//        validateByName(dto, productName, producer, storeName);
//        validateDataBase(dto); //TODO
//        Purchase purchase = new Purchase();
//        purchase.setProduct(productRepository.findByNameAndProducer(productName, producer));
//        purchase.setStore(storeRepository.findByName(storeName));
//        purchase.setPrice(dto.getPrice());
//        purchase.setSale(dto.isSale());
//        try {
//            Date date = dateFormat.parse(dto.getDate());
//            purchase.setDate(date);
//        } catch (ParseException e){
//            //TODO
//        }
//        return purchaseRepository.save(purchase).getId();
//    }

    public PurchaseDTO findPurchase(Long id){
        Purchase purchase = purchaseRepository.findOne(id);
        if (purchase == null){
            throw new PurchaseNotFoundException("Purchase not found");
        }
        PurchaseDTO dto = new PurchaseDTO();
        dto.setId(purchase.getId());
        dto.setProductId(purchase.getProduct().getId());
        dto.setStoreId(purchase.getStore().getId());
        dto.setPrice(purchase.getPrice());
        dto.setSale(purchase.isSale());
        dto.setDate(dateFormat.format(purchase.getDate()));
        return dto;
    }

    public void deletePurchase(Long id){
        Purchase purchase = purchaseRepository.findOne(id);
        if (purchase == null){
            throw new PurchaseNotFoundException("Purchase not found");
        }
        purchaseRepository.delete(id);
    }

    public double minimalProductPrice(Long productId, String start, String end){
        Product product = productRepository.findOne(productId);
        if (product == null){
            throw new ProductNotFoundException("Product not found.");
        }
        List<Purchase> purchases = new ArrayList<Purchase>();
        if (start == null && end == null){
            purchases = productNoDates(product);
        } else if (start == null && end != null){
            purchases = productLessThan(product, end);
        } else if (start != null && end == null){
            purchases = productGreaterThan(product, start);
        } else {
            purchases = productBetween(product, start, end);
        }
        double minimalPrice = purchases.get(0).getPrice();
        return minimalPrice;
    }

    public double averageProductPrice(Long productId, String start, String end){
        Product product = productRepository.findOne(productId);
        if (product == null){
            throw new ProductNotFoundException("Product not found.");
        }
        List<Purchase> purchases = new ArrayList<Purchase>();
        if (start == null && end == null){
            purchases = productNoDates(product);
        } else if (start == null && end != null){
            purchases = productLessThan(product, end);
        } else if (start != null && end == null){
            purchases = productGreaterThan(product, start);
        } else {
            purchases = productBetween(product, start, end);
        }
        List<Double> prices = new ArrayList<Double>();
        double averagePrice = 0;
        for (int i = 0; i < purchases.size() ; i++) {
            averagePrice += purchases.get(i).getPrice();
        }
        averagePrice = averagePrice/purchases.size();
        return averagePrice;
    }

    private List<Purchase> productNoDates(Product product){
        List<Purchase> purchases = purchaseRepository.findByProductOrderByPriceAsc(product);
        if (purchases.isEmpty()){
            throw new PurchaseNotFoundException("There is no purchase with productId=" + product.getId());
        }
        return purchases;
    }

    private List<Purchase> productLessThan(Product product, String end){
        try {
            Date endDate = dateFormat.parse(end);
            List<Purchase> purchases = purchaseRepository.findByProductAndDateLessThanOrderByPriceAsc(product, endDate);
            if (purchases.isEmpty()) {
                throw new PurchaseNotFoundException("There is no purchase with productId=" + product.getId() + " and dates before " + end);
            }
            return purchases;
        } catch (ParseException e){
            throw new WrongDateFormatException("Wrong date format, expected yyyy-MM-dd HH:mm:ss");
        }
    }

    private List<Purchase> productGreaterThan(Product product, String start){
        try {
            Date startDate = dateFormat.parse(start);
            List<Purchase> purchases = purchaseRepository.findByProductAndDateGreaterThanOrderByPriceAsc(product, startDate);
            if (purchases.isEmpty()) {
                throw new PurchaseNotFoundException("There is no purchase with productId=" + product.getId() + " and dates after " + start);
            }
            return purchases;
        } catch (ParseException e){
            throw new WrongDateFormatException("Wrong date format, expected yyyy-MM-dd HH:mm:ss");
        }
    }

    private List<Purchase> productBetween(Product product, String start, String end){
        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);
            List<Purchase> purchases = purchaseRepository.findByProductAndDateBetweenOrderByPriceAsc(product, startDate, endDate);
            if (purchases.isEmpty()) {
                throw new PurchaseNotFoundException("There is no purchase with productId=" + product.getId() + " and dates between " + start + " - " + end);
            }
            return purchases;
        } catch (ParseException e){
            throw new WrongDateFormatException("Wrong date format, expected yyyy-MM-dd HH:mm:ss");
        }
    }

//    public PurchaseDTO findByProduct(Long productId) {
//        Product product = productRepository.findOne(productId);
//        if (product == null){
//            throw new ProductNotFoundException("Product not found.");
//        }
//        Purchase purchase = purchaseRepository.findByProduct(product);
//        if (purchase == null){
//            throw new PurchaseNotFoundException("Purchase not found.");
//        }
//        PurchaseDTO dto = new PurchaseDTO();
//        dto.setId(purchase.getId());
//        dto.setProductId(purchase.getProduct().getId());
//        dto.setStoreId(purchase.getStore().getId());
//        dto.setPrice(purchase.getPrice());
//        dto.setSale(purchase.isSale());
//        dto.setDate(dateFormat.format(purchase.getDate()));
//        return dto;
//    }


    private void validate(PurchaseDTO dto){
        List<ValidationError> errors = new ArrayList<>();
        if (dto.getId() != null) {
            errors.add(new ValidationError("id", NOT_ALLOWED));
        }
        if (dto.getProductId() == null) {
            errors.add(new ValidationError("productId", MAY_NOT_BE_NULL));
        }
        if (dto.getProductId() <= 0){
            errors.add(new ValidationError("productId", NOT_ALLOWED));
        }
        if (dto.getStoreId() == null) {
            errors.add(new ValidationError("storeId", MAY_NOT_BE_NULL));
        }
        if (dto.getStoreId() <= 0){
            errors.add(new ValidationError("storeId", NOT_ALLOWED));
        }
        if (dto.getPrice() == 0 ) {
            errors.add(new ValidationError("price", MAY_NOT_BE_NULL));
        }
        if (dto.getPrice() < 0){
            errors.add(new ValidationError("price", NOT_ALLOWED));
        }
        //sale can be null, then sale = false
        if (dto.getDate() == null ) {
            errors.add(new ValidationError("date", MAY_NOT_BE_NULL));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    private void validateDataBase(PurchaseDTO dto){
        List<ValidationError> errors = new ArrayList<>();
        if (!productRepository.exists(dto.getProductId())){
            errors.add(new ValidationError("product", NOT_FOUND));
        }
        if (!storeRepository.exists(dto.getStoreId())){
            errors.add(new ValidationError("store", NOT_FOUND));
        }

        Product product = productRepository.findOne(dto.getProductId());
        Store store = storeRepository.findOne(dto.getStoreId());
        try {
            Date date = dateFormat.parse(dto.getDate());
            Purchase purchase = purchaseRepository.findByProductAndStoreAndPriceAndSaleAndDate(product, store, dto.getPrice(), dto.isSale(), date);
            if (purchase != null){
                errors.add(new ValidationError("purchase", ALREADY_EXIST));
            }
        } catch (ParseException e){
            throw new WrongDateFormatException("Wrong date format, expected yyyy-MM-dd HH:mm:ss");
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

//    private void validateByName(PurchaseDTO dto, String productName, String producer, String storeName){
//        if (productRepository.findByNameAndProducer(productName, producer) == null){
//            throw new ProductNotFoundException("Product not found.");
//        }
//        if (storeRepository.findByName(storeName) == null){
//            throw new StoreNotFoundException("Store not found");
//        }
//        //TODO czy istnieje już identyczny purchase?
//        //TODO not null etc
//    }
}
