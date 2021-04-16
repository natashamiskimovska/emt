package lab1.emt.service.impl;

import lab1.emt.service.BooksService;
import lab1.emt.model.Books;
import lab1.emt.model.ShoppingCart;
import lab1.emt.model.User;
import lab1.emt.model.enumerations.ShoppingCartStatus;
import lab1.emt.model.exceptions.BookAlreadyInShoppingCartException;
import lab1.emt.model.exceptions.BookNotFoundException;
import lab1.emt.model.exceptions.ShoppingCartNotFoundException;
import lab1.emt.model.exceptions.UserNotFoundException;
import lab1.emt.repository.ShoppingCartRepository;
import lab1.emt.repository.UserRepository;
import lab1.emt.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final BooksService booksService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   BooksService booksService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.booksService = booksService;
    }

    @Override
    public List<Books> listAllProductsInShoppingCart(Long cartId) {
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Books books = this.booksService.findById(productId)
                .orElseThrow(() -> new BookNotFoundException(productId));
        if(shoppingCart.getProducts()
                .stream().filter(i -> i.getId().equals(productId))
                .collect(Collectors.toList()).size() > 0)
            throw new BookAlreadyInShoppingCartException(productId, username);
        shoppingCart.getProducts().add(books);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
