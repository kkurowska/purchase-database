package application;

import application.model.Category;
import application.model.Product;
import application.model.Purchase;
import application.model.Store;

/**
 * Created by kkurowska on 15.12.2016.
 */

public class Test {

    public static void main(String[] args) {
//        String name = Category.GROCERIES.name();
//        System.out.println(name);

        Product product = new Product("Milk","1 liter", Category.GROCERIES);
        Store store = new Store("Lidl");
        Purchase purchase = new Purchase(product, store, 3.50, false);
        System.out.println(purchase.toString());
    }

}

