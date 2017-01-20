package application.controller;

import application.dto.StoreDTO;
import application.model.Store;
import application.service.StoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/updateStore", method = PUT)
    public Long updateStore(@RequestBody StoreDTO dto) { return storeService.updateStore(dto);}

    @ApiOperation(value = "deleteStore", nickname = "deleteStore")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/deleteStore/{id}", method = DELETE)
    public void deleteStore(@PathVariable Long id) { storeService.deleteStore(id);}

    @RequestMapping(value = "/get/all", method = GET)
    public List<StoreDTO> findAll() {
        return storeService.findAll();
    }
}
