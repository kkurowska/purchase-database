package application.service;

import application.dto.StoreDTO;
import application.exception.StoreNotFoundException;
import application.exception.ValidationError;
import application.exception.ValidationException;
import application.model.Store;
import application.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static application.exception.ErrorMessages.ALREADY_EXIST;
import static application.exception.ErrorMessages.MAY_NOT_BE_NULL;
import static application.exception.ErrorMessages.NOT_ALLOWED;

/**
 * Created by kkurowska on 15.12.2016.
 */

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public Long addStore(StoreDTO dto){
        validate(dto);
        Store store = new Store();
        store.setName(dto.getName());
        return storeRepository.save(store).getId();
    }

    public StoreDTO findStore(Long id){
        Store store = storeRepository.findOne(id);
        if (store == null){
            throw new StoreNotFoundException("Store not found");
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
            throw new StoreNotFoundException("Store not found");
        }
        store.setName(dto.getName());
        return storeRepository.save(store).getId();
    }

    public void deleteStore(Long id){
        Store store = storeRepository.findOne(id);
        if (store == null){
            throw new StoreNotFoundException("Store not found");
        }
        storeRepository.delete(id);
    }

    private void validate(StoreDTO dto){
        List<ValidationError> errors = new ArrayList<>();
        if (dto.getId() != null) {
            errors.add(new ValidationError("id", NOT_ALLOWED));
        }
        if (dto.getName() == null) {
            errors.add(new ValidationError("name", MAY_NOT_BE_NULL));
        }

        if (storeRepository.findByNameIgnoreCase(dto.getName()) != null){
            errors.add(new ValidationError("store", ALREADY_EXIST));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    private void validateUpdate(StoreDTO dto){
        List<ValidationError> errors = new ArrayList<>();
        if (dto.getId() == null) {
            errors.add(new ValidationError("id", MAY_NOT_BE_NULL));
        }
        if (dto.getId() <= 0){
            errors.add(new ValidationError("id", NOT_ALLOWED));
        }
        if (dto.getName() == null) {
            errors.add(new ValidationError("name", MAY_NOT_BE_NULL));
        }

        if (storeRepository.findByNameIgnoreCase(dto.getName()) != null){
            errors.add(new ValidationError("store", ALREADY_EXIST));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
}
