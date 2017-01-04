package application.service;

import application.dto.StoreDTO;
import application.exception.ValidationException;
import application.model.Store;
import application.repository.StoreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by kkurowska on 03.01.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {

    private StoreDTO storeDTO;
    private Store store;
    @Mock
    private StoreRepository storeRepository;
    @InjectMocks
    private StoreService storeService;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        storeDTO = new StoreDTO();
        store = new Store();
    }

    @Test
    public void testAddStoreWhenProperArguments() throws ValidationException{
        Long id = new Long(3);
        String name = new String("New Store");
        storeDTO.setName(name);
        storeDTO.setId(null);
        store.setId(id);
        store.setName(name);
        when(storeRepository.save(any(Store.class))).thenReturn(store);
        when(storeRepository.findByNameIgnoreCase(name)).thenReturn(null);
        assertEquals(id, storeService.addStore(storeDTO));
    }

    @Test (expected = ValidationException.class)
    public void testAddStoreWhenIdNotNull() throws ValidationException{
        Long id = new Long(3);
        String name = new String("New Store");
        storeDTO.setName(name);
        storeDTO.setId(id);
        when(storeRepository.findByNameIgnoreCase(name)).thenReturn(null);
        storeService.addStore(storeDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddStoreWhenNameIsNull() throws ValidationException{
        storeDTO.setName(null);
        storeDTO.setId(null);
        when(storeRepository.findByNameIgnoreCase(null)).thenReturn(null);
        storeService.addStore(storeDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddStoreWhenNameAlreadyExist() throws ValidationException{
        Long id = new Long(3);
        String name = new String("New Store");
        storeDTO.setName(name);
        storeDTO.setId(null);
        store.setName(name);
        store.setId(id);
        when(storeRepository.findByNameIgnoreCase(name)).thenReturn(store);
        storeService.addStore(storeDTO);
    }

    @Test
    public void testFindStoreWhenProperArguments() throws ValidationException {
        Long id = new Long(3);
        String name = new String("New Store");
        store.setId(id);
        store.setName(name);
        storeDTO.setId(id);
        storeDTO.setName(name);
        when(storeRepository.findOne(id)).thenReturn(store);
        assertTrue(storeDTO.equals(storeService.findStore(id)));
    }
}
