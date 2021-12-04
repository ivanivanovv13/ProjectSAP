package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import Model.Administrator;
import Model.Category;
import Model.Item;
import Model.NotEmailAddressException;
import Model.User;

public class AdministratorController {

	String databaseUrl;
	String databaseUser;
	String databasePassword;
	final String allAdministratos = "SELECT * FROM administrators";
	final String allCategories = "SELECT * FROM category";
	final String addCategory = "Insert into category(id,name) Values(?,?)";
	final String updateItems = "UPDATE category set id=? ,name=? where id=? ;";
	final String deleteCategory="DELETE FROM category WHERE name =?";

	private List<Administrator> accounts = new ArrayList<Administrator>();
	private List<Item> items;
	private List<Category> categories;

	public AdministratorController(String databaseUrl, String databaseUser, String databasePassword, List<Item> items,
			List<Category> categories) throws SQLException, NotEmailAddressException {
		this.databaseUrl = databaseUrl;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
		this.items = items;
		this.categories = categories;
		fetchAllAdministrators();
		fetchAllCategories();
	}

	public boolean logIn(String email, String password) {
		for (Administrator temp : accounts) {
			if (temp.getEmail().equals(email)) {
				if (temp.getPassword().equals(password)) {
					return true;
				}
			}
		}
		return false;
	}

	public void getActiveItems() {
		for (Item item : items) {
			if (item.isActive()) {
				System.out.println(item.toString());
			}
		}
	}

	public void getInactiveItems() {
		for (Item item : items) {
			if (!item.isActive()) {
				System.out.println(item.toString());
			}
		}
	}
	
	public void getItemsByDate(LocalDate from, LocalDate to) {
		for (Item item : items) {
			if (item.isActive() && (item.getDate().toLocalDate().isBefore(to))
					&& (item.getDate().toLocalDate().isAfter(from))) {
				System.out.println(item.toString());
			}
		}
	}
	
	public void getInactiveItemsByDate(LocalDate from, LocalDate to) {
		for (Item item : items) {
			if (!item.isActive() && (item.getDate().toLocalDate().isBefore(to))
					&& (item.getDate().toLocalDate().isAfter(from))) {
				System.out.println(item.toString());
			}
		}
	}

	public void getCategories() {
		for (Category category : categories) {
			System.out.println(category.getName());
		}
	}

	public void addCategory(String category) throws SQLException {
		String id = UUID.randomUUID().toString();
		categories.add(new Category(id, category));
		Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
		PreparedStatement preparedStatement = myCon.prepareStatement(addCategory);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, category);
		preparedStatement.executeUpdate();
	}

	public void updateCategory(String category, String newCategory) throws SQLException {
		for (Category temp : categories) {
			if (temp.getName().equals(category)) {
				Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
				PreparedStatement preparedStatement = myCon.prepareStatement(updateItems);
				preparedStatement.setString(1, temp.getId());
				preparedStatement.setString(2, newCategory);
				preparedStatement.setString(3, temp.getId());

				preparedStatement.executeUpdate();

				Category newSection = new Category(temp.getId(), newCategory);
				categories.set(categories.indexOf(temp), newSection);
				System.out.println("Category updated successfully");
			}
		}
	}

	public void deleteCategory(String category) throws SQLException {

		Iterator<Category> itr = categories.iterator();
		Category currItem;
		while (itr.hasNext()) {
			currItem = itr.next();
			if (currItem.getName().equals(category)) {
				Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
				PreparedStatement preparedStatement = myCon.prepareStatement(deleteCategory);
				preparedStatement.setString(1, category);
				preparedStatement.executeUpdate();

				itr.remove();
			}
		}
	}

	public void fetchAllAdministrators() throws SQLException, NotEmailAddressException {
		Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
		Statement myStmt = myCon.createStatement();
		ResultSet myRs = myStmt.executeQuery(allAdministratos);

		while (myRs.next()) {
			accounts.add(new Administrator(myRs.getString("id"), myRs.getString("email"), myRs.getString("password"),
					myRs.getString("first_name"), myRs.getString("last_name"), myRs.getString("phone_number")));
		}
	}
	
	public void fetchAllCategories() throws SQLException, NotEmailAddressException {
		Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
		Statement myStmt = myCon.createStatement();
		ResultSet myRs = myStmt.executeQuery(allCategories);

		while (myRs.next()) {
			categories.add(new Category(myRs.getString("id"), myRs.getString("name")));
		}
	}

}
