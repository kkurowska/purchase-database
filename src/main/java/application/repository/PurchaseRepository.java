package application.repository;

import application.model.Product;
import application.model.Purchase;
import application.model.Store;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * Created by kkurowska on 15.12.2016.
 */

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

    Purchase findByProductAndStoreAndPriceAndSaleAndDate(Product product, Store store, double price, boolean sale, Date date);
    List<Purchase> findByProduct(Product product);
    List<Purchase> findByProductOrderByPriceAsc(Product product);
}
