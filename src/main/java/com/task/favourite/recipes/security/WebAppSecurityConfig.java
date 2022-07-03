package com.task.favourite.recipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.task.favourite.recipes.util.CommonConstants;

@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    
    @Override
    public void configure(WebSecurity web)  {
        web.ignoring().antMatchers("/management/health");
    }
    
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.csrf() 
		.disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.DELETE).hasAnyAuthority(CommonConstants.ROLE_ADMIN)
		.antMatchers(HttpMethod.POST).hasAnyAuthority(CommonConstants.ROLE_ADMIN)
		.antMatchers(HttpMethod.PUT).hasAnyAuthority(CommonConstants.ROLE_ADMIN)
		.antMatchers(HttpMethod.GET).hasAnyAuthority(CommonConstants.ROLE_ADMIN, CommonConstants.ROLE_VIEWER)
		.anyRequest().authenticated()
		.and().httpBasic();
	}
}
