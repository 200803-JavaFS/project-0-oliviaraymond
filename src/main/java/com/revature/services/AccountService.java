package com.revature.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.AccountDAO;
import com.revature.daos.IAccountDAO;
import com.revature.daos.IUserDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;

public class AccountService  {
	
	private static IUserDAO uDao = new UserDAO();
	private static IAccountDAO dao = new AccountDAO();
	private static final Logger log = LogManager.getLogger(AccountService.class);
	
	//call all the account and user dao methods
	
	public User createUser(User u) {
		User newUser = uDao.createUser(u);
		Account a = new Account(0, null, 0.0, newUser.getId());
		//call dao to save account to user
		
		return newUser;
	}
	
	public User login(String email, String password) {
		User u = uDao.getUserByEmail(email);
		if (u == null) {
			return null;
		} 
		if (u.getPassword().equals(password)) {
			return u;
		}else 
			return null;

	}
	
	private void validateAmount(double amount) {
		if (amount <= 0.0 ) {
			throw new IllegalArgumentException("Amount zero or less is not allowed.");

		}
		if ((amount * 100) % 1 != 0) {
			throw new IllegalArgumentException("Fractions of a penny not allowed.");
			
		}
	}
	
	// should this go in console or in services?
	public void deposit(double amount) {
		validateAmount(amount);
		
		//setBalance(getBalance() + amount);
		//talk to database
		return;
	}
	
	public void withdraw(double amount, int id) {
		validateAmount(amount);
		Account a = dao.getAccountById(id);
		if (a.getBalance() < amount) {
			throw new IllegalArgumentException("Withdraw amount is greater than balance.");
		} else {
			dao.update((a.getBalance() - amount), id);
		}
	}
	
	public void transfer(double amount) {
		validateAmount(amount);
		
		return;
		
	}
	
	
	
}
