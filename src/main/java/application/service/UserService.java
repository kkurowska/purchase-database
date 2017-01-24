package application.service;

import application.dto.PasswordDTO;
import application.dto.UserDTO;
import application.exception.*;
import application.exception.Error;
import application.model.User;
import application.repository.UserRepository;
import application.utils.UserRoles;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorField.*;
import static application.utils.UserRoles.fromUserRolesValue;

/**
 * Created by kkurowska on 18.01.2017.
 */

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private int maxLength = 20;
    private Messages msg = new EnglishMessages();

    public Long createUser(UserDTO dto) {
        validate(dto);
        validateDataBase(dto);
        User user = new User();
        user.setName(dto.getName());
        String password = dto.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setUserRole(UserRoles.valueOf(dto.getUserRole()));
        return userRepository.save(user).getId();
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setPassword(user.getPassword());
            dto.setUserRole(user.getUserRole().getValue());
            dtos.add(dto);
        }
        return dtos;
    }

    public String changePassword(PasswordDTO dto) {
        validate(dto);
        User user = userRepository.findByNameIgnoreCase(dto.getName());
        if (user == null) {
            throw new MyRuntimeException(new Error(USER, NOT_FOUND));
        }
        if (!user.getPassword().equals(passwordEncoder.encode(dto.getOldPassword()))) {
            throw new MyRuntimeException(new Error(OLD_PASSWORD, WRONG));
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        return msg.getMessage(PASSWORD, CHANGED);
    }

    private void validate(UserDTO dto) {
        List<Error> errors = new ArrayList<>();
        if (dto.getId() != null) {
            errors.add(new Error(ID, NOT_ALLOWED));
        }
        if (dto.getName() == null || StringUtils.isBlank(dto.getName())) {
            errors.add(new Error(NAME, MAY_NOT_BE_NULL));
        } else if (dto.getName().length() > maxLength) {
            errors.add(new Error(NAME, NOT_ALLOWED));
        }
        if (dto.getPassword() == null || StringUtils.isBlank(dto.getPassword())) {
            errors.add(new Error(PASSWORD, MAY_NOT_BE_NULL));
        } else if (dto.getPassword().length() > maxLength) {
            errors.add(new Error(PASSWORD, NOT_ALLOWED));
        }
        if (dto.getUserRole() == null) {
            errors.add(new Error(USER_ROLE, MAY_NOT_BE_NULL));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateDataBase(UserDTO dto) {
        List<Error> errors = new ArrayList<>();
        if (fromUserRolesValue(dto.getUserRole()) == null) {
            errors.add(new Error(USER_ROLE, NOT_ALLOWED));
        }

        if (userRepository.findByNameIgnoreCase(dto.getName()) != null) {
            errors.add(new Error(USER, ALREADY_EXIST));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validate(PasswordDTO dto) {
        List<Error> errors = new ArrayList<>();
        if (dto.getName() == null || StringUtils.isBlank(dto.getName())) {
            errors.add(new Error(NAME, MAY_NOT_BE_NULL));
        }
        if (dto.getOldPassword() == null || StringUtils.isBlank(dto.getOldPassword())) {
            errors.add(new Error(OLD_PASSWORD, MAY_NOT_BE_NULL));
        }
        if (dto.getNewPassword() == null || StringUtils.isBlank(dto.getNewPassword())) {
            errors.add(new Error(NEW_PASSWORD, MAY_NOT_BE_NULL));
        } else if (dto.getNewPassword().length() > maxLength) {
            errors.add(new Error(NEW_PASSWORD, NOT_ALLOWED));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

}
