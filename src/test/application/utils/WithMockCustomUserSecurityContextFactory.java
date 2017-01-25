package application.utils;

import application.controller.StoreControllerTest;
import application.model.User;
import application.utils.UserRoles;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * Created by kkurowska on 25.01.2017.
 */
public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<StoreControllerTest.WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(StoreControllerTest.WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetails principal = new User(customUser.username(), customUser.password(), UserRoles.valueOf(customUser.authority()));
//        principal.
//        principal.setName(customUser.username());
//        principal.setPassword(customUser.password());
//        principal.setUserRole(UserRoles.valueOf(customUser.authorities()[0]));
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal.getUsername(), principal.getPassword(), principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}