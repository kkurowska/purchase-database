package application.dto;

/**
 * Created by kkurowska on 16.12.2016.
 */
public class StoreDTO {

    private Long id;
    private String name;

    public StoreDTO() {
    }

    public StoreDTO(String name) {
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

    public boolean equals(StoreDTO other){
        if (this == null && other == null) {
            return true;
        } else if (this !=null && other!= null) {
            if (this.id == other.getId() && this.name == other.getName()) {
                return true;
            }
        }
        return false;
    }
}
