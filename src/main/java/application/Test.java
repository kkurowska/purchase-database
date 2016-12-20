package application;

import application.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kkurowska on 15.12.2016.
 */

public class Test {

    public static void main(String[] args) {
//        String name = Category.GROCERIES.name();
//        System.out.println(name);

        Product product = new Product("Milk", Unit.LITER, Category.GROCERIES);
        Store store = new Store("Lidl");
        //Date date = new Date("2016/12/16 10:23:04");
        Date date = new Date (2016-1900, 12-1, 16);
        Purchase purchase = new Purchase(product, store, 3.50, false, date);
        System.out.println(purchase.toString());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        System.out.println(dateFormat.format(today));
        System.out.println(today.toLocaleString());
        System.out.println(today);

        String s = new String("2016-12-20 09:26:12");
        Date ds = new Date();
        try {
            ds = dateFormat.parse(s);
            System.out.println(ds);
        } catch (ParseException e){
            System.out.println("Nie udało się");
        }

        Date date2 = new Date();
        date2.setYear(2016-1900);
        date2.setMonth(12-1);
        date2.setDate(20);
        System.out.println(date2.toLocaleString());

    }


}

