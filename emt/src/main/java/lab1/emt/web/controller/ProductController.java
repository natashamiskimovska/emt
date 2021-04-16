package lab1.emt.web.controller;

import lab1.emt.model.Author;
import lab1.emt.model.Books;
import lab1.emt.service.BooksService;
import lab1.emt.model.Category;
import lab1.emt.service.CategoryService;
import lab1.emt.service.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final BooksService booksService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public ProductController(BooksService booksService,
                             CategoryService categoryService,
                             AuthorService authorService) {
        this.booksService = booksService;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Books> books = this.booksService.findAll();
        model.addAttribute("products", books);
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.booksService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.booksService.findById(id).isPresent()) {
            Books books = this.booksService.findById(id).get();
            List<Author> authors = this.authorService.findAll();
            List<Category> categories = this.categoryService.listCategories();
            model.addAttribute("manufacturers", authors);
            model.addAttribute("categories", categories);
            model.addAttribute("product", books);
            model.addAttribute("bodyContent", "add-product");
            return "master-template";
        }
        return "redirect:/products?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model) {
        List<Author> authors = this.authorService.findAll();
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("manufacturers", authors);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-product");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduct(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam Long category,
            @RequestParam Long manufacturer) {
        if (id != null) {
            this.booksService.edit(id, name, price, quantity, category, manufacturer);
        } else {
            this.booksService.save(name, price, quantity, category, manufacturer);
        }
        return "redirect:/products";
    }
}
