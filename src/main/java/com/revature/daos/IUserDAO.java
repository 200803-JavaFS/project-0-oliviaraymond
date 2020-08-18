package com.revature.daos;

import java.util.List;

import com.revature.models.User;

public interface IUserDAO{
	
	public List<User> findAll(); //only for admin and employees?
	public User createUser(User user);
	public boolean updateUser(User user);
	public int hideUserId(User user);
	public User getUserById(int id);
	public User getUserByEmail(String email);
	public boolean isUserEmailUnique (User user);
	

}
