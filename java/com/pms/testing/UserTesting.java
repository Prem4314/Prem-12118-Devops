package com.pms.testing;

import com.pms.bean.User;
import com.pms.controller.UserController;
import com.pms.repo.UserRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

	@Autowired
	private UserController userController;

	@Autowired
	private UserRepo userRepo;

	@Test
	@Order(1)
	void testRegisterUser() {
		User user = new User();
		user.setUserName("JohnDoe");
		user.setUserMobno("1234567890");

		User registeredUser = userController.registerUser(user);

		Assertions.assertNotNull(registeredUser);
		Assertions.assertNotNull(registeredUser.getUserId());
		Assertions.assertEquals(user.getUserName(), registeredUser.getUserName());
		Assertions.assertEquals(user.getUserMobno(), registeredUser.getUserMobno());
	}

	@Test
	@Order(2)
	void testGetAllUsers() {
		List<User> userList = userController.getAllUsers();

		Assertions.assertFalse(userList.isEmpty());
	}

	@Test
	@Order(3)
	void testUpdateUser() {
		Long userId = 1L;
		User updatedUser = new User();
		updatedUser.setUserName("UpdatedName");
		updatedUser.setUserMobno("9876543210");

		User result = userController.updateUser(userId, updatedUser);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(updatedUser.getUserName(), result.getUserName());
		Assertions.assertEquals(updatedUser.getUserMobno(), result.getUserMobno());
	}

	@Test
	@Order(4)
	void testDeleteUser() {
		Long userId = 1L;

		userController.deleteUser(userId);

		Assertions.assertFalse(userRepo.findById(userId).isPresent());
	}

	@Test
	@Order(5)
	void testRegisterUserWithExistingId() {
		User user = new User();
		user.setUserId(1L);

		User registeredUser = userController.registerUser(user);

		Assertions.assertNull(registeredUser);
	}

	@Test
	@Order(6)
	void testUpdateNonExistingUser() {
		Long nonExistingUserId = 100L;
		User updatedUser = new User();
		updatedUser.setUserName("UpdatedName");
		updatedUser.setUserMobno("9876543210");

		User result = userController.updateUser(nonExistingUserId, updatedUser);

		Assertions.assertNull(result);
	}

	@Test
	@Order(7)
	void testDeleteNonExistingUser() {
		Long nonExistingUserId = 100L;

		userController.deleteUser(nonExistingUserId);
	}
}
