package application.controller;

import application.dto.ProductDTO;
import application.exception.EnglishMessages;
import application.exception.Messages;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    private StoreController storeController;

    @Before
    public void setupMock() {
        globalExceptionService = new GlobalExceptionService();
        globalExceptionHandler = new GlobalExceptionHandler(globalExceptionService);
        productDTO = new ProductDTO();
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(storeController)
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
}
