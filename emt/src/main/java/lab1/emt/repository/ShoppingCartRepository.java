package lab1.emt.repository;

import lab1.emt.model.enumerations.ShoppingCartStatus;
import lab1.emt.model.ShoppingCart;
import lab1.emt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);
}
