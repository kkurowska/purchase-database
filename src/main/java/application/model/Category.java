package application.model;


/**
 * Created by kkurowska on 15.12.2016.
 */

public enum Category {
    GROCERIES("groceries"),
    COSMETICS("cosmetics"),
    FASHION("fashion"),
    PHARMACY("pharmacy"),
    HOME("home"),
    OFFICE("office"),
    HOBBY("hobby");

    private String name;

    Category(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
