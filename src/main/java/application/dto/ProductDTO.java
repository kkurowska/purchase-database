package application.dto;

/**
 * Created by kkurowska on 16.12.2016.
 */
public class ProductDTO {

    private Long id;
    private String name;
    private String category;

    public ProductDTO() {
    }

    public ProductDTO(String name, String category) {
        this.name = name;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
