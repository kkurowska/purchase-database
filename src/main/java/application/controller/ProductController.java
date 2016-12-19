package application.controller;

import application.dto.ProductDTO;
import application.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by kkurowska on 15.12.2016.
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation(value = "addProduct", nickname = "addProduct")
    @RequestMapping(value = "/addProduct", method = POST)
    public Long addProduct(@RequestBody ProductDTO dto) {
        return productService.addProduct(dto);
    }

    @ApiOperation(value = "findProduct", nickname = "findProduct")
    @RequestMapping(value = "/get/{id}", method = GET)
    public ProductDTO findProduct(@PathVariable Long id) {
        return productService.findProduct(id);
    }

}
