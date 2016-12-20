package application.controller;

import application.dto.StoreDTO;
import application.service.StoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by kkurowska on 15.12.2016.
 */

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    StoreService storeService;

    @ApiOperation(value = "addStore", nickname = "addStore")
    @RequestMapping(value = "/addStore", method = POST)
    public Long addStore(@RequestBody StoreDTO dto) {
        return storeService.addStore(dto);
    }

    @ApiOperation(value = "findStore", nickname = "findStore")
    @RequestMapping(value = "/getStore/{id}", method = GET)
    public StoreDTO findStore(@PathVariable Long id) {
        return storeService.findStore(id);
    }

    @ApiOperation(value = "updateStore", nickname = "updateStore")
    @RequestMapping(value = "/updateStore", method = PUT)
    public Long updateStore(@RequestBody StoreDTO dto) { return storeService.updateStore(dto);}

    @ApiOperation(value = "deleteStore", nickname = "deleteStore")
    @RequestMapping(value = "/deleteStore/{id}", method = DELETE)
    public void deleteStore(@PathVariable Long id) { storeService.deleteStore(id);}
}
