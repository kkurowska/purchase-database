package application.service;

import application.dto.UserDTO;
import application.model.User;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kkurowska on 18.01.2017.
 */

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Long createUser(UserDTO dto){
        User user = new User();
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        return userRepository.save(user).getId();
    }


}
