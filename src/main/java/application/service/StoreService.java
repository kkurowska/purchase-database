package application.service;

import application.dto.StoreDTO;
import application.exception.StoreExistException;
import application.exception.StoreNotFoundException;
import application.model.Store;
import application.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Store store = storeRepository.findOne(dto.getId());
        if (store == null){
            throw new StoreNotFoundException("Store not found");
        }
        validateUpdate(dto);
        store.setName(dto.getName());
        return storeRepository.save(store).getId();
    }

    private void validateUpdate(StoreDTO dto) {
        //TODO
    }

    private void validate(StoreDTO dto){
        if (storeRepository.findByName(dto.getName()) != null){
            throw new StoreExistException("This store already exist.");
        }
    }

}
