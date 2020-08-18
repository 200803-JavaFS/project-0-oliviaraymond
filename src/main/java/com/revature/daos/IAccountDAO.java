package com.revature.daos;

import java.util.List;

import com.revature.models.Account;

public interface IAccountDAO {
	
	public List<Account> getAllAccounts();
	public Account getAccountById(int id);
	public int createAccount(Account bank);
	public int updateAccount(Account bank);
	public Account getAccountByAccountId(int id);
	public void update(double amount, int id);

}
