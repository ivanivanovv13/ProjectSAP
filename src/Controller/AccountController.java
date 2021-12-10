package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.NotEmailAddressException;
import Model.User;

public class AccountController {

	public List<User> accounts = new ArrayList<User>();
	private static final String allUsers = "select * from users";
	private static final String insertUsers = "INSERT INTO users(id,email,password,first_name,last_name,phone_number) VALUES (?,?,?,?,?,?); ";
	private String databaseUrl;
	private String databaseUser;
	private String databasePassword;

	public AccountController(String databaseUrl, String databaseUser, String databasePassword)
			throws SQLException, NotEmailAddressException {
		this.databaseUrl = databaseUrl;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
		fetchAllUsers();
	}

	public boolean addAccount(User obj) throws SQLException, NullPointerException {
		if (obj != null) {

			Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			PreparedStatement preparedStatement = myCon.prepareStatement(insertUsers);
			preparedStatement.setString(1, obj.getId());
			preparedStatement.setString(2, obj.getEmail());
			preparedStatement.setString(3, obj.getPassword());
			preparedStatement.setString(4, obj.getFirstName());
			preparedStatement.setString(5, obj.getLastName());
			preparedStatement.setString(6, obj.getPhoneNum());
			preparedStatement.executeUpdate();

			accounts.add(obj);
			return true;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public String logIn(String email, String password) {
		for (User temp : accounts) {
			if (temp.getEmail().equals(email)) {
				if (temp.getPassword().equals(password)) {
					return temp.getId();
				}
			}
		}
		return null;
	}

	public String getUserPhoneNumber(String userId) {
		for (User temp : accounts) {
			if (temp.getId().equals(userId))
				return temp.getFirstName() + " " + temp.getLastName() + " " + temp.getPhoneNum();
		}
		return null;
	}

	public void fetchAllUsers() throws SQLException, NotEmailAddressException {
		Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
		Statement myStmt = myCon.createStatement();
		ResultSet myRs = myStmt.executeQuery(allUsers);

		while (myRs.next()) {
			accounts.add(new User(myRs.getString("id"), myRs.getString("email"), myRs.getString("password"),
					myRs.getString("first_name"), myRs.getString("last_name"), myRs.getString("phone_number")));
		}
	}

}
