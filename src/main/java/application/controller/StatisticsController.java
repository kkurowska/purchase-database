package application.controller;

import application.service.StatisticsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by kkurowska on 04.01.2017.
 */

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @ApiOperation(value = "minimalPrice", nickname = "minimalPrice")
    @RequestMapping(value = "/minimalPrice/{productId}", method = GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "startDate", required = false, dataType = "String",
                    paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "endDate", required = false, dataType = "String",
                    paramType = "query")
    })
    public double  minimalProductPrice(@PathVariable Long productId, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { return statisticsService.minimalProductPrice(productId, startDate, endDate);}


    @ApiOperation(value = "averagePrice", nickname = "averagePrice")
    @RequestMapping(value = "/averagePrice/{productId}", method = GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "startDate", required = false, dataType = "String",
                    paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "endDate", required = false, dataType = "String",
                    paramType = "query")
    })
    public double  averageProductPrice(@PathVariable Long productId, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) { return statisticsService.averageProductPrice(productId, startDate, endDate);}
}

