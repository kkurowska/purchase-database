package application.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kkurowska on 13.12.2016.
 */

@Entity
public class Store{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @OneToMany(mappedBy = "store_id")
    private Long id;

    @Column(name = "name")
    private String name;

    protected Store() {
    }

    public Store(String name) {
        super();
        this.name = name;
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

    @Override
    public String toString() {
        return getName();
    }
}
