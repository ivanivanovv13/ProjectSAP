package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import Model.Category;
import Model.Item;
import Model.NotEmailAddressException;

public class ItemsController {
	public List<Item> items = new ArrayList<Item>();
	public List<Category> category = new ArrayList<Category>();
	private static final String allItems = "select * from items order by date desc;";
	private static final String allCategories = "SELECT * FROM category";
	private static final String insertItem = "INSERT INTO items(id,name,price,description,category,date,status,user_id,image) VALUES(?,?,?,?,?,?,?,?,?); ";
	private static final String deleteItem = "DELETE FROM items WHERE id =?";
	private static final String updateItems = "UPDATE items set name=? ,description=?,price=?,status=?,category=?,date=?,user_id=?,image=? where id=? ;";
	private String databaseUrl;
	private String databaseUser;
	private String databasePassword;

	public ItemsController(String databaseUrl, String databaseUser, String databasePassword)
			throws SQLException, NotEmailAddressException {
		this.databaseUrl = databaseUrl;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
		fetchAllItems();
		fetchAllCategories();
	}

	public List<Item> getItems() {
		List<Item> list = new ArrayList<Item>();
		for (Item item : items) {
			if (item.isActive()) {
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

	public List<Item> getUsersItems(String userId) {
		List<Item> list = new ArrayList<Item>();
		for (Item item : items) {
			if (item.getUserId().equals(userId)) {
				list.add(item);
			}
		}
		return list;
	}

	public void deleteItem(String userId, String itemId) throws SQLException {
		Iterator<Item> itr = items.iterator();
		while (itr.hasNext()) {
			Item currItem = itr.next();
			if (currItem.getUserId().equals(userId)) {
				if (currItem.getId().equals(itemId)) {
					Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
					PreparedStatement preparedStatement = myCon.prepareStatement(deleteItem);
					preparedStatement.setString(1, itemId);
					preparedStatement.executeUpdate();

					itr.remove();
				}
			}
		}
	}

	public boolean updateItem(String userId, String itemId, String name, double price, String description,
			String category, boolean status, Blob image) throws SQLException {
		for (Item item : items) {
			if (item.getUserId().equals(userId)) {
				if (item.getId().equals(itemId)) {

					Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
					PreparedStatement preparedStatement = myCon.prepareStatement(updateItems);
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, description);
					preparedStatement.setDouble(3, price);
					preparedStatement.setString(4, String.valueOf(status));
					preparedStatement.setString(5, category);
					preparedStatement.setDate(6, Date.valueOf(LocalDate.now()));
					preparedStatement.setString(7, userId);
					preparedStatement.setString(8, itemId);
					preparedStatement.setBlob(9,image);
					preparedStatement.executeUpdate();

					Item newItem = new Item(itemId, name, description, price, status, userId, category,
							Date.valueOf(LocalDate.now()), null);
					items.set(items.indexOf(item), newItem);
					return true;

				}
			}
		}

		throw new IllegalArgumentException();
	}

	public boolean addItem(Item item) throws SQLException, IOException {

		if (item != null) {
			Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			PreparedStatement preparedStatement = myCon.prepareStatement(insertItem);
			preparedStatement.setString(1, item.getId());
			preparedStatement.setString(2, item.getName());
			preparedStatement.setDouble(3, item.getPrice());
			preparedStatement.setString(4, item.getDescription());
			preparedStatement.setString(5, item.getCategory());
			preparedStatement.setDate(6, Date.valueOf(LocalDate.now()));
			preparedStatement.setBoolean(7, item.isActive());
			preparedStatement.setString(8, item.getUserId());
			preparedStatement.setBlob(9, item.getImage());
			preparedStatement.executeUpdate();

			items.add(item);
			return true;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public String getUser(String itemId) {
		for (Item item : items) {
			if (item.getId().equals(itemId)) {
				return item.getUserId();
			}
		}
		return null;
	}

	public boolean getCategory(String categoryName) {
		for (Category temp : category) {
			if (temp.getName().equals(categoryName)) {
				return true;
			}
		}
		return false;
	}

	public void fetchAllItems() throws SQLException {
		Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
		Statement myStmt = myCon.createStatement();
		ResultSet myRs = myStmt.executeQuery(allItems);
		while (myRs.next()) {
			items.add(new Item(myRs.getString("id"), myRs.getString("name"), myRs.getString("description"),
					myRs.getDouble("price"), myRs.getBoolean("status"), myRs.getString("user_id"),
					myRs.getString("category"), myRs.getDate("date"),myRs.getBlob("image")));
		}
	}

	public void fetchAllCategories() throws SQLException, NotEmailAddressException {
		Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
		Statement myStmt = myCon.createStatement();
		ResultSet myRs = myStmt.executeQuery(allCategories);

		while (myRs.next()) {
			category.add(new Category(myRs.getString("id"), myRs.getString("name")));
		}
	}
}
