package com.springboot.config;

//SecurityConfig.java

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

 @Override
 protected void configure(HttpSecurity http) throws Exception {
     http.csrf().disable()
         .authorizeRequests()
         .antMatchers("/auth/signup").permitAll()
         .antMatchers("/auth/login").permitAll()
         .anyRequest().authenticated()
         .and().sessionManagement().disable();
 }

 @Override
 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     auth.inMemoryAuthentication()
         .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
         .and()
         .withUser("user").password(passwordEncoder().encode("user123")).roles("USER");
 }

 @Bean
 public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
 }

 @Override
 @Bean
 public AuthenticationManager authenticationManagerBean() throws Exception {
     return super.authenticationManagerBean();
 }
}

