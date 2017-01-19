package application.repository;

import application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kkurowska on 18.01.2017.
 */

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByNameIgnoreCase(String name);
}
