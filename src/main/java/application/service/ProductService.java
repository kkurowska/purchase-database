package application.service;

import application.dto.ProductDTO;
import application.exception.CategoryIllegalArgumentException;
import application.exception.ProductExistException;
import application.exception.ProductNotFoundException;
import application.model.Category;
import application.model.Product;
import application.model.Unit;
import application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static application.model.Category.fromValue;

/**
 * Created by kkurowska on 15.12.2016.
 */

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Long addProduct(ProductDTO dto){
        validate(dto);
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
            throw new ProductNotFoundException("Product not found.");
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
        Product product = productRepository.findOne(dto.getId());
        if (product == null){
            throw new ProductNotFoundException("Product not found.");
        }
        validateUpdate(dto);
        product.setName(dto.getName());
        product.setProducer(dto.getProducer());
        product.setUnit(Unit.valueOf(dto.getUnit()));
        product.setCategory(Category.valueOf(dto.getCategory()));
        return productRepository.save(product).getId();
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findOne(id);
        if (product == null){
            throw new ProductNotFoundException("Product not found.");
        }
        productRepository.delete(id);
    }

    private void validateUpdate(ProductDTO dto) {
        //TODO
    }

    private void validate(ProductDTO dto){
        if (fromValue(dto.getCategory()) == null){
            throw new CategoryIllegalArgumentException("There is no category named '" + dto.getCategory() + "'.");
        }
        if (productRepository.findByNameAndProducer(dto.getName(), dto.getProducer()) != null){
            throw new ProductExistException("This product already exist.");
        }
    }
    //TODO isNotEmpty etc
}
