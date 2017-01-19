package application.controller;

import application.dto.StoreDTO;
import application.exception.ValidationException;
import application.service.StoreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by kkurowska on 05.01.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class StoreControllerTest {

    private MockMvc mockMvc;
//    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private StoreDTO storeDTO;
    @Mock
    private StoreService storeService;
    @InjectMocks
    private StoreController storeController;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        storeDTO = new StoreDTO();
    }

    @Test
    public void testAddStoreWhenProperArguments() throws ValidationException {

    }


}
