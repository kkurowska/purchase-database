package application.controller;

import application.dto.PasswordDTO;
import application.dto.UserDTO;
import application.model.User;
import application.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by kkurowska on 18.01.2017.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "success";
    }

    @ApiOperation(value = "addUser", nickname = "addUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/addUser", method = POST)
    public Long createUser(@RequestBody UserDTO dto) {
        return userService.createUser(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/get/all", method = GET)
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @RequestMapping(value = "/changePassword", method = POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void changePassword(@RequestBody PasswordDTO dto) {
        userService.changePassword(dto);
    }
}
