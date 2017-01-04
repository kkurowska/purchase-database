package application;

import application.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static application.utils.MyDateFormat.MY_DATE_FORMAT;

/**
 * Created by kkurowska on 15.12.2016.
 */

public class Test {

    public static void main(String[] args) {
//        String name = Category.GROCERIES.name();
//        System.out.println(name);
//
//        Product product = new Product("Milk", Unit.LITER, Category.GROCERIES);
//        Store store = new Store("Lidl");
//        //Date date = new Date("2016/12/16 10:23:04");
//        Date date = new Date (2016-1900, 12-1, 16);
//        Purchase purchase = new Purchase(product, store, 3.50, false, date);
//        System.out.println(purchase.toString());
//
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date today = new Date();
//        System.out.println(dateFormat.format(today));
//        System.out.println(today.toLocaleString());
//        System.out.println(today);
//
//        String s = new String("2016-12-20 09:26:12");
//        Date ds = new Date();
//        try {
//            ds = dateFormat.parse(s);
//            System.out.println(ds);
//        } catch (ParseException e){
//            System.out.println("Nie udało się");
//        }
//
//        Date date2 = new Date();
//        date2.setYear(2016-1900);
//        date2.setMonth(12-1);
//        date2.setDate(20);
//        System.out.println(date2.toLocaleString());

        DateFormat dateFormat = new SimpleDateFormat(MY_DATE_FORMAT.getValue());
        String dateS1 = "0116--10-30 12:02:35";
        String dateS2 = "2016-10-30 12:02:35";
        try {
            Date date1 = dateFormat.parse(dateS1);
            Date date2 = dateFormat.parse(dateS2);
            if(date1.before(date2)) {
                System.out.println("Data1 później niż data2");
            } else {
                System.out.println("Data1 wcześniej niż data2");
            }

//            String[] parts = dateS1.split(" ");
//            String[] date = parts[0].split("/");
//            String[] time = parts[1].split(":");
//            for (int i = 0; i < date.length ; i++) {
//                System.out.println(date[i]);
//            }
//            for (int i = 0; i < time.length ; i++) {
//                System.out.println(time[i]);
//            }
//            System.out.println(date1.getYear());
//            if (date1.getYear() == Integer.parseInt(date[0])){
//                System.out.println("Rok się zgadza");
//            }
            String checkDate1 = dateFormat.format(date1);
            System.out.println(checkDate1);
            if (checkDate1.equals(dateS1)){
                System.out.println("Zgadza się");
            }
            System.out.println(checkDate1.equals(dateS1));

        } catch (ParseException e){
            //  throw new WrongDateFormatException("Wrong date format, expected yyyy-MM-dd HH:mm:ss");
        }
    }


}

