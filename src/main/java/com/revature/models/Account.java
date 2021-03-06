package com.revature.models;

import java.io.Serializable;

public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int accountId;
	private int status;
	private String accountType;
	private double balance; //should I use double or float for balance?
	private int userId;
	
	public Account() {
		super();
	}

	public Account(int status, String accountType, double balance, int userId) {
		super();
		accountId = 999999;
		this.status = status;
		this.accountType = accountType;
		this.balance = balance;
		this.userId = userId;
	}

	public Account(int accountId, int status, String accountType, double balance, int userId) {
		super();
		this.accountId = accountId;
		this.status = status;
		this.accountType = accountType;
		this.balance = balance;
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + status;
		result = prime * result + userId;
		return result;
	}
	
	

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", status=" + status + ", accountType=" + accountType + ", balance="
				+ balance + ", userId=" + userId + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId != other.accountId)
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (status != other.status)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	

}