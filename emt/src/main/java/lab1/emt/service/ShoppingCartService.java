package lab1.emt.service;

import lab1.emt.model.Books;
import lab1.emt.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    List<Books> listAllProductsInShoppingCart(Long cartId);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart addProductToShoppingCart(String username, Long productId);
}
