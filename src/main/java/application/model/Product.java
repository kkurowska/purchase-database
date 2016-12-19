package application.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kkurowska on 13.12.2016.
 */

@Entity
public class Product{


    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    private String unit;

    @Column(name = "producer")
    private String producer;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Product() { }

    public Product(String name, String unit, Category category) {
        super();
        this.name = name;
        this.unit = unit;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return getName() + " [" + getUnit() + "], " + category.getValue();
    }
}
