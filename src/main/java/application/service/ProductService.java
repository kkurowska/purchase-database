package application.service;

import application.dto.ProductDTO;
import application.exception.*;
import application.exception.Error;
import application.model.Category;
import application.model.Product;
import application.model.Purchase;
import application.model.Unit;
import application.repository.ProductRepository;
import application.repository.PurchaseRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorField.*;
import static application.model.Category.fromCategoryValue;
import static application.model.Unit.fromUnitValue;

/**
 * Created by kkurowska on 15.12.2016.
 */

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private int maxLength = 20;

    public Long addProduct(ProductDTO dto){
        validate(dto);
        validateDataBase(dto);
        Product product = new Product();
        product.setName(dto.getName());
        product.setProducer(dto.getProducer());
        product.setUnit(Unit.valueOf(dto.getUnit()));
        product.setCategory(Category.valueOf(dto.getCategory()));

        return productRepository.save(product).getId();
    }

    public ProductDTO findProduct(Long id){
        Product product = productRepository.findOne(id);
        if (product == null){
            throw new MyRuntimeException(new Error(PRODUCT, NOT_FOUND));
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setProducer(product.getProducer());
        dto.setUnit(product.getUnit().getValue());
        dto.setCategory(product.getCategory().getValue());
        return dto;
    }

    public Long updateProduct(ProductDTO dto){
        validateUpdate(dto);
        validateDataBase(dto);
        Product product = productRepository.findOne(dto.getId());
        if (product == null){
            throw new MyRuntimeException(new Error(PRODUCT, NOT_FOUND));
        }
        product.setName(dto.getName());
        product.setProducer(dto.getProducer());
        product.setUnit(Unit.valueOf(dto.getUnit()));
        product.setCategory(Category.valueOf(dto.getCategory()));
        return productRepository.save(product).getId();
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findOne(id);
        if (product == null){
            throw new MyRuntimeException(new Error(PRODUCT, NOT_FOUND));
        }
        List<Purchase> purchases = purchaseRepository.findByProduct(product);
        if (purchases.isEmpty()) {
            productRepository.delete(id);
        } else {
            throw new ActionNotAllowedException();
        }
    }

    private void validate(ProductDTO dto){
        List<Error> errors = new ArrayList<>();
        if (dto.getId() != null) {
            errors.add(new Error(ID, NOT_ALLOWED));
        }
        if (dto.getName() == null || StringUtils.isBlank(dto.getName())) {
            errors.add(new Error(NAME, MAY_NOT_BE_NULL));
        } else if (dto.getName().length() > maxLength) {
            errors.add(new Error(NAME, NOT_ALLOWED));
        }
        if (dto.getProducer() == null || StringUtils.isBlank(dto.getProducer())) {
            errors.add(new Error(PRODUCER, MAY_NOT_BE_NULL));
        } else if (dto.getProducer().length() > maxLength) {
            errors.add(new Error(PRODUCER, NOT_ALLOWED));
        }
        if (dto.getUnit() == null ) {
            errors.add(new Error(UNIT, MAY_NOT_BE_NULL));
        }
        if (dto.getCategory() == null ) {
            errors.add(new Error(CATEGORY, MAY_NOT_BE_NULL));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    private void validateUpdate(ProductDTO dto){
        List<Error> errors = new ArrayList<>();
        if (dto.getId() == null) {
            errors.add(new Error(ID, MAY_NOT_BE_NULL));
        } else if (dto.getId() <= 0){
            errors.add(new Error(ID, NOT_ALLOWED));
        }
        if (dto.getName() == null || StringUtils.isBlank(dto.getName())) {
            errors.add(new Error(NAME, MAY_NOT_BE_NULL));
        } else if (dto.getName().length() > maxLength) {
            errors.add(new Error(NAME, NOT_ALLOWED));
        }
        if (dto.getProducer() == null || StringUtils.isBlank(dto.getProducer())) {
            errors.add(new Error(PRODUCER, MAY_NOT_BE_NULL));
        } else if (dto.getProducer().length() > maxLength) {
            errors.add(new Error(PRODUCER, NOT_ALLOWED));
        }
        if (dto.getUnit() == null ) {
            errors.add(new Error(UNIT, MAY_NOT_BE_NULL));
        }
        if (dto.getCategory() == null ) {
            errors.add(new Error(CATEGORY, MAY_NOT_BE_NULL));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    private void validateDataBase(ProductDTO dto){
        List<Error> errors = new ArrayList<>();
        if (fromCategoryValue(dto.getCategory()) == null){
            errors.add(new Error(CATEGORY, NOT_ALLOWED));
        }
        if (fromUnitValue(dto.getUnit()) == null){
            errors.add(new Error(UNIT, NOT_ALLOWED));
        }
        if (productRepository.findByNameIgnoreCaseAndProducerIgnoreCase(dto.getName(), dto.getProducer()) != null){
            errors.add(new Error(PRODUCT, ALREADY_EXIST));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
}
