package lab1.emt.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private Integer quantity;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Author author;

    public Books() {
    }

    public Books(String name, Double price, Integer quantity, Category category, Author author) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.author = author;
    }
}
