package lab1.emt.librarian;

import lab1.emt.model.events.ProductCreatedEvent;
import lab1.emt.service.BooksService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventHandlers {

    private final BooksService booksService;

    public ProductEventHandlers(BooksService booksService) {
        this.booksService = booksService;
    }

    @EventListener
    public void onProductCreated(ProductCreatedEvent event) {
        this.booksService.refreshMaterializedView();
    }
}
