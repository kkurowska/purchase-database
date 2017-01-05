package application.service;

import application.exception.ProductNotFoundException;
import application.exception.PurchaseNotFoundException;
import application.exception.WrongDateFormatException;
import application.model.Product;
import application.model.Purchase;
import application.repository.ProductRepository;
import application.repository.PurchaseRepository;
import application.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static application.utils.MyDateFormat.MY_DATE_FORMAT;

/**
 * Created by kkurowska on 04.01.2017.
 */

@Service
public class StatisticsService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private PurchaseService purchaseService;

    private DateFormat dateFormat = new SimpleDateFormat(MY_DATE_FORMAT.getValue());

    public double minimalProductPrice(Long productId, String start, String end){
        Product product = productRepository.findOne(productId);
        if (product == null){
            throw new ProductNotFoundException();
        }
        Purchase purchase = new Purchase();
        try {
            if (start == null && end == null) {
                purchase = purchaseRepository.findTopByProductOrderByPriceAsc(product);
            } else if (start == null && end != null) {
                Date endDate = dateFormat.parse(end);
                purchaseService.checkParseDate(end, endDate);
                purchase = purchaseRepository.findTopByProductAndDateLessThanOrderByPriceAsc(product, endDate);
            } else if (start != null && end == null) {
                Date startDate = dateFormat.parse(start);
                purchaseService.checkParseDate(start, startDate);
                purchase = purchaseRepository.findTopByProductAndDateGreaterThanOrderByPriceAsc(product, startDate);
            } else {
                Date startDate = dateFormat.parse(start);
                purchaseService.checkParseDate(start, startDate);
                Date endDate = dateFormat.parse(end);
                purchaseService.checkParseDate(end, endDate);
                purchase = purchaseRepository.findTopByProductAndDateBetweenOrderByPriceAsc(product, startDate, endDate);
            }
            if (purchase == null){
                throw new PurchaseNotFoundException();
            }
            double minimalPrice = purchase.getPrice();
            return minimalPrice;
        } catch (ParseException e){
            throw new WrongDateFormatException();
        }
    }

    public double averageProductPrice(Long productId, String start, String end){
        Product product = productRepository.findOne(productId);
        if (product == null){
            throw new ProductNotFoundException();
        }
        List<Purchase> purchases = new ArrayList<Purchase>();
        try {
            if (start == null && end == null) {
                purchases = purchaseRepository.findByProduct(product);
            } else if (start == null && end != null) {
                Date endDate = dateFormat.parse(end);
                purchaseService.checkParseDate(end, endDate);
                purchases = purchaseRepository.findByProductAndDateLessThan(product, endDate);
            } else if (start != null && end == null) {
                Date startDate = dateFormat.parse(start);
                purchaseService.checkParseDate(start, startDate);
                purchases = purchaseRepository.findByProductAndDateGreaterThan(product, startDate);
            } else {
                Date startDate = dateFormat.parse(start);
                purchaseService.checkParseDate(start, startDate);
                Date endDate = dateFormat.parse(end);
                purchaseService.checkParseDate(end, endDate);
                purchases = purchaseRepository.findByProductAndDateBetween(product, startDate, endDate);
            }
            if (purchases.isEmpty()) {
                throw new PurchaseNotFoundException();
            }
            List<Double> prices = new ArrayList<Double>();
            double averagePrice = 0;
            for (int i = 0; i < purchases.size(); i++) {
                averagePrice += purchases.get(i).getPrice();
            }
            averagePrice = averagePrice / purchases.size();
            return averagePrice;
        } catch (ParseException e){
            throw new WrongDateFormatException();
        }
    }

}
