package application.service;

import application.dto.PurchaseDTO;
import application.dto.StoreDTO;
import application.exception.ProductNotFoundException;
import application.exception.PurchaseNotFoundException;
import application.exception.StoreNotFoundException;
import application.model.Product;
import application.model.Purchase;
import application.model.Store;
import application.repository.ProductRepository;
import application.repository.PurchaseRepository;
import application.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Long addPurchase(PurchaseDTO dto){
        validate(dto);
        Purchase purchase = new Purchase();
        purchase.setProduct(productRepository.findOne(dto.getProductId()));
        purchase.setStore(storeRepository.findOne(dto.getStoreId()));
        purchase.setPrice(dto.getPrice());
        purchase.setSale(dto.isSale());
        purchase.setDate(dto.getDate());
        return purchaseRepository.save(purchase).getId();
    }

    public Long addPurchaseByName(PurchaseDTO dto, String productName, String producer, String storeName){
        validateByName(dto, productName, producer, storeName);
        Purchase purchase = new Purchase();
        purchase.setProduct(productRepository.findByNameAndProducer(productName, producer));
        purchase.setStore(storeRepository.findByName(storeName));
        purchase.setPrice(dto.getPrice());
        purchase.setSale(dto.isSale());
        purchase.setDate(dto.getDate());
        return purchaseRepository.save(purchase).getId();
    }

    public PurchaseDTO findPurchase(Long id){
        Purchase purchase = purchaseRepository.findOne(id);
        if (purchase == null){
            throw new PurchaseNotFoundException("Purchase not found.");
        }
        PurchaseDTO dto = new PurchaseDTO();
        dto.setId(purchase.getId());
        dto.setProductId(purchase.getProduct().getId());
        dto.setStoreId(purchase.getStore().getId());
        dto.setPrice(purchase.getPrice());
        dto.setSale(purchase.isSale());
        dto.setDate(purchase.getDate());
        return dto;
    }

    private void validate(PurchaseDTO dto){
        if (!productRepository.exists(dto.getProductId())){
            throw new ProductNotFoundException("Product not found.");
        }
        if (!storeRepository.exists(dto.getStoreId())){
            throw new StoreNotFoundException("Store not found");
        }
        //TODO czy istnieje już identyczny purchase?
    }

    private void validateByName(PurchaseDTO dto, String productName, String producer, String storeName){
        if (productRepository.findByNameAndProducer(productName, producer) == null){
            throw new ProductNotFoundException("Product not found.");
        }
        if (storeRepository.findByName(storeName) == null){
            throw new StoreNotFoundException("Store not found");
        }
        //TODO czy istnieje już identyczny purchase?
    }

}
