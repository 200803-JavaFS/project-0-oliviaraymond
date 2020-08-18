package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionUtility;

public class AccountDAO implements IAccountDAO {

	private static final Logger log = LogManager.getLogger(AccountDAO.class);
	private IUserDAO uDao = new UserDAO();

	@Override
	public List<Account> getAllAccounts() {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts;";
			Statement statement = conn.createStatement();

			List<Account> list = new ArrayList<>();

			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Account a = new Account(result.getInt("account_number"), result.getInt("status_of_account"),
						result.getString("account_type"), result.getDouble("account_balance"), null);
				if (result.getInt("user_id_fk") != 0) {
					a.setUser(uDao.getUserById(result.getInt("user_id_fk")));
				}
				list.add(a);

			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account getAccountById(int id) { // by user id
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE user_id_fk = ?;";

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
	public boolean createAccount(Account bank) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "INSERT INTO accounts (status_of_account, account_type, account_balance, user_id_fk)"
					+ "VALUES (?, ?, ?, ?);";
			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setInt(++index, bank.getStatus());
			statement.setString(++index, bank.getAccountType());
			statement.setDouble(++index, bank.getBalance());
			
				User u = bank.getUser();
				statement.setInt(++index, u.getId());
			
			statement.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Account getAccountByAccountId(int id) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE account_number =" + id + ";";

			Statement statement = conn.createStatement();

			ResultSet result = statement.executeQuery(sql);

			if (result.next()) {
				Account a = new Account(result.getInt("account_number"), result.getInt("status_of_account"),
						result.getString("account_type"), result.getDouble("balance"), null);
				if (result.getInt("user_id_fk") != 0) {
					a.setUser(uDao.getUserById(result.getInt("user_id_fk")));
				}
				return a;

			}
		} catch (SQLException e) {
//			log.warn(e)
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(double balance, int id) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "UPDATE accounts SET account_balance = ? WHERE user_id_fk = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;

			statement.setDouble(++index, balance);
			statement.setInt(++index, id);

			statement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean updateAccount(Account bank) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "UPDATE accounts SET account_balance = ?," + " status_of_account = ?," + " account_type= ?, "
					+ " user_id_fk = ? WHERE account_number = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setDouble(++index, bank.getBalance());
			statement.setInt(++index, bank.getStatus());
			statement.setString(++index, bank.getAccountType());
			if (bank.getUser() != null) {
				User u = bank.getUser();
				statement.setInt(++index, u.getId());
			} else {
				statement.setInt(++index, 0);
			}
			statement.setInt(++index, bank.getAccountId());

			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean addAccountWithUser(Account bank) {
		try (Connection conn = ConnectionUtility.getConnection()) {

			String sql = "BEGIN; "
					+ "INSERT INTO users (user_id, user_first_name, user_last_name, user_email, user_password, user_type)"
					+ "VALUES (?, ?, ?, ?, ?, ?);"
					+ "INSERT INTO accounts (status_of_account, account_type, account_balance, user_id_fk)"
					+ "VALUES (?, ?, ?, ?);" + "COMMIT;";
			PreparedStatement statement = conn.prepareStatement(sql);

			User u = bank.getUser();
			System.out.println("user in addAccountWithUser:" + u);

			int index = 0;
			statement.setInt(++index, u.getId());
			statement.setString(++index, u.getFirstName());
			statement.setString(++index, u.getLastName());
			statement.setString(++index, u.getPassword());
			statement.setString(++index, u.getUserType());
			statement.setString(++index, u.getEmail());
			statement.setDouble(++index, bank.getBalance());
			statement.setInt(++index, bank.getStatus());
			statement.setString(++index, bank.getAccountType());
			statement.setInt(++index, u.getId());

			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteAccount(int id) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "DELETE FROM users WHERE account_number =" + id + ";";
			Statement statement = conn.createStatement();
			statement.execute(sql);

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
