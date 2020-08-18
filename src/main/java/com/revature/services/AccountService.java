package com.revature.services;

import java.util.List;

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
	
	
	//account control methods -------------------
	
	public List<Account> findAllAccounts() {
		log.info("Retrieving all accounts");
		List<Account> list = dao.getAllAccounts();
		
		// what goes here?
		for (Account a: list) {
			System.out.println("account :" + a);
		}
		return list;
	}
	
	public Account findAccountById(int id) {
		log.info("finding account with id " +id);
		return dao.getAccountById(id);
	}
	
	public boolean updateAccount(Account a) {
		log.info("Updating account "+ a);
		if (dao.updateAccount(a)) {
			return true;
		}
		return false;
	}
	
	public boolean insertAccount(Account a) {
		if (a.getUser() != null) {
			List<User> list = uDao.findAll();
			log.info("list in insert account: "+ list);
			boolean b = false;
			for (User u: list) {
				if (u.equals(a.getUser())) {
					b = true;
				}
			}
			if (b) {
				log.info("Adding account: " + a);
				if (dao.createAccount(a)) {
					return true;
				}
			} else {
				log.info("Adding account: " + a + "with a new User:" + a.getUser());
				if (dao.addAccountWithUser(a)) {
					return true;
				}
			}
		} else {
//			log.info("Adding account: " + a);
//			if (dao.createAccount(a)) {
//				return true;
			}
		return false;
	}
	
	public boolean removeAccount(int id) {
		log.info("deleting account with id: "+ id);
		if (dao.deleteAccount(id)) {
			return true;
		}
		return false;
	}
	
	
	// user control methods -------------------------------------------
	
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
	
	
	public void deposit(double amount, int id) {
		validateAmount(amount);
		Account a = dao.getAccountById(id);
		dao.update((a.getBalance() + amount), id);
	}
	
	public void withdraw(double amount, int id) {
		validateAmount(amount);
		Account a = dao.getAccountById(id);
		if(a == null ) {
			//throw new IllegalArgumentException("You do not have a bank account attached to this user");
			log.info("No bank account attached to this user");
		}
		if (a.getBalance() < amount) {
			throw new IllegalArgumentException("Withdraw amount is greater than balance.");
		} else {
			dao.update((a.getBalance() - amount), id);
		}
	}
	
	public void transfer(double amount, int accountId) {
		validateAmount(amount);
		Account a = dao.getAccountByAccountId(accountId);
		if (a.getBalance() >= amount) {
			if (a.getStatus() == 2) {
				dao.update(amount, accountId);
				
			} else {
				throw new IllegalArgumentException("Not an available account.");
			}
		}
	}
	
	public List<Account> findUserAccounts(User u) {
		log.info("Retrieving user accounts");
		return uDao.findUserAccount(u);
	}
	
	public List<User> findAllUsers() {
		log.info("Retrieving all users");
		return uDao.findAll();
	}
	
	public User findUserById(int id) {
		log.info("finding a user with id " +id);
		return uDao.getUserById(id);
	}
	
	public User findByEmail(String email) {
		log.info("finding a user with email " + email);
		return uDao.getUserByEmail(email);
	}
	
	public boolean updateUser(User u) {
		log.info("updating a user: " + u);
		if (uDao.updateUser(u)) {
			return true;
		} 
		return false;
	}
	
	public boolean insertUser(User u) {
		log.info("adding a user: " + u);

		if (uDao.createUser(u)) {
			return true;
		}
		return false;
	}
	
	public boolean removeUser(int id) {
		log.info("deleting a user with id: " + id);

		if (uDao.deleteUser(id)) {
			return true;
		}
		return false;
	}
	
}
