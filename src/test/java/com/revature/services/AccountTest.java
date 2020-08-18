package com.revature.services;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountTest {

	@Test
	public void testNegativeDeposit() {
		Account acct = new Account();
		assertThrows(IllegalArgumentException.class, () -> acct.deposit(-2));
	}
	
	@Test
	public void testMinisculeDeposit() {
		Account acct = new Account();
		assertThrows(IllegalArgumentException.class, () -> acct.deposit(0.001));
	}

	@Test
	public void testSuccess() {
		Account acct = new Account();
		acct.deposit(2);
	}
}
