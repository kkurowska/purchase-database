package application.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kkurowska on 13.12.2016.
 */

@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @OneToMany(mappedBy = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    private String unit;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    protected Product() { }

    public Product(String name, String unit, Category category) {
        super();
        this.name = name;
        this.unit = unit;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return getName() + " [" + getUnit() + "], " + category.getName();
    }
}
