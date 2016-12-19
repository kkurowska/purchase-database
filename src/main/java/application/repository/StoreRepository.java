package application.repository;

import application.model.Store;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kkurowska on 15.12.2016.
 */


@Repository
public interface StoreRepository extends CrudRepository<Store, Long>, JpaSpecificationExecutor {

    Store findByName(String name);
}
