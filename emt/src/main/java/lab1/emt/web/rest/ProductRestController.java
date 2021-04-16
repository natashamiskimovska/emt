package lab1.emt.web.rest;

import lab1.emt.model.Books;
import lab1.emt.model.dto.ProductDto;
import lab1.emt.service.BooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/products")
public class ProductRestController {

    private final BooksService booksService;

    public ProductRestController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    private List<Books> findAll() {
        return this.booksService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Books> findById(@PathVariable Long id) {
        return this.booksService.findById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Books> save(@RequestBody ProductDto productDto) {
        return this.booksService.save(productDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Books> save(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return this.booksService.edit(id, productDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.booksService.deleteById(id);
        if(this.booksService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}

