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

import com.revature.models.User;
import com.revature.util.ConnectionUtility;

public class UserDAO implements IUserDAO {
	
	private static final Logger log = LogManager.getLogger(UserDAO.class);

	@Override
	public List<User> findAll() {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM users";

			Statement statement = conn.createStatement();

			List<User> list = new ArrayList<>();

			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				User u = new User();
				u.setId(result.getInt("user_id"));
				u.setFirstName(result.getString("user_first_name"));
				u.setLastName(result.getString("user_last_name"));
				u.setEmail(result.getString("user_email"));
				u.setPassword(result.getString("user_password"));
				u.setUserType(result.getString("user_type"));
				// why this way instead of the way in avengers?

				list.add(u);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User createUser(User u) {

		try (Connection conn = ConnectionUtility.getConnection()) {

			String sql = "INSERT INTO users (user_first_name, user_last_name, user_email, user_password, user_type)"
					+ "VALUES (?, ?, ?, ?, ?);";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setString(++index, u.getFirstName());
			statement.setString(++index, u.getLastName());
			statement.setString(++index, u.getEmail());
			statement.setString(++index, u.getPassword());
			statement.setString(++index, u.getUserType());

			statement.execute();
			return getUserByEmail(u.getEmail());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateUser(User u) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "UPDATE users SET user_first_name = ?, user_last_name = ?, user_email = ?, "
					+ "user_password = ?, user_type = ? WHERE user_id = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setString(++index, u.getFirstName());
			statement.setString(++index, u.getLastName());
			statement.setString(++index, u.getEmail());
			statement.setString(++index, u.getPassword());
			statement.setString(++index, u.getUserType());

			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public User getUserById(int id) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM users WHERE user_id = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				User u = new User();
				u.setEmail(result.getString("user_email"));
				u.setFirstName(result.getString("user_first_name"));
				u.setLastName(result.getString("user_last_name"));
				u.setPassword(result.getString("user_password"));
				u.setUserType(result.getString("user_type"));

				return u;
			} else {
				// good place to log a failed query
				// do you have loggers in dao
				log.warn("Failed query - getUserByID");
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return null;

	}
	

	@Override
	public User getUserByEmail(String name) {
		try (Connection conn = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM users WHERE user_email = ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, name);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				User u = new User();
				u.setId(result.getInt("user_id"));
				u.setFirstName(result.getString("user_first_name"));
				u.setLastName(result.getString("user_last_name"));
				u.setPassword(result.getString("user_password"));
				u.setUserType(result.getString("user_type"));

				return u;
			} else {
				// good place to log a failed query
				// do you have loggers in dao
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return null;

	}

	@Override
	public int hideUserId(User user) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isUserEmailUnique(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
