package application.security;

import application.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by kkurowska on 18.01.2017.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LOG.info("User {} trying to log in.", s);
        UserDetails ud = userRepository.findByNameIgnoreCase(s);
        if(ud == null) {
            throw new UsernameNotFoundException("could not find the user '"
                    + s + "'");
        }
        return ud;
    }
}
