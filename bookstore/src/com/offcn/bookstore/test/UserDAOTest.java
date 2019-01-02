package com.offcn.bookstore.test;

import org.junit.Test;

import com.offcn.bookstore.dao.UserDAO;
import com.offcn.bookstore.dao.impl.UserDAOImpl;
import com.offcn.bookstore.domain.User;

public class UserDAOTest {

	private UserDAO userDAO = new UserDAOImpl();
	
	@Test
	public void testGetUser() {
		User user = userDAO.getUser("AAA");
		System.out.println(user); 
	}

}
