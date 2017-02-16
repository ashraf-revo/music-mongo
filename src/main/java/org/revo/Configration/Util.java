package org.revo.Configration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.revo.Domain.IndexedSong;
import org.revo.Domain.User;
import org.revo.Repository.IndexedSongRepository;
import org.revo.Repository.SongRepository;
import org.revo.Repository.UserRepository;
import org.revo.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;
import javax.validation.Validator;

import static java.util.stream.Collectors.toList;

/**
 * Created by ashraf on 18/01/17.
 */
@Configuration
public class Util {
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    LoggingEventListener loggingEventListener() {
        return new LoggingEventListener();
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(Validator validator) {
        return new ValidatingMongoEventListener(validator);
    }

    @Bean
    public CookieCsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

    @Bean
    public AuditorAware<User> aware(UserService userService) {
        return userService::current;
    }

    @Bean
    CommandLineRunner runner(UserService userService, AppEnv appEnv, SongRepository songRepository, IndexedSongRepository indexedSongRepository) {
        return x -> {
            if (userService.count() == 0) {
                appEnv.getUsers().forEach(userService::save);
            }
            if ((indexedSongRepository.count() != songRepository.count()) && songRepository.count() > 0) {
                indexedSongRepository.deleteAll();
                indexedSongRepository.save(songRepository.findAll().stream().map(IndexedSong::indexedSong).collect(toList()));
            }
        };
    }

    @Bean
    public Cloudinary cloudinary(AppEnv appEnv) {
        AppEnv.Cloudinary cloudinary = appEnv.getCloudinary();
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinary.getName(),
                "api_key", cloudinary.getKey(),
                "api_secret", cloudinary.getSecret()));
    }


    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return s -> userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    RememberMeServices rememberMeServices(AppEnv appEnv, UserDetailsService userDetailsService, PersistentTokenRepository persistentTokenRepository) {
        return new PersistentTokenBasedRememberMeServices(appEnv.getKey(), userDetailsService, persistentTokenRepository);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}