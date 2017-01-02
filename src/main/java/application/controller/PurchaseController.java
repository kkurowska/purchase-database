package application.controller;

import application.dto.ProductDTO;
import application.dto.PurchaseDTO;
import application.service.PurchaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by kkurowska on 15.12.2016.
 */

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @ApiOperation(value = "addPurchase", nickname = "addPurchase")
    @RequestMapping(value = "/addPurchase", method = POST)
    public Long addPurchase(@RequestBody PurchaseDTO dto) {
        return purchaseService.addPurchase(dto);
    }

//    @ApiOperation(value = "addPurchaseByName", nickname = "addPurchaseByName")
//    @RequestMapping(value = "/addPurchaseByName", method = POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "productName", value = "productName", required = true, dataType = "String",
//                    paramType = "query"),
//            @ApiImplicitParam(name = "producer", value = "producer", required = true, dataType = "String",
//                    paramType = "query"),
//            @ApiImplicitParam(name = "storeName", value = "storeName", required = true, dataType = "String",
//                    paramType = "query")
//    })
//    public Long addPurchaseByName(@RequestBody PurchaseDTO dto, @PathParam("productName") String productName, @PathParam("producer") String producer, @PathParam("storeName") String storeName) {
//        return purchaseService.addPurchaseByName(dto, productName, producer, storeName);
//    }

    @ApiOperation(value = "findPurchase", nickname = "findPurchase")
    @RequestMapping(value = "/getPurchase/{id}", method = GET)
    public PurchaseDTO findPurchase(@PathVariable Long id) {
        return purchaseService.findPurchase(id);
    }

    @ApiOperation(value = "deletePurchase", nickname = "deletePurchase")
    @RequestMapping(value = "/deletePurchase/{id}", method = DELETE)
    public void deletePurchase(@PathVariable Long id) { purchaseService.deletePurchase(id);}

    @ApiOperation(value = "minimalProductPrice", nickname = "minimalProductPrice")
    @RequestMapping(value = "/minimalProductPrice/{productId}", method = GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "startDate", required = false, dataType = "String",
                    paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "endDate", required = false, dataType = "String",
                    paramType = "query")
    })
    public double  minimalProductPrice(@PathVariable Long productId, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { return purchaseService.minimalProductPrice(productId, startDate, endDate);}


    @ApiOperation(value = "averageProductPrice", nickname = "averageProductPrice")
    @RequestMapping(value = "/averageProductPrice/{productId}", method = GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "startDate", required = false, dataType = "String",
                    paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "endDate", required = false, dataType = "String",
                    paramType = "query")
    })
    public double  averageProductPrice(@PathVariable Long productId, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { return purchaseService. averageProductPrice(productId, startDate, endDate);}
}
