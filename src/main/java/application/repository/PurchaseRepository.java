package application.repository;

import application.model.Purchase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * Created by kkurowska on 15.12.2016.
 */

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

}
