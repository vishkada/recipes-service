package com.task.favourite.recipes.usercontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.task.favourite.recipes.controller.UserController;
import com.task.favourite.recipes.dto.UserRoleDTO;
import com.task.favourite.recipes.dto.UsersDTO;
import com.task.favourite.recipes.entity.UserRole;
import com.task.favourite.recipes.entity.Users;
import com.task.favourite.recipes.repository.UserRepository;

@SpringBootTest
public class UserTest {

	@Autowired
	private UserController userController;
	
	@MockBean
	private  UserRepository userRepository;
	
	@Test
	public void testAddUser () throws Exception {
		UserRoleDTO  roleDTO = new UserRoleDTO();
		roleDTO.setId(1);
		roleDTO.setUserRoleId(1);
		roleDTO.setUserRoleName("ADMIN");
		Set<UserRoleDTO> roles = new HashSet<>();
		roles.add(roleDTO);
		
		UsersDTO userDto = new UsersDTO();
		userDto.setUserName("admin");
		userDto.setPassword("password");
		userDto.setRoles(roles);
		
		UserRole  role = new UserRole();
		role.setId(1);
		role.setUserRoleId(1);
		role.setUserRoleName("VIEVER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(role);
		
		Users user = new Users();
		user.setUserName("viewe");
		user.setPassword("password");
		user.setUserRole(userRoles);
		
		Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn( Optional.of(new Users()));
		Mockito.when(userRepository.save(Mockito.any(Users.class))).thenReturn(user);
		try {
			UsersDTO userDTO = new UsersDTO();
			String result = userController.addUser(userDTO);
			assertEquals("User is added successfully",result);
		} catch(Exception e) {
			
		}
	}
}
