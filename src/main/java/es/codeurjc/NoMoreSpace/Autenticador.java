package es.codeurjc.NoMoreSpace;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.codeurjc.NoMoreSpace.services.UserDependencies;
import es.codeurjc.NoMoreSpace.model.User;
import es.codeurjc.NoMoreSpace.repository.UserRepository;

@Component
public class Autenticador implements UserDetailsService
{

	@Autowired
	private UserRepository repo;
	@Autowired
	private UserDependencies userOP;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException
	{
		//String name=auth.getName();
		List<User> candidatos=repo.findByUsername(name);
		if(candidatos.size()>1)
			throw new UsernameNotFoundException("Error UserName, multiple");
		if(candidatos.size()<1)
			throw new UsernameNotFoundException("Error UserName, not exist");
		
		User usuario=candidatos.get(0);
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		if(usuario.isAdmin())
			roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		String passwd= usuario.getPassword();
		passwd= passwordEncoder.encode(usuario.getPassword()).toString();
		return new org.springframework.security.core.userdetails.User(name,passwd,roles);
	}
}
