package application.service;

import application.dto.StoreDTO;
import application.exception.ActionNotAllowedException;
import application.exception.StoreNotFoundException;
import application.exception.ValidationError;
import application.exception.ValidationException;
import application.model.Purchase;
import application.model.Store;
import application.repository.PurchaseRepository;
import application.repository.StoreRepository;
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

    public Long addStore(StoreDTO dto){
        validate(dto);
        Store store = new Store();
        store.setName(dto.getName());
        return storeRepository.save(store).getId();
    }

    public StoreDTO findStore(Long id){
        Store store = storeRepository.findOne(id);
        if (store == null){
            throw new StoreNotFoundException();
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
            throw new StoreNotFoundException();
        }
        store.setName(dto.getName());
        return storeRepository.save(store).getId();
    }

    public void deleteStore(Long id){
        Store store = storeRepository.findOne(id);
        if (store == null){
            throw new StoreNotFoundException();
        }
        List<Purchase> purchases = purchaseRepository.findByStore(store);
        if (purchases.isEmpty()) {
            storeRepository.delete(id);
        } else {
            throw new ActionNotAllowedException();
        }
    }

    private void validate(StoreDTO dto){
        List<ValidationError> errors = new ArrayList<>();
        if (dto.getId() != null) {
            errors.add(new ValidationError(ID, NOT_ALLOWED));
        }
        if (dto.getName() == null) {
            errors.add(new ValidationError(NAME, MAY_NOT_BE_NULL));
        } else if (storeRepository.findByNameIgnoreCase(dto.getName()) != null){
            errors.add(new ValidationError(STORE, ALREADY_EXIST));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    private void validateUpdate(StoreDTO dto){
        List<ValidationError> errors = new ArrayList<>();
        if (dto.getId() == null) {
            errors.add(new ValidationError(ID, MAY_NOT_BE_NULL));
        } else if (dto.getId() <= 0){
            errors.add(new ValidationError(ID, NOT_ALLOWED));
        }
        if (dto.getName() == null) {
            errors.add(new ValidationError(NAME, MAY_NOT_BE_NULL));
        } else if (storeRepository.findByNameIgnoreCase(dto.getName()) != null){
            errors.add(new ValidationError(STORE, ALREADY_EXIST));
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
