package com.bwongo.core.base.config;

import com.bwongo.core.security.model.SecurityUserDetails;
import com.bwongo.core.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

/**
 * @Author bkaaron
 * @Date 3/10/24
 * @LocalTime 6:03 PM
 **/
@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {

    private final SecurityUserService securityUserService;

    @Bean
    public UserDetailsService userDetailsService(){
        return this::getMappedCustomUserDetails;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    private SecurityUserDetails getMappedCustomUserDetails(String username){
        return securityUserService.getSecurityUserDetails(username);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer(){

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .maxAge(3600)
                        .allowCredentials(false)//When true "*" not allowed
                        .allowedMethods(HttpMethod.GET.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.PATCH.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.OPTIONS.name(),
                                HttpMethod.DELETE.name())
                        .allowedHeaders(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaders.AUTHORIZATION,
                                HttpHeaders.ORIGIN,
                                HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                                HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,
                                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS);
            }
        };
    }

    @Bean(name = "asyncTaskExecutor")
    public Executor asyncTaskExecutor(){
        var taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setQueueCapacity(150);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setThreadNamePrefix("AsyncTaskThread");
        taskExecutor.initialize();

        return taskExecutor;
    }
}
