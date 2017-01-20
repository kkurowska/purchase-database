package application.service;

import application.dto.StoreDTO;
import application.exception.ActionNotAllowedException;
import application.exception.Error;
import application.exception.MyRuntimeException;
import application.exception.ValidationException;
import application.model.Purchase;
import application.model.Store;
import application.repository.PurchaseRepository;
import application.repository.StoreRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorField.*;

/**
 * Created by kkurowska on 15.12.2016.
 */

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private int maxLength = 20;

    public Long addStore(StoreDTO dto){
        validate(dto);
        Store store = new Store();
        store.setName(dto.getName());
        return storeRepository.save(store).getId();
    }

    public StoreDTO findStore(Long id){
        Store store = storeRepository.findOne(id);
        if (store == null){
            throw new MyRuntimeException(new Error(STORE, NOT_FOUND));
        }
        StoreDTO dto = new StoreDTO();
        dto.setId(store.getId());
        dto.setName(store.getName());
        return dto;
    }

    public Long updateStore(StoreDTO dto){
        validateUpdate(dto);
        Store store = storeRepository.findOne(dto.getId());
        if (store == null){
            throw new MyRuntimeException(new Error(STORE, NOT_FOUND));
        }
        store.setName(dto.getName());
        return storeRepository.save(store).getId();
    }

    public void deleteStore(Long id){
        Store store = storeRepository.findOne(id);
        if (store == null){
            throw new MyRuntimeException(new Error(STORE, NOT_FOUND));
        }
        List<Purchase> purchases = purchaseRepository.findByStore(store);
        if (purchases.isEmpty()) {
            storeRepository.delete(id);
        } else {
            throw new ActionNotAllowedException();
        }
    }

    public List<StoreDTO> findAll() {
        List<Store> stores = storeRepository.findAll();
        List<StoreDTO> dtos = new ArrayList<>();
        for (int i = 0; i < stores.size(); i++) {
            Store store = stores.get(i);
            StoreDTO dto = new StoreDTO();
            dto.setId(store.getId());
            dto.setName(store.getName());
            dtos.add(dto);
        }
        return dtos;
    }

    private void validate(StoreDTO dto){
        List<Error> errors = new ArrayList<>();
        if (dto.getId() != null) {
            errors.add(new Error(ID, NOT_ALLOWED));
        }
        if (dto.getName() == null || StringUtils.isBlank(dto.getName())) {
            errors.add(new Error(NAME, MAY_NOT_BE_NULL));
        } else if (dto.getName().length() > maxLength) {
            errors.add(new Error(NAME, NOT_ALLOWED));
        } else if (storeRepository.findByNameIgnoreCase(dto.getName()) != null){
            errors.add(new Error(STORE, ALREADY_EXIST));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    private void validateUpdate(StoreDTO dto){
        List<Error> errors = new ArrayList<>();
        if (dto.getId() == null) {
            errors.add(new Error(ID, MAY_NOT_BE_NULL));
        } else if (dto.getId() <= 0){
            errors.add(new Error(ID, NOT_ALLOWED));
        }
        if (dto.getName() == null || StringUtils.isBlank(dto.getName())) {
            errors.add(new Error(NAME, MAY_NOT_BE_NULL));
        } else if (dto.getName().length() > maxLength) {
            errors.add(new Error(NAME, NOT_ALLOWED));
        } else if (storeRepository.findByNameIgnoreCase(dto.getName()) != null){
            errors.add(new Error(STORE, ALREADY_EXIST));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    public void setStoreRepository(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public StoreService() {
    }
}
