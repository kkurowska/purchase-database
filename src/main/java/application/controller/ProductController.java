package application.controller;

import application.dto.ProductDTO;
import application.model.Product;
import application.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

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
    @RequestMapping(value = "/getProduct/{id}", method = GET)
    public ProductDTO findProduct(@PathVariable Long id) {
        return productService.findProduct(id);
    }

    @RequestMapping(value = "/get/all", method = GET)
    public List<ProductDTO> findAll() {
        return productService.findAll();
    }

    @ApiOperation(value = "updateProduct", nickname = "updateProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/updateProduct", method = PUT)
    public Long updateProduct(@RequestBody ProductDTO dto) { return productService.updateProduct(dto);}

    @ApiOperation(value = "deleteProduct", nickname = "deleteProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/deleteProduct/{id}", method = DELETE)
    public String deleteProduct(@PathVariable Long id) { return productService.deleteProduct(id);}

}
