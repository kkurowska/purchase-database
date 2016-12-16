package application.repository;

import application.model.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kkurowska on 15.12.2016.
 */

public interface ProductRepository extends CrudRepository<Product, Long>, JpaSpecificationExecutor {

//    List<Product> findByName(String name);
//    List<Product> findByCategory(String category);
}
