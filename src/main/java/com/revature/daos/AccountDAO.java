package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionUtility;

public class AccountDAO implements IAccountDAO {
	
	private static final Logger log = LogManager.getLogger(AccountDAO.class);

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccountById(int id) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE user_id = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Account a = new Account();
				a.setStatus(result.getInt("status_of_account"));
				a.setAccountType(result.getString("account_type"));
				a.setBalance(result.getDouble("account_balance"));
				a.setAccountType(result.getString("account_type"));

				return a;
			} else {
				// good place to log a failed query
				// do you have loggers in dao
				log.warn("Failed query - getAccountByID");
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return null;

	}

	@Override
	public int createAccount(Account bank) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateAccount(Account bank) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Account getAccountByAccountId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(double balance, int id) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "UPDATE accounts SET account_balance = ? WHERE user_id = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
		
			statement.setDouble(++index, balance);
			statement.setInt(++index, id);

			statement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
