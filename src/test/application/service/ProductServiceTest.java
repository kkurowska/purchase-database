package application.service;

import application.dto.ProductDTO;
import application.exception.ActionNotAllowedException;
import application.exception.ProductNotFoundException;
import application.exception.StoreNotFoundException;
import application.exception.ValidationException;
import application.model.Category;
import application.model.Product;
import application.model.Purchase;
import application.model.Unit;
import application.repository.ProductRepository;
import application.repository.PurchaseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static application.model.Category.*;
import static application.model.Unit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkurowska on 05.01.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private Product product;
    private ProductDTO productDTO;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private PurchaseRepository purchaseRepository;
    @InjectMocks
    private ProductService productService;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        product = new Product();
        productDTO = new ProductDTO();
    }

    @Test
    public void testAddProductWhenProperArguments() throws ValidationException {
        Long id = new Long(3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(null);
        productDTO.setName(name);
        productDTO.setProducer(producer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        product.setId(id);
        product.setName(name);
        product.setProducer(producer);
        product.setUnit(KG);
        product.setCategory(GROCERIES);
        when(productRepository.findByNameIgnoreCaseAndProducerIgnoreCase(anyString(), anyString())).thenReturn(null);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        assertEquals(id, productService.addProduct(productDTO));
    }

    @Test (expected = ValidationException.class)
    public void testAddProductWhenIdNotNull() throws ValidationException {
        Long id = new Long(3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setProducer(producer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        productService.addProduct(productDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddProductWhenNameIsNull() throws ValidationException {
        String producer = new String("Chiquita");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(null);
        productDTO.setName(null);
        productDTO.setProducer(producer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        productService.addProduct(productDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddProductWhenProducerIsNull() throws ValidationException {
        String name = new String("Banana");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(null);
        productDTO.setName(name);
        productDTO.setProducer(null);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        productService.addProduct(productDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddProductWhenUnitIsNull() throws ValidationException {
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String category = new String("GROCERIES");
        productDTO.setId(null);
        productDTO.setName(name);
        productDTO.setProducer(producer);
        productDTO.setUnit(null);
        productDTO.setCategory(category);
        productService.addProduct(productDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddProductWhenCategoryIsNull() throws ValidationException {
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String unit = new String("KG");
        productDTO.setId(null);
        productDTO.setName(name);
        productDTO.setProducer(producer);
        productDTO.setUnit(unit);
        productDTO.setCategory(null);
        productService.addProduct(productDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddProductWhenCategoryNotAllowed() throws ValidationException {
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String unit = new String("KG");
        String category = new String("FRUITS");
        productDTO.setId(null);
        productDTO.setName(name);
        productDTO.setProducer(producer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        when(productRepository.findByNameIgnoreCaseAndProducerIgnoreCase(anyString(), anyString())).thenReturn(null);
        productService.addProduct(productDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddProductWhenUnitNotAllowed() throws ValidationException {
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String unit = new String("KILO");
        String category = new String("GROCERIES");
        productDTO.setId(null);
        productDTO.setName(name);
        productDTO.setProducer(producer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        when(productRepository.findByNameIgnoreCaseAndProducerIgnoreCase(anyString(), anyString())).thenReturn(null);
        productService.addProduct(productDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddProductWhenProductAlreadyExist() throws ValidationException {
        Long id = new Long(3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(null);
        productDTO.setName(name);
        productDTO.setProducer(producer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        product.setId(id);
        product.setName(name);
        product.setProducer(producer);
        product.setUnit(KG);
        product.setCategory(GROCERIES);
        when(productRepository.findByNameIgnoreCaseAndProducerIgnoreCase(anyString(), anyString())).thenReturn(product);
        productService.addProduct(productDTO);
    }

    @Test
    public void testFindProductWhenProperArguments() throws ProductNotFoundException {
        Long id = new Long(3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setProducer(producer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        product.setId(id);
        product.setName(name);
        product.setProducer(producer);
        product.setUnit(KG);
        product.setCategory(GROCERIES);
        when(productRepository.findOne(id)).thenReturn(product);
        assertTrue(productDTO.equals(productService.findProduct(id)));
    }

    @Test (expected = ProductNotFoundException.class)
    public void testFindProductWhenProductNotExist() throws ProductNotFoundException {
        Long id = new Long(3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setProducer(producer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        when(productRepository.findOne(id)).thenReturn(null);
        productService.findProduct(id);
    }

    @Test
    public void testUpdateProductWhenProperArguments() throws ValidationException, ProductNotFoundException{
        Long id = new Long(3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String newProducer = new String("unknown");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setProducer(newProducer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        product.setId(id);
        product.setName(name);
        product.setProducer(producer);
        product.setUnit(KG);
        product.setCategory(GROCERIES);
        Product updatedProduct = new Product();
        updatedProduct.setId(id);
        updatedProduct.setName(name);
        updatedProduct.setProducer(newProducer);
        updatedProduct.setUnit(KG);
        updatedProduct.setCategory(GROCERIES);
        when(productRepository.findByNameIgnoreCaseAndProducerIgnoreCase(anyString(), anyString())).thenReturn(null);
        when(productRepository.findOne(id)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        assertEquals(id, productService.updateProduct(productDTO));
    }

    @Test (expected = ValidationException.class)
    public void testUpdateProductWhenIdNull() throws ValidationException, ProductNotFoundException{
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String newProducer = new String("unknown");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(null);
        productDTO.setName(name);
        productDTO.setProducer(newProducer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        productService.updateProduct(productDTO);
    }

    @Test (expected = ValidationException.class)
    public void testUpdateProductWhenIdIsNotPositive() throws ValidationException, ProductNotFoundException{
        Long id = new Long(-3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String newProducer = new String("unknown");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setProducer(newProducer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        productService.updateProduct(productDTO);
    }

    @Test (expected = ValidationException.class)
    public void testUpdateProductWhenNameAlreadyExist() throws ValidationException, ProductNotFoundException{
        Long id = new Long(3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String newProducer = new String("unknown");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setProducer(newProducer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        product.setId(id);
        product.setName(name);
        product.setProducer(producer);
        product.setUnit(KG);
        product.setCategory(GROCERIES);
        when(productRepository.findByNameIgnoreCaseAndProducerIgnoreCase(anyString(), anyString())).thenReturn(product);
        productService.updateProduct(productDTO);
    }

    @Test (expected = ProductNotFoundException.class)
    public void testUpdateProductWhenProductNotExist() throws ValidationException, ProductNotFoundException{
        Long id = new Long(3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        String newProducer = new String("unknown");
        String unit = new String("KG");
        String category = new String("GROCERIES");
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setProducer(newProducer);
        productDTO.setUnit(unit);
        productDTO.setCategory(category);
        when(productRepository.findByNameIgnoreCaseAndProducerIgnoreCase(anyString(), anyString())).thenReturn(null);
        when(productRepository.findOne(id)).thenReturn(null);
        productService.updateProduct(productDTO);
    }

    @Test
    public void testDeleteProductWhenProperArguments() throws ProductNotFoundException, ActionNotAllowedException {
        Long id = new Long(3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        product.setId(id);
        product.setName(name);
        product.setProducer(producer);
        product.setUnit(KG);
        product.setCategory(GROCERIES);
        when(productRepository.findOne(id)).thenReturn(product);
        List<Purchase> purchases = new ArrayList<>();
        when(purchaseRepository.findByProduct(any(Product.class))).thenReturn(purchases);
        doNothing().when(productRepository).delete(id);
        productService.deleteProduct(id);
    }

    @Test (expected = ProductNotFoundException.class)
    public void testDeleteProductWhenProductNotExist() throws ProductNotFoundException, ActionNotAllowedException {
        Long id = new Long(3);
        when(productRepository.findOne(id)).thenReturn(null);
        productService.deleteProduct(id);
    }

    @Test (expected = ActionNotAllowedException.class)
    public void testDeleteProductWhenExistPurchasesWithThisProduct() throws ProductNotFoundException, ActionNotAllowedException {
        Long id = new Long(3);
        String name = new String("Banana");
        String producer = new String("Chiquita");
        product.setId(id);
        product.setName(name);
        product.setProducer(producer);
        product.setUnit(KG);
        product.setCategory(GROCERIES);
        when(productRepository.findOne(id)).thenReturn(product);
        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        purchases.add(purchase);
        when(purchaseRepository.findByProduct(any(Product.class))).thenReturn(purchases);
        productService.deleteProduct(id);
    }
}
