package application.service;

import application.dto.StoreDTO;
import application.exception.ActionNotAllowedException;
import application.exception.StoreNotFoundException;
import application.exception.ValidationException;
import application.model.Purchase;
import application.model.Store;
import application.repository.PurchaseRepository;
import application.repository.StoreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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
    @Mock
    private PurchaseRepository purchaseRepository;
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
        String name = new String("Store");
        storeDTO.setName(name);
        storeDTO.setId(null);
        store.setId(id);
        store.setName(name);
        when(storeRepository.save(any(Store.class))).thenReturn(store);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);
        assertEquals(id, storeService.addStore(storeDTO));
    }

    @Test (expected = ValidationException.class)
    public void testAddStoreWhenIdNotNull() throws ValidationException{
        Long id = new Long(3);
        String name = new String("Store");
        storeDTO.setName(name);
        storeDTO.setId(id);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);
        storeService.addStore(storeDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddStoreWhenNameIsNull() throws ValidationException{
        storeDTO.setName(null);
        storeDTO.setId(null);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);
        storeService.addStore(storeDTO);
    }

    @Test (expected = ValidationException.class)
    public void testAddStoreWhenStoreAlreadyExist() throws ValidationException{
        Long id = new Long(3);
        String name = new String("Store");
        storeDTO.setName(name);
        storeDTO.setId(null);
        store.setName(name);
        store.setId(id);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(store);
        storeService.addStore(storeDTO);
    }

    @Test
    public void testFindStoreWhenProperArguments() throws StoreNotFoundException {
        Long id = new Long(3);
        String name = new String("Store");
        store.setId(id);
        store.setName(name);
        storeDTO.setId(id);
        storeDTO.setName(name);
        when(storeRepository.findOne(id)).thenReturn(store);
        assertTrue(storeDTO.equals(storeService.findStore(id)));
    }

    @Test (expected = StoreNotFoundException.class)
    public void testFindStoreWhenStoreNotExist() throws StoreNotFoundException {
        Long id = new Long(3);
        when(storeRepository.findOne(id)).thenReturn(null);
        storeService.findStore(id);
    }

    @Test
    public void testUpdateStoreWhenProperArguments() throws ValidationException, StoreNotFoundException{
        Long id = new Long(3);
        String name = new String("Store");
        String newName = new String("New Store");
        storeDTO.setName(newName);
        storeDTO.setId(id);
        store.setId(id);
        store.setName(name);
        Store updatedStore = new Store();
        updatedStore.setId(id);
        updatedStore.setName(newName);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);
        when(storeRepository.findOne(id)).thenReturn(store);
        when(storeRepository.save(any(Store.class))).thenReturn(updatedStore);
        assertEquals(id, storeService.updateStore(storeDTO));
    }

    @Test (expected = ValidationException.class)
    public void testUpdateStoreWhenIdNull() throws ValidationException, StoreNotFoundException{
        String newName = new String("New Store");
        storeDTO.setName(newName);
        storeDTO.setId(null);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);
        storeService.updateStore(storeDTO);
    }

    @Test (expected = ValidationException.class)
    public void testUpdateStoreWhenNameIsNull() throws ValidationException, StoreNotFoundException{
        Long id = new Long(3);
        storeDTO.setName(null);
        storeDTO.setId(id);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);
        storeService.updateStore(storeDTO);
    }

    @Test (expected = ValidationException.class)
    public void testUpdateStoreWhenNameAlreadyExist() throws ValidationException, StoreNotFoundException{
        Long id = new Long(3);
        String newName = new String("New Store");
        String name = new String("Store");
        storeDTO.setName(newName);
        storeDTO.setId(id);
        store.setId(id);
        store.setName(name);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(store);
        storeService.updateStore(storeDTO);
    }

    @Test (expected = StoreNotFoundException.class)
    public void testUpdateStoreWhenStoreNotExist() throws ValidationException, StoreNotFoundException{
        Long id = new Long(3);
        String newName = new String("New Store");
        String name = new String("Store");
        storeDTO.setName(newName);
        storeDTO.setId(id);
        store.setId(id);
        store.setName(name);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);
        when(storeRepository.findOne(id)).thenReturn(null);
        storeService.updateStore(storeDTO);
    }

    @Test
    public void testDeleteStoreWhenProperArguments() throws StoreNotFoundException, ActionNotAllowedException {
        Long id = new Long(3);
        String name = new String("Store");
        store.setId(id);
        store.setName(name);
        when(storeRepository.findOne(id)).thenReturn(store);
        List<Purchase> purchases = new ArrayList<>();
        when(purchaseRepository.findByStore(any(Store.class))).thenReturn(purchases);
        doNothing().when(storeRepository).delete(id);
        storeService.deleteStore(id);
    }

    @Test (expected = StoreNotFoundException.class)
    public void testDeleteStoreWhenStoreNotExist() throws StoreNotFoundException, ActionNotAllowedException {
        Long id = new Long(3);
        when(storeRepository.findOne(id)).thenReturn(null);
        storeService.deleteStore(id);
    }

    @Test (expected = ActionNotAllowedException.class)
    public void testDeleteStoreWhenExistPurchasesWithThisStore() throws StoreNotFoundException, ActionNotAllowedException {
        Long id = new Long(3);
        String name = new String("Store");
        store.setId(id);
        store.setName(name);
        when(storeRepository.findOne(id)).thenReturn(store);
        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        purchases.add(purchase);
        when(purchaseRepository.findByStore(any(Store.class))).thenReturn(purchases);
        storeService.deleteStore(id);
    }
}
