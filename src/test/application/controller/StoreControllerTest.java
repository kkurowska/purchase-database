package application.controller;

import application.dto.StoreDTO;
import application.exception.*;
import application.exception.Error;
import application.model.Store;
import application.service.GlobalExceptionService;
import application.service.StoreService;
import application.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorField.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by kkurowska on 05.01.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class StoreControllerTest {

    private MockMvc mockMvc;

    private GlobalExceptionService globalExceptionService;
    private GlobalExceptionHandler globalExceptionHandler;
    private Messages msg = new EnglishMessages();

    private StoreDTO storeDTO;
    @Mock
    private StoreService storeService;
    @InjectMocks
    private StoreController storeController;

    @Before
    public void setupMock() {
        globalExceptionService = new GlobalExceptionService();
        globalExceptionHandler = new GlobalExceptionHandler(globalExceptionService);
        storeDTO = new StoreDTO();
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(storeController).setControllerAdvice(globalExceptionHandler).build();
    }

    @Test
    @WithUserDetails("admin")
    public void testAddStoreWhenProperArguments() throws Exception {
        Long id = new Long(3);
        String name = new String("Store");
        storeDTO.setName(name);
        storeDTO.setId(null);
        when(storeService.addStore(org.mockito.Matchers.any(StoreDTO.class))).thenReturn(id);
        try {
            mockMvc.perform(post("/store/addStore")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
                    .andExpect(status().isOk())
                    .andExpect(content().string("3"));
            verify(storeService, times(1)).addStore(org.mockito.Matchers.any(StoreDTO.class));
            verifyNoMoreInteractions(storeService);
        } catch (Exception e) {
            System.out.println("Sth is wrong in testAddStoreWhenProperArguments " + e.getMessage());
        }
    }

    @Test
    @WithUserDetails("admin")
    public void testAddStoreWhenIdNotNull() throws Exception {
        Long id = new Long(3);
        String name = new String("Store");
        storeDTO.setName(name);
        storeDTO.setId(id);
        when(storeService.addStore(org.mockito.Matchers.any(StoreDTO.class))).thenThrow(new MyRuntimeException(new Error(ID, MAY_NOT_BE_NULL)));
        try {
            mockMvc.perform(post("/store/addStore")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(msg.getMessage(ID, MAY_NOT_BE_NULL)));
            verify(storeService, times(1)).addStore(org.mockito.Matchers.any(StoreDTO.class));
            verifyNoMoreInteractions(storeService);
        } catch (Exception e) {
            System.out.println("Sth is wrong in testAddStoreWhenIdNotNull " + e.getMessage());
        }
    }

    @Test
    @WithUserDetails("admin")
    public void testGetStoreWhenProperArguments() throws Exception {
        Long id = new Long(3);
        String name = new String("Store");
        storeDTO.setName(name);
        storeDTO.setId(id);
        when(storeService.findStore(anyLong())).thenReturn(storeDTO);
        try {
            mockMvc.perform(get("/store/getStore/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.id", is(3)))
                    .andExpect(jsonPath("$.name", is(name)));
            verify(storeService, times(1)).findStore(id);
            verifyNoMoreInteractions(storeService);

        } catch (Exception e) {
            System.out.println("Sth is wrong in testGetStoreWhenProperArguments " + e.getMessage());
        }
    }

    @Test
    @WithUserDetails("admin")
    public void testGetStoreWhenStoreNotExist() throws Exception {
        Long id = new Long(3);
        when(storeService.findStore(anyLong())).thenThrow(new MyRuntimeException(new Error(STORE, NOT_FOUND)));
        try {
            mockMvc.perform(get("/store/getStore/{id}", id))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(msg.getMessage(STORE, NOT_FOUND)));
            verify(storeService, times(1)).findStore(id);
            verifyNoMoreInteractions(storeService);

        } catch (Exception e) {
            System.out.println("Sth is wrong in testGetStoreWhenStoreNotExist " + e.getMessage());
        }
    }

    @Test
    public void testUpdateStoreWhenProperArguments() throws Exception {
        Long id = new Long(3);
        String name = new String("Store");
        storeDTO.setName(name);
        storeDTO.setId(null);
        when(storeService.updateStore(org.mockito.Matchers.any(StoreDTO.class))).thenReturn(id);
        try {
            mockMvc.perform(post("/store/updateStore")
                    .with(user("admin").roles("ROLE_ADMIN"))
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
                    .andExpect(status().isOk())
                    .andExpect(content().string("3"));
            verify(storeService, times(1)).updateStore(org.mockito.Matchers.any(StoreDTO.class));
            verifyNoMoreInteractions(storeService);
        } catch (Exception e) {
            System.out.println("Sth is wrong in testUpdateStoreWhenProperArguments " + e.getMessage());
        }
    }
}
