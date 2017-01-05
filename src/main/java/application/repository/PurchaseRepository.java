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
    List<Purchase> findByStore(Store store);
    List<Purchase> findByProduct(Product product);
    List<Purchase> findByProductAndDateBetween(Product product, Date start, Date end);
    List<Purchase> findByProductAndDateLessThan(Product product, Date end);
    List<Purchase> findByProductAndDateGreaterThan(Product product, Date start);
    Purchase findTopByProductOrderByPriceAsc(Product product);
    Purchase findTopByProductAndDateBetweenOrderByPriceAsc(Product product, Date start, Date end);
    Purchase findTopByProductAndDateLessThanOrderByPriceAsc(Product product, Date end);
    Purchase findTopByProductAndDateGreaterThanOrderByPriceAsc(Product product, Date start);
}
