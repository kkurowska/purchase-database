package application.controller;

import application.Application;
import application.WebSecurityConfiguration;
import application.dto.PurchaseDTO;
import application.exception.EnglishMessages;
import application.exception.Error;
import application.exception.Messages;
import application.exception.MyRuntimeException;
import application.model.User;
import application.service.GlobalExceptionService;
import application.service.ProductService;
import application.service.PurchaseService;
import application.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorField.*;
import static application.utils.UserRoles.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by kkurowska on 25.01.2017.
 */

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
public class PurchaseControllerTest {

    private MockMvc mockMvc;

    private GlobalExceptionService globalExceptionService;
    private GlobalExceptionHandler globalExceptionHandler;
    private Messages msg = new EnglishMessages();

    private PurchaseDTO purchaseDTO;
    @Mock
    private PurchaseService purchaseService;
    @InjectMocks
    private PurchaseController purchaseController;

    @Before
    public void setupMock() {
        globalExceptionService = new GlobalExceptionService();
        globalExceptionHandler = new GlobalExceptionHandler(globalExceptionService);
        purchaseDTO = new PurchaseDTO();
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(purchaseController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
//        User user = new User();
//        user.setName("admin");
//        user.setPassword("adminPass");
//        user.setUserRole(ROLE_USER);
//        Authentication authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Test
    @WithUserDetails("none")
    public void testAddPurchaseWhenProperArguments() throws Exception {
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
        when(purchaseService.addPurchase(org.mockito.Matchers.any(PurchaseDTO.class))).thenReturn(id);
        try {
            mockMvc.perform(post("/purchase/addPurchase")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseDTO))
            )
                    .andExpect(status().isOk())
                    .andExpect(content().string("3"));
            verify(purchaseService, times(1)).addPurchase(org.mockito.Matchers.any(PurchaseDTO.class));
            verifyNoMoreInteractions(purchaseService);
        } catch (Exception e) {
            System.out.println("Sth is wrong in testAddPurchaseWhenProperArguments " + e.getMessage());
        }
    }

    @Test
//    @WithUserDetails("admin")
    public void testAddPurchaseWhenIdNotNull() throws Exception {
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
        when(purchaseService.addPurchase(org.mockito.Matchers.any(PurchaseDTO.class))).thenThrow(new MyRuntimeException(new Error(ID, MAY_NOT_BE_NULL)));
        try {
            mockMvc.perform(post("/purchase/addPurchase")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseDTO))
            )
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(msg.getMessage(ID, MAY_NOT_BE_NULL)));
            verify(purchaseService, times(1)).addPurchase(org.mockito.Matchers.any(PurchaseDTO.class));
            verifyNoMoreInteractions(purchaseService);
        } catch (Exception e) {
            System.out.println("Sth is wrong in testAddPurchaseWhenWhenIdNotNull " + e.getMessage());
        }
    }

    @Test
//    @WithUserDetails("admin")
//    @WithMockUser(roles="ADMIN")
    public void testFindPurchaseWhenProperArguments() throws Exception {
        Long id = new Long(3);
        Long productId = new Long(1);
        Long storeId = new Long(1);
        double price = 5.3;
        boolean sale = false;
        String date = new String("2017/01/05 13:12:11");
        purchaseDTO.setId(id);
        purchaseDTO.setProductId(productId);
        purchaseDTO.setStoreId(storeId);
        purchaseDTO.setPrice(price);
        purchaseDTO.setSale(sale);
        purchaseDTO.setDate(date);
        when(purchaseService.findPurchase(org.mockito.Matchers.anyLong())).thenReturn(purchaseDTO);
        try {
            mockMvc.perform(get("/purchase/getPurchase/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.id", is(3)))
                    .andExpect(jsonPath("$.productId", is(1)))
                    .andExpect(jsonPath("$.storeId", is(1)))
                    .andExpect(jsonPath("$.price", is(5.3)))
                    .andExpect(jsonPath("$.sale", is(false)))
                    .andExpect(jsonPath("$.date", is(date)));
            verify(purchaseService, times(1)).findPurchase(org.mockito.Matchers.anyLong());
            verifyNoMoreInteractions(purchaseService);
        } catch (Exception e) {
            System.out.println("Sth is wrong in testFindPurchaseWhenProperArguments " + e.getMessage());
        }
    }
}
