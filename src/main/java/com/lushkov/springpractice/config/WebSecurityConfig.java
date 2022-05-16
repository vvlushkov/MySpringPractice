package com.lushkov.springpractice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.lushkov.springpractice")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/user/**").hasAuthority("user")
                .antMatchers("/admin/**").hasAuthority("admin")
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()

                .formLogin()
                .loginPage("/").permitAll()
                .usernameParameter("login")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/default")
                .permitAll()
                .and()

                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/").permitAll()
                .and()

                .exceptionHandling().accessDeniedPage("/WEB-INF/views/errors/error.jsp");
    }
}