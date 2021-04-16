package lab1.emt.service;

import lab1.emt.model.Books;
import lab1.emt.model.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface BooksService {

    List<Books> findAll();

    Optional<Books> findById(Long id);

    Optional<Books> findByName(String name);

    Optional<Books> save(String name, Double price, Integer quantity, Long category, Long manufacturer);

    Optional<Books> save(ProductDto productDto);

    Optional<Books> edit(Long id, String name, Double price, Integer quantity, Long category, Long manufacturer);

    Optional<Books> edit(Long id, ProductDto productDto);

    void deleteById(Long id);

    void refreshMaterializedView();
}
