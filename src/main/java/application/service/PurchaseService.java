package application.service;

import application.dto.PurchaseDTO;
import application.exception.*;
import application.model.Product;
import application.model.Purchase;
import application.model.Store;
import application.repository.ProductRepository;
import application.repository.PurchaseRepository;
import application.repository.StoreRepository;
import application.utils.MyDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorField.*;
import static application.utils.MyDateFormat.MY_DATE_FORMAT;

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

    private DateFormat dateFormat = new SimpleDateFormat(MY_DATE_FORMAT.getValue());

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
            throw new PurchaseNotFoundException();
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
            throw new PurchaseNotFoundException();
        }
        purchaseRepository.delete(id);
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
            errors.add(new ValidationError(ID, NOT_ALLOWED));
        }
        if (dto.getProductId() == null) {
            errors.add(new ValidationError(PRODUCT_ID, MAY_NOT_BE_NULL));
        }
        if (dto.getProductId() <= 0){
            errors.add(new ValidationError(PRODUCT_ID, NOT_ALLOWED));
        }
        if (dto.getStoreId() == null) {
            errors.add(new ValidationError(STORE_ID, MAY_NOT_BE_NULL));
        }
        if (dto.getStoreId() <= 0){
            errors.add(new ValidationError(STORE_ID, NOT_ALLOWED));
        }
        if (dto.getPrice() == 0 ) {
            errors.add(new ValidationError(PRICE, MAY_NOT_BE_NULL));
        }
        if (dto.getPrice() < 0 || dto.getPrice() > 1000000000){
            errors.add(new ValidationError(PRICE, NOT_ALLOWED));
        }
        //sale can be null, then sale = false
        if (dto.getDate() == null ) {
            errors.add(new ValidationError(DATE, MAY_NOT_BE_NULL));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    private void validateDataBase(PurchaseDTO dto){
        List<ValidationError> errors = new ArrayList<>();
        if (!productRepository.exists(dto.getProductId())){
            errors.add(new ValidationError(PRODUCT, NOT_FOUND));
        }
        if (!storeRepository.exists(dto.getStoreId())){
            errors.add(new ValidationError(STORE, NOT_FOUND));
        }

        Product product = productRepository.findOne(dto.getProductId());
        Store store = storeRepository.findOne(dto.getStoreId());
        try {
            Date date = dateFormat.parse(dto.getDate());
            checkDateParse(dto.getDate(),date);
            Purchase purchase = purchaseRepository.findByProductAndStoreAndPriceAndSaleAndDate(product, store, dto.getPrice(), dto.isSale(), date);
            if (purchase != null){
                errors.add(new ValidationError(PURCHASE, ALREADY_EXIST));
            }
        } catch (ParseException e){
            errors.add(new ValidationError(DATE, WRONG_FORMAT));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    public void checkDateParse(String stringDate, Date date){
        String checkDate = dateFormat.format(date);
        String[] parts = stringDate.split("/");
        int year = Integer.parseInt(parts[0]);
        if (!checkDate.equals(stringDate) || year > 9999){
            throw new WrongDateFormatException();
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
