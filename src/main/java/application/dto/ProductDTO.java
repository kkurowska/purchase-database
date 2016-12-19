package application.dto;

/**
 * Created by kkurowska on 16.12.2016.
 */
public class ProductDTO {

    private Long id;
    private String name;
    private String producer;
    private String unit;
    private String category;

    public ProductDTO() {
    }

    public ProductDTO(String name, String producer, String unit, String category) {
        this.name = name;
        this.producer = producer;
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

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
