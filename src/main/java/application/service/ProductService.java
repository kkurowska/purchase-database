package application.service;

import application.model.Product;
import application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kkurowska on 15.12.2016.
 */

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

}
