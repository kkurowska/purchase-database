package application.controller;

import application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kkurowska on 15.12.2016.
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
}
