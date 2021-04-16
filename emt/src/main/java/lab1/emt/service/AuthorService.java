package lab1.emt.service;

import lab1.emt.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> findById(Long id);

    List<Author> findAll();

    Optional<Author> save(String name, String address);

    void deleteById(Long id);
}
