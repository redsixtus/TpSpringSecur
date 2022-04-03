package com.diginamic.security.projetdemo.security;

import com.diginamic.security.projetdemo.service.UserService;
import com.diginamic.security.projetdemo.dao.AppAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true,prePostEnabled = true,securedEnabled = true)
public class ConfigSecurity extends WebSecurityConfigurerAdapter {


@Autowired
UserService userS;
//je branche mon service de typer UserDetailsService avec AuthenticationManagerBuilder
@Override
protected  void configure (AuthenticationManagerBuilder auth)throws Exception{
    auth.userDetailsService(userS);
}
@Bean
public AuthenticationProvider getProvider(){
    AppAuthProvider provider= new AppAuthProvider();
    provider.setUserDetailsService(userS);
    return provider;
}

@Override
    protected void configure(HttpSecurity http) throws Exception{
//   //j'autorise tout !!!
//    http.csrf().disable().
//    authorizeRequests().
//    anyRequest().
//    permitAll().and().httpBasic();
    //http.csrf().disable()
    http
            .authenticationProvider(getProvider())
            .formLogin().loginProcessingUrl("/login")
            .and()
            .logout().logoutUrl("/logout")
            .invalidateHttpSession(true)
            .and()
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/logout").authenticated()
            .antMatchers("api/**").permitAll()//accée libre pour les controller REST
            .anyRequest().authenticated()
            .and()
            .httpBasic();// on n'envoie pas le formulaire de login aux controller Rest
}

}
