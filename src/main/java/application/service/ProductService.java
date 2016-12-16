package application.service;

import application.model.Product;
import application.repository.ProductRepository;
import org.springframework.stereotype.Service;

/**
 * Created by kkurowska on 15.12.2016.
 */

@Service
public class ProductService {

    private ProductRepository productRepository;

    private ProductService() {
    }

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
