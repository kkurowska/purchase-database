package application.service;

import application.model.Purchase;
import application.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kkurowska on 15.12.2016.
 */

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;


}
