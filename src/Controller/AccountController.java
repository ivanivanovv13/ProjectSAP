package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import Model.NotEmailAddressException;
import Model.User;

public class AccountController {

	private List<User> accounts = new ArrayList<User>();

	public AccountController() throws SQLException, NotEmailAddressException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "ivan1313");
		Statement myStmt = myCon.createStatement();
		ResultSet myRs = myStmt.executeQuery("select * from users");

		while (myRs.next()) {
			accounts.add(new User(myRs.getString("id"), myRs.getString("email"), myRs.getString("password"),
					myRs.getString("first_name"), myRs.getString("last_name"), myRs.getString("phone_number")));
		}
	}

	public void addAccount(User obj) throws SQLException {
		if (obj != null) {
			
			Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "ivan1313");
			Statement myStmt = myCon.createStatement();
			String sql="INSERT INTO users(id,email,password,first_name,last_name,phone_number) "+
			"VALUES (\""+obj.getId()+"\",\""+obj.getEmail()+"\",\""+obj.getPassword()+"\",\""+obj.getFirstName()+"\",\""+obj.getLastName()+"\",\""+obj.getPhoneNum() +"\");";
			
			myStmt.executeUpdate(sql);
			
			accounts.add(obj);
			System.out.println("You register successfuly!For log in press 2.");
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
		return "false";
	}

}
