package application;

import application.security.PasswordEncoderImpl;
import application.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by kkurowska on 18.01.2017.
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/swagger-ui.html",  "/webjars/**", "/swagger-resources", "/v2/api-docs" ,"/configuration/**").permitAll()
                .anyRequest().authenticated();
        http
                .formLogin()
                .loginPage("/login")
//                .failureHandler((request, response, exception) -> response.sendError(HttpStatus.BAD_REQUEST.value(), "Username or password invalid"))
                .usernameParameter("user")
                .passwordParameter("password")
                .defaultSuccessUrl("/users/success")
                .permitAll();
        http
                .logout()
                .invalidateHttpSession(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin").password("adminPass").roles("ADMIN")
                .and()
                .withUser("user").password("userPass").roles("USER");
    }

//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }
//
//    @Bean
//    public PasswordEncoder encoder() {
//        final ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
//        encoder.setEncodeHashAsBase64(true);
//        encoder.setIterations(1024);
//        return new PasswordEncoderImpl(encoder);
//    }

}
