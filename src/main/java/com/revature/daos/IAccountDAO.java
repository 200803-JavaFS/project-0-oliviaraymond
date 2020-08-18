package com.revature.daos;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

public interface IAccountDAO {
	
	public List<Account> getAllAccounts(); //find All
	public Account getAccountById(int id); //find by id
	public boolean createAccount(Account bank); //add account
	public boolean updateAccount(Account bank);
	
	public Account getAccountByAccountId(int id);
	public void update(double amount, int id);
	
	public boolean addAccountWithUser(Account bank); // different than create account?
	
	public boolean deleteAccount(int id);

}
