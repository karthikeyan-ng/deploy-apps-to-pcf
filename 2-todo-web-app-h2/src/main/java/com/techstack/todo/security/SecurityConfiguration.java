package com.techstack.todo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication().withUser("pascal").password("{noop}dummy")
                .roles("USER", "ADMIN");

        auth.inMemoryAuthentication().withUser("thomas").password("{noop}dummy")
                .roles("USER", "ADMIN");

        auth.inMemoryAuthentication().withUser("sara").password("{noop}dummy")
                .roles("USER", "ADMIN");
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/login", "/h2-console/**").permitAll()
                    .antMatchers("/", "/*todo*/**").access("hasRole('USER')")
                .and()
                .formLogin();

        http.headers().frameOptions().disable();
    }
}
