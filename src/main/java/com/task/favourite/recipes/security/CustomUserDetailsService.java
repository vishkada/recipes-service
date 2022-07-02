package com.task.favourite.recipes.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.favourite.recipes.entity.Users;
import com.task.favourite.recipes.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository  userRepository;
	
	@Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
	
  @Override 
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException { 
	  Optional<Users> optionalUser = userRepository.findByUserName(userName); 
	  Users users=null;
	  if(optionalUser.isPresent()) {
        	 users  = optionalUser.get();
	  }  else {
        	throw new UsernameNotFoundException("User Name is not Found");
        } 
       return new RecipeUserDetails(users);
  }
}
