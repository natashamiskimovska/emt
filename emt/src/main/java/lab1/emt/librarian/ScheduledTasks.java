package lab1.emt.librarian;

import lab1.emt.service.BooksService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final BooksService booksService;

    public ScheduledTasks(BooksService booksService) {
        this.booksService = booksService;
    }

    @Scheduled(fixedDelay = 5000)
    public void refreshMaterializedView() {
        //this.productService.refreshMaterializedView();
    }
}
