package lab1.emt.model.events;

import lombok.Getter;
import lab1.emt.model.Books;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class ProductCreatedEvent extends ApplicationEvent {

    private LocalDateTime when;

    public ProductCreatedEvent(Books source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public ProductCreatedEvent(Books source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
