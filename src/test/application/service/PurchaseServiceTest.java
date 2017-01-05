package application.service;

import application.dto.PurchaseDTO;
import application.exception.PurchaseNotFoundException;
import application.exception.ValidationException;
import application.exception.WrongDateFormatException;
import application.model.Product;
import application.model.Purchase;
import application.model.Store;
import application.repository.ProductRepository;
import application.repository.PurchaseRepository;
import application.repository.StoreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static application.model.Category.*;
import static application.model.Unit.*;
import static application.utils.MyDateFormat.MY_DATE_FORMAT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by kkurowska on 05.01.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {

    private DateFormat dateFormat = new SimpleDateFormat(MY_DATE_FORMAT.getValue());
    private Purchase purchase;
    private PurchaseDTO purchaseDTO;
    private Product product;
    private Store store;
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private StoreRepository storeRepository;
    @InjectMocks
    private PurchaseService purchaseService;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        purchase = new Purchase();
        purchaseDTO = new PurchaseDTO();
        product = new Product("Banana", "Chiquita", KG, GROCERIES);
        store = new Store("Store");
    }

    @Test
    public void testAddPurchaseWhenProperArguments() throws ValidationException {
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        product.setId(productId);
        store.setId(storeId);
        purchase.setId(id);
        purchase.setProduct(product);
        purchase.setStore(store);
        purchase.setPrice(price);
        purchase.setSale(sale);
        try {
            Date date = dateFormat.parse(stringDate);
            purchase.setDate(date);
        } catch (ParseException e){
            System.out.println("Wrong date format!");
        }
        when(productRepository.exists(anyLong())).thenReturn(true);
        when(storeRepository.exists(anyLong())).thenReturn(true);
        when(productRepository.findOne(anyLong())).thenReturn(product);
        when(storeRepository.findOne(anyLong())).thenReturn(store);
        when(purchaseRepository.findByProductAndStoreAndPriceAndSaleAndDate(any(Product.class), any(Store.class), anyDouble(), anyBoolean(), any(Date.class))).thenReturn(null);
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);
        assertEquals(id, purchaseService.addPurchase(purchaseDTO));
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenIdNotNull() throws ValidationException {
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(id);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenProductIdNull() throws ValidationException {
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(null);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenProductIdNotPositive() throws ValidationException {
        Long productId = new Long(-1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenStoreIdNull() throws ValidationException {
        Long productId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(null);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenStoreIdNotPositive() throws ValidationException {
        Long productId = new Long(1);
        Long storeId = new Long(-1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenPriceIsNull() throws ValidationException {
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(0);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenPriceIsNegative() throws ValidationException {
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = -5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenDateIsNull() throws ValidationException {
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(null);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenProductNotExist() throws ValidationException {
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        store.setId(storeId);
        when(productRepository.exists(anyLong())).thenReturn(false);
        when(storeRepository.exists(anyLong())).thenReturn(true);
        when(productRepository.findOne(anyLong())).thenReturn(null);
        when(storeRepository.findOne(anyLong())).thenReturn(store);
        when(purchaseRepository.findByProductAndStoreAndPriceAndSaleAndDate(any(Product.class), any(Store.class), anyDouble(), anyBoolean(), any(Date.class))).thenReturn(null);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenStoreNotExist() throws ValidationException {
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        product.setId(productId);
        when(productRepository.exists(anyLong())).thenReturn(true);
        when(storeRepository.exists(anyLong())).thenReturn(false);
        when(productRepository.findOne(anyLong())).thenReturn(product);
        when(storeRepository.findOne(anyLong())).thenReturn(null);
        when(purchaseRepository.findByProductAndStoreAndPriceAndSaleAndDate(any(Product.class), any(Store.class), anyDouble(), anyBoolean(), any(Date.class))).thenReturn(null);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = WrongDateFormatException.class)
    public void testAddPurchaseWhenDateWrongFormat() throws ValidationException, WrongDateFormatException {
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("-2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        product.setId(productId);
        store.setId(storeId);
        when(productRepository.exists(anyLong())).thenReturn(true);
        when(storeRepository.exists(anyLong())).thenReturn(true);
        when(productRepository.findOne(anyLong())).thenReturn(product);
        when(storeRepository.findOne(anyLong())).thenReturn(store);
        when(purchaseRepository.findByProductAndStoreAndPriceAndSaleAndDate(any(Product.class), any(Store.class), anyDouble(), anyBoolean(), any(Date.class))).thenReturn(null);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddPurchaseWhenPurchaseAlreadyExist() throws ValidationException {
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(null);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        product.setId(productId);
        store.setId(storeId);
        when(productRepository.exists(anyLong())).thenReturn(true);
        when(storeRepository.exists(anyLong())).thenReturn(true);
        when(productRepository.findOne(anyLong())).thenReturn(product);
        when(storeRepository.findOne(anyLong())).thenReturn(store);
        when(purchaseRepository.findByProductAndStoreAndPriceAndSaleAndDate(any(Product.class), any(Store.class), anyDouble(), anyBoolean(), any(Date.class))).thenReturn(purchase);
        purchaseService.addPurchase(purchaseDTO);
    }

    @Test
    public  void testFindPurchaseWhenProperArguments() throws PurchaseNotFoundException{
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(id);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(stringDate);
        product.setId(productId);
        store.setId(storeId);
        purchase.setId(id);
        purchase.setProduct(product);
        purchase.setStore(store);
        purchase.setPrice(price);
        purchase.setSale(sale);
        try {
            Date date = dateFormat.parse(stringDate);
            purchase.setDate(date);
        } catch (ParseException e){
            System.out.println("Wrong date format!");
        }
        when(purchaseRepository.findOne(id)).thenReturn(purchase);
        PurchaseDTO foundPurchaseDTO = purchaseService.findPurchase(id);
        assertTrue(purchaseDTO.equals(foundPurchaseDTO));

    }

    @Test (expected = PurchaseNotFoundException.class)
    public  void testFindPurchaseWhenPurchaseNotExist() throws PurchaseNotFoundException{
        Long id = new Long(3);
        when(purchaseRepository.findOne(id)).thenReturn(null);
        purchaseService.findPurchase(id);
    }

    @Test
    public void testDeleteProductWhenProperArguments() throws PurchaseNotFoundException{
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String stringDate = new String("2017/01/05 13:12:11");
        product.setId(productId);
        store.setId(storeId);
        purchase.setId(id);
        purchase.setProduct(product);
        purchase.setStore(store);
        purchase.setPrice(price);
        purchase.setSale(sale);
        when(purchaseRepository.findOne(id)).thenReturn(purchase);
        doNothing().when(purchaseRepository).delete(id);
        purchaseService.deletePurchase(id);
    }

    @Test (expected = PurchaseNotFoundException.class)
    public void testDeleteProductWhenPurchaseNotExist() throws PurchaseNotFoundException{
        Long id = new Long(3);
        when(purchaseRepository.findOne(id)).thenReturn(null);
        purchaseService.deletePurchase(id);
    }

}
