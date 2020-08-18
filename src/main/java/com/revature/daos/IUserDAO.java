package com.revature.daos;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

public interface IUserDAO{
	
	public List<User> findAll(); 
	public boolean createUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUser(int id);
	public User getUserById(int id); // find by id 
	public User getUserByEmail(String email);
	public List<Account> findUserAccount(User u);
	
	//public boolean isUserEmailUnique (User user);
	

}
