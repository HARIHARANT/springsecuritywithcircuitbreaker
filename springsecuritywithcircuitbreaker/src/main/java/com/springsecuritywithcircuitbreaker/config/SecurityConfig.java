package com.springsecuritywithcircuitbreaker.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;



@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login", "/register").permitAll() // Allow public access to these URLs
                .anyRequest().authenticated() // Require authentication for all other URLs
            .and()
            .formLogin()
               .loginPage("/login") // Custom login page URL
                .permitAll() // Allow everyone to access the login page
            .and()
            .logout()
                .permitAll(); // Allow everyone to logout
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("{noop}admin").roles("ADMIN").build());
        manager.createUser(User.withUsername("user").password("{noop}user").roles("USER").build());
        return manager;
    }
}
