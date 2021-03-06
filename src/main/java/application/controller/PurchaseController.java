package application.controller;

import application.dto.ProductDTO;
import application.dto.PurchaseDTO;
import application.model.Purchase;
import application.service.PurchaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.websocket.server.PathParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/deletePurchase/{id}", method = DELETE)
    public String deletePurchase(@PathVariable Long id) {
        return purchaseService.deletePurchase(id);
    }

    @RequestMapping(value = "/get/all", method = GET)
    public List<PurchaseDTO> findAll() {
        return purchaseService.findAll();
    }

}