package com.offcn.bookstore.service;

import com.offcn.bookstore.dao.AccountDAO;
import com.offcn.bookstore.dao.impl.AccountDAOIml;
import com.offcn.bookstore.domain.Account;

public class AccountService {
	
	private AccountDAO accountDAO = new AccountDAOIml();
	
	public Account getAccount(int accountId){
		return accountDAO.get(accountId);
	}
	
}
