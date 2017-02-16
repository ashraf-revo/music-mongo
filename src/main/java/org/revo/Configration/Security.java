package org.revo.Configration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.revo.Domain.Role.SONG;
import static org.revo.Domain.Role.USER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * Created by ashraf on 14/12/16.
 */
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder encoder;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final RememberMeServices rememberMeServices;
    private final AppEnv appEnv;
    private final CsrfTokenRepository csrfTokenRepository;


    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public Security(PasswordEncoder encoder, UserDetailsService userDetailsService, UserService userService, RememberMeServices rememberMeServices, AppEnv appEnv, CsrfTokenRepository csrfTokenRepository) {
        this.encoder = encoder;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.rememberMeServices = rememberMeServices;
        this.appEnv = appEnv;
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.setStatus(SC_UNAUTHORIZED))
                .and().authorizeRequests()
                .antMatchers(POST, USER.getPath()).permitAll()
                .antMatchers(GET, USER.getPath() + "/**/*").permitAll()

                .antMatchers(SONG.getPath() + "/**/*").permitAll()

                .antMatchers(GET, USER.getPath()).authenticated()

                .antMatchers(POST, USER.getPath() + "/update").authenticated()


                .antMatchers(POST, SONG.getPath()).authenticated()
                .antMatchers(POST, SONG.getPath()+"/like").authenticated()
                .antMatchers(POST, SONG.getPath()+"/unlike").authenticated()
                .antMatchers(POST, SONG.getPath()+"/view").authenticated()


                .and().formLogin()
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json");
                    response.getWriter().write(mapper.writeValueAsString(userService.current()));
                    response.setStatus(SC_OK);
                })
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\":\"" + exception.getMessage() + "\"}");
                    response.setStatus(SC_UNAUTHORIZED);
                })
                .and().logout()
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> this.csrfTokenRepository.saveToken(this.csrfTokenRepository.generateToken(httpServletRequest), httpServletRequest, httpServletResponse))
                .and().rememberMe().rememberMeServices(rememberMeServices).key(appEnv.getKey())
                .and().csrf().csrfTokenRepository(this.csrfTokenRepository);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}
