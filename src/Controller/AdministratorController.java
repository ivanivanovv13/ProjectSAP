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

public class AdministratorController {

	private String databaseUrl;
	private String databaseUser;
	private String databasePassword;
	private static final String allAdministratos = "SELECT * FROM administrators";
	private static final String addCategory = "Insert into category(id,name,administrator_Id) Values(?,?,?)";
	private static final String updateItems = "UPDATE category set id=? ,name=?,administrator_Id=? where id=? ;";
	private static final String deleteCategory = "DELETE FROM category WHERE name =?";

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
	}

	public String logIn(String email, String password) {
		for (Administrator temp : accounts) {
			if (temp.getEmail().equals(email)) {
				if (temp.getPassword().equals(password)) {
					return temp.getId();
				}
			}
		}
		return null;
	}

	public List<Item> getActiveItems() {
		List<Item> list = new ArrayList<Item>();
		for (Item item : items) {
			if (item.isActive()) {
				list.add(item);
			}
		}
		return list;
	}

	public List<Item> getInactiveItems() {
		List<Item> list = new ArrayList<Item>();
		for (Item item : items) {
			if (!item.isActive()) {
				list.add(item);
			}
		}
		return list;
	}

	public List<Item> getItemsByDate(LocalDate from, LocalDate to) {
		List<Item> list = new ArrayList<Item>();
		for (Item item : items) {
			if (item.isActive() && (item.getDate().toLocalDate().isBefore(to))
					&& (item.getDate().toLocalDate().isAfter(from))) {
				list.add(item);
			}
		}
		return list;
	}

	public List<Item> getInactiveItemsByDate(LocalDate from, LocalDate to) {
		List<Item> list = new ArrayList<Item>();
		for (Item item : items) {
			if (!item.isActive() && (item.getDate().toLocalDate().isBefore(to))
					&& (item.getDate().toLocalDate().isAfter(from))) {
				list.add(item);
			}
		}
		return list;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void addCategory(String category, String administratorId) throws SQLException {
		String id = UUID.randomUUID().toString();
		categories.add(new Category(id, category, administratorId));
		Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
		PreparedStatement preparedStatement = myCon.prepareStatement(addCategory);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, category);
		preparedStatement.setString(3, administratorId);
		preparedStatement.executeUpdate();
	}

	public boolean updateCategory(String category, String newCategory, String administratorId) throws SQLException {
		for (Category temp : categories) {
			if (temp.getName().equals(category)) {
				Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
				PreparedStatement preparedStatement = myCon.prepareStatement(updateItems);
				preparedStatement.setString(1, temp.getId());
				preparedStatement.setString(2, newCategory);
				preparedStatement.setString(3, administratorId);
				preparedStatement.setString(4, temp.getId());

				preparedStatement.executeUpdate();

				Category newSection = new Category(temp.getId(), newCategory, administratorId);
				categories.set(categories.indexOf(temp), newSection);
				return true;

			}
		}
		throw new IllegalArgumentException();
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

}
