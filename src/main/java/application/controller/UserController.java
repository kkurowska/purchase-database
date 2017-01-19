package application.controller;

import application.dto.UserDTO;
import application.model.User;
import application.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by kkurowska on 18.01.2017.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "success";
    }


    @Autowired
    UserService userService;


    @ApiOperation(value = "addUser", nickname = "addUser")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/addUser", method = POST)
    public Long createUser(@RequestBody UserDTO dto) {
        return userService.createUser(dto);
    }
}
