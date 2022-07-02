package com.task.favourite.recipes.serviceImpl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.favourite.recipes.dto.UsersDTO;
import com.task.favourite.recipes.entity.UserRole;
import com.task.favourite.recipes.entity.Users;
import com.task.favourite.recipes.exception.ErrorMessage;
import com.task.favourite.recipes.exception.UserException;
import com.task.favourite.recipes.repository.UserRepository;
import com.task.favourite.recipes.service.UserService;

import lombok.extern.log4j.Log4j2;
/**
 * {@link UserServiceImpl}
 * 
 * UserServiceImpl is used to write business logic for User
 * 
 * @author Vishwas_Kadam
 *
 */

@Log4j2
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * Adding new User
	 * Checking first if User is available, if not  throwing the message : Con not add, User is already exist.
	 * if User is available proceeding with save User
	 */
	@Override
	public void saveUser(UsersDTO usersDTO) throws UserException{
		log.info(" In saveUser() of  UserServiceImpl ");
		
		ErrorMessage message = new ErrorMessage();
		UserException	ex =  new UserException();
		/**
		 * checking the user if available by user name
		 */
		 Optional<Users> optionalUser = userRepository.findByUserName(usersDTO.getUserName());
         if(optionalUser.isPresent()) {
        	 log.error(" In saveUser() of  UserServiceImpl : Con not add, User is already exist.");
        	message.setStatus(HttpStatus.FOUND);
			message.setMessage("Con not add, User is already exist.");
			ex.setErrorMessage(message);
			throw ex;
         }
         /**
          * Converting UserDTO to Users Object  to save into DB
          */
         Users user = new Users();
         user.setUserName(usersDTO.getUserName());
         user.setPassword(bCryptPasswordEncoder.encode(usersDTO.getPassword()));
         user.setEmail(usersDTO.getEmail());
         user.setActive(usersDTO.getActive());
         
         
         Set<UserRole> roles = usersDTO.getRoles().stream().map(role-> new UserRole(role.getId(), role.getUserRoleId(), role.getUserRoleName()))
        		 .collect(Collectors.toSet());
         /**
 		 * saving the user
 		 */
         user.setUserRole(roles);
         try {
        	 userRepository.save(user);
         } catch (Exception exe) {
        	log.error(" In saveUser() of  UserServiceImpl :Something went wrong while saving User!");
 			message.setStatus(HttpStatus.BAD_REQUEST);
 			message.setMessage("Something went wrong while saving User!");
 			ex.setErrorMessage(message);
 			throw ex;
 		}
	}
}
