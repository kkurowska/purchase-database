package application.service;

import application.dto.ProductDTO;
import application.exception.*;
import application.model.Category;
import application.model.Product;
import application.model.Unit;
import application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static application.exception.ErrorMessages.ALREADY_EXIST;
import static application.exception.ErrorMessages.MAY_NOT_BE_NULL;
import static application.exception.ErrorMessages.NOT_ALLOWED;
import static application.model.Category.fromCategoryValue;
import static application.model.Unit.fromUnitValue;

/**
 * Created by kkurowska on 15.12.2016.
 */

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

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
            throw new ProductNotFoundException("Product not found");
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
            throw new ProductNotFoundException("Product not found");
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
            throw new ProductNotFoundException("Product not found");
        }
        productRepository.delete(id);
    }

    private void validate(ProductDTO dto){
        List<ValidationError> errors = new ArrayList<>();
        if (dto.getId() != null) {
            errors.add(new ValidationError("id", NOT_ALLOWED));
        }
        if (dto.getName() == null) {
            errors.add(new ValidationError("name", MAY_NOT_BE_NULL));
        }
        if (dto.getProducer() == null) {
            errors.add(new ValidationError("producer", MAY_NOT_BE_NULL));
        }
        if (dto.getUnit() == null ) {
            errors.add(new ValidationError("unit", MAY_NOT_BE_NULL));
        }
        if (dto.getCategory() == null ) {
            errors.add(new ValidationError("category", MAY_NOT_BE_NULL));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    private void validateUpdate(ProductDTO dto){
        List<ValidationError> errors = new ArrayList<>();
        if (dto.getId() == null) {
            errors.add(new ValidationError("id", MAY_NOT_BE_NULL));
        }
        if (dto.getId() <= 0){
            errors.add(new ValidationError("id", NOT_ALLOWED));
        }
        if (dto.getName() == null) {
            errors.add(new ValidationError("name", MAY_NOT_BE_NULL));
        }
        if (dto.getProducer() == null) {
            errors.add(new ValidationError("producer", MAY_NOT_BE_NULL));
        }
        if (dto.getUnit() == null ) {
            errors.add(new ValidationError("unit", MAY_NOT_BE_NULL));
        }
        if (dto.getCategory() == null ) {
            errors.add(new ValidationError("category", MAY_NOT_BE_NULL));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    private void validateDataBase(ProductDTO dto){
        List<ValidationError> errors = new ArrayList<>();
        if (fromCategoryValue(dto.getCategory()) == null){
            errors.add(new ValidationError("category", NOT_ALLOWED));
        }
        if (fromUnitValue(dto.getUnit()) == null){
            errors.add(new ValidationError("unit", NOT_ALLOWED));
        }
        if (productRepository.findByNameIgnoreCaseAndProducerIgnoreCase(dto.getName(), dto.getProducer()) != null){
            errors.add(new ValidationError("product", ALREADY_EXIST));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
}
