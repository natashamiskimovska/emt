package lab1.emt.service.impl;

import lab1.emt.model.Author;
import lab1.emt.model.events.ProductCreatedEvent;
import lab1.emt.repository.views.ProductsPerManufacturerViewRepository;
import lab1.emt.service.BooksService;
import lab1.emt.model.Category;
import lab1.emt.model.dto.ProductDto;
import lab1.emt.model.exceptions.CategoryNotFoundException;
import lab1.emt.model.Books;
import lab1.emt.model.exceptions.AuthorNotFoundException;
import lab1.emt.model.exceptions.BookNotFoundException;
import lab1.emt.repository.CategoryRepository;
import lab1.emt.repository.AuthorRepository;
import lab1.emt.repository.BooksRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final ProductsPerManufacturerViewRepository productsPerManufacturerViewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public BooksServiceImpl(BooksRepository booksRepository,
                            AuthorRepository authorRepository,
                            CategoryRepository categoryRepository,
                            ProductsPerManufacturerViewRepository productsPerManufacturerViewRepository,
                            ApplicationEventPublisher applicationEventPublisher) {
        this.booksRepository = booksRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.productsPerManufacturerViewRepository = productsPerManufacturerViewRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Books> findAll() {
        return this.booksRepository.findAll();
    }

    @Override
    public Optional<Books> findById(Long id) {
        return this.booksRepository.findById(id);
    }

    @Override
    public Optional<Books> findByName(String name) {
        return this.booksRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Books> save(String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Author author = this.authorRepository.findById(manufacturerId)
                .orElseThrow(() -> new AuthorNotFoundException(manufacturerId));

        this.booksRepository.deleteByName(name);
        Books books = new Books(name, price, quantity, category, author);
        this.booksRepository.save(books);
        //this.refreshMaterializedView();

        this.applicationEventPublisher.publishEvent(new ProductCreatedEvent(books));
        return Optional.of(books);
    }

    @Override
    public Optional<Books> save(ProductDto productDto) {
        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        Author author = this.authorRepository.findById(productDto.getManufacturer())
                .orElseThrow(() -> new AuthorNotFoundException(productDto.getManufacturer()));

        this.booksRepository.deleteByName(productDto.getName());
        Books books = new Books(productDto.getName(), productDto.getPrice(), productDto.getQuantity(), category, author);
        this.booksRepository.save(books);
        //this.refreshMaterializedView();

        this.applicationEventPublisher.publishEvent(new ProductCreatedEvent(books));
        return Optional.of(books);
    }

    @Override
    @Transactional
    public Optional<Books> edit(Long id, String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {

        Books books = this.booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        books.setName(name);
        books.setPrice(price);
        books.setQuantity(quantity);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        books.setCategory(category);

        Author author = this.authorRepository.findById(manufacturerId)
                .orElseThrow(() -> new AuthorNotFoundException(manufacturerId));
        books.setManufacturer(author);

        this.booksRepository.save(books);
        return Optional.of(books);
    }

    @Override
    public Optional<Books> edit(Long id, ProductDto productDto) {
        Books books = this.booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        books.setName(productDto.getName());
        books.setPrice(productDto.getPrice());
        books.setQuantity(productDto.getQuantity());

        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        books.setCategory(category);

        Author author = this.authorRepository.findById(productDto.getManufacturer())
                .orElseThrow(() -> new AuthorNotFoundException(productDto.getManufacturer()));
        books.setManufacturer(author);

        this.booksRepository.save(books);
        return Optional.of(books);
    }

    @Override
    public void deleteById(Long id) {
        this.booksRepository.deleteById(id);
    }

    @Override
    public void refreshMaterializedView() {
        this.productsPerManufacturerViewRepository.refreshMaterializedView();
    }
}
