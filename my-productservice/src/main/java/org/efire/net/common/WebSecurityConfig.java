package org.efire.net.common;

import org.efire.net.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String API_V1_PRODUCTS = "/api/v1/products";
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, API_V1_PRODUCTS).hasRole("ADMIN")
                //.mvcMatchers(HttpMethod.GET, API_V1_PRODUCTS+"/{id}").hasAnyRole("USER", "ADMIN")
                //Use regular expression to only accept 'numeric' for the path variable 'code'
                .mvcMatchers(HttpMethod.GET, API_V1_PRODUCTS).hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.GET, API_V1_PRODUCTS+"/{id:^\\d+$}").hasAnyRole("USER", "ADMIN")
                .anyRequest().denyAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
