package application;

import application.model.Category;
import application.model.Product;
import application.model.Purchase;
import application.model.Store;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kkurowska on 15.12.2016.
 */

public class Test {

    public static void main(String[] args) {
//        String name = Category.GROCERIES.name();
//        System.out.println(name);

        Product product = new Product("Milk","1 liter", Category.GROCERIES);
        Store store = new Store("Lidl");
        //Date date = new Date("2016/12/16 10:23:04");
        Date date = new Date (2016, 12, 16, 10,23, 04);
        Purchase purchase = new Purchase(product, store, 3.50, false, date);
        System.out.println(purchase.toString());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date today = new Date();
        System.out.println(dateFormat.format(today));

    }


}

