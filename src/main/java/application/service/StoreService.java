package application.service;

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

}
