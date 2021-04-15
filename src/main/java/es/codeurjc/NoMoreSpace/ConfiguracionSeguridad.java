package es.codeurjc.NoMoreSpace;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.codeurjc.NoMoreSpace.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter
{
	@Autowired
	private Autenticador lista;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		//Public pages
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/newuser").permitAll();
		http.authorizeRequests().antMatchers("/logout").permitAll();
		
		// Private pages (all other pages)
		
		//http.authorizeRequests().anyRequest().authenticated();
		http.authorizeRequests().antMatchers("/adm").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/home").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/home/newpanel").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/home/deletepanel").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/home/upfiles").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/home/downfiles").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/home/deletefiles").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/myuser").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/myuser/mail").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/myuser/password").hasAnyRole("USER");
		
		// Login form
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("username");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/home");
		http.formLogin().failureUrl("/error");
		
		// Logout
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/");
		
		// Disable CSRF at the moment
		http.csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(lista).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}
}
