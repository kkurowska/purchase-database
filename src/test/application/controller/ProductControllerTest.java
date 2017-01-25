package application.controller;

import application.dto.ProductDTO;
import application.exception.EnglishMessages;
import application.exception.Error;
import application.exception.Messages;
import application.exception.MyRuntimeException;
import application.service.GlobalExceptionService;
import application.service.ProductService;
import application.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorField.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by kkurowska on 24.01.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    private GlobalExceptionService globalExceptionService;
    private GlobalExceptionHandler globalExceptionHandler;
    private Messages msg = new EnglishMessages();

    private ProductDTO productDTO;
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @Before
    public void setupMock() {
        globalExceptionService = new GlobalExceptionService();
        globalExceptionHandler = new GlobalExceptionHandler(globalExceptionService);
        productDTO = new ProductDTO();
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(productController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    @WithUserDetails("admin")
    public void testAddProductWhenProperArguments() throws Exception {
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
        when(productService.addProduct(org.mockito.Matchers.any(ProductDTO.class))).thenReturn(id);
        try {
            mockMvc.perform(post("/product/addProduct")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
                    .andExpect(status().isOk())
                    .andExpect(content().string("3"));
            verify(productService, times(1)).addProduct(org.mockito.Matchers.any(ProductDTO.class));
            verifyNoMoreInteractions(productService);
        } catch (Exception e) {
            System.out.println("Sth is wrong in testAddProductWhenProperArguments " + e.getMessage());
        }
    }

    @Test
    @WithUserDetails("admin")
    public void testAddProductWhenIdNotNull() throws Exception {
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
        when(productService.addProduct(org.mockito.Matchers.any(ProductDTO.class))).thenThrow(new MyRuntimeException(new Error(ID, MAY_NOT_BE_NULL)));
        try {
            mockMvc.perform(post("/product/addProduct")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(msg.getMessage(ID, MAY_NOT_BE_NULL)));
            verify(productService, times(1)).addProduct(org.mockito.Matchers.any(ProductDTO.class));
            verifyNoMoreInteractions(productService);
        } catch (Exception e) {
            System.out.println("Sth is wrong in testAddProductWhenIdNotNull " + e.getMessage());
        }
    }

    @Test
    @WithUserDetails("admin")
    public void testFindProductWhenProperArguments() throws Exception {
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
        when(productService.findProduct(org.mockito.Matchers.anyLong())).thenReturn(productDTO);
        try {
            mockMvc.perform(get("/product/getProduct/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.id", is(3)))
                    .andExpect(jsonPath("$.name", is(name)))
                    .andExpect(jsonPath("$.producer", is(producer)))
                    .andExpect(jsonPath("$.unit", is(unit)))
                    .andExpect(jsonPath("$.category", is(category)));
            verify(productService, times(1)).findProduct(org.mockito.Matchers.anyLong());
            verifyNoMoreInteractions(productService);
        } catch (Exception e) {
            System.out.println("Sth is wrong in testFindProductWhenProperArguments " + e.getMessage());
        }
    }

    @Test
    @WithUserDetails("admin")
    public void testFindProductWhenProductNotExist() throws Exception {
        Long id = new Long(3);
        when(productService.findProduct(org.mockito.Matchers.anyLong())).thenThrow(new MyRuntimeException(new Error(PRODUCT, NOT_FOUND)));
        try {
            mockMvc.perform(get("/product/getProduct/{id}", id))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(msg.getMessage(PRODUCT, NOT_FOUND)));
            verify(productService, times(1)).findProduct(org.mockito.Matchers.anyLong());
            verifyNoMoreInteractions(productService);
        } catch (Exception e) {
            System.out.println("Sth is wrong in testFindProductWhenProductNotExist " + e.getMessage());
        }
    }
}
