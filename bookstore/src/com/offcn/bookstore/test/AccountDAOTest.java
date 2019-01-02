package com.offcn.bookstore.test;

import org.junit.Test;

import com.offcn.bookstore.dao.AccountDAO;
import com.offcn.bookstore.dao.impl.AccountDAOIml;
import com.offcn.bookstore.domain.Account;

public class AccountDAOTest {

	AccountDAO accountDAO = new AccountDAOIml();
	
	@Test
	public void testGet() {
		Account account = accountDAO.get(1);
		System.out.println(account.getBalance()); 
	}

	@Test
	public void testUpdateBalance() {
		accountDAO.updateBalance(1, 50);
	}

}
