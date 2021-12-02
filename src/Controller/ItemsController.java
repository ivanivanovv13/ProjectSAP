package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import Model.Category;
import Model.Item;

public class ItemsController {
	public List<Item> items = new ArrayList<Item>();
	public List<Category> category = new ArrayList<Category>();
	final String allItems = "select * from items";
	final String insertItem = "INSERT INTO items(id,name,price,description,category,status,user_id) VALUES(?,?,?,?,?,?,?); ";
	final String deleteItem = "DELETE FROM items WHERE id =?";
	final String updateItems = "UPDATE items set name=? ,description=?,price=?,status=?,category=?,user_id=? where id=? ;";
	String databaseUrl;
	String databaseUser;
	String databasePassword;

	public ItemsController(String databaseUrl, String databaseUser, String databasePassword) throws SQLException {
		this.databaseUrl = databaseUrl;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
		fetchAllItems();
	}

	public void getItems() {
		for (Item item : items) {
			if (item.isActive()) {
				System.out.println(item.toString());
			}
		}
	}

	public void getUsersItems(String userId) {
		for (Item item : items) {
			if (item.getUserId().equals(userId)) {
				System.out.println(item.toString());
			}
		}
	}

	public void deleteItem(String userId, String itemId) throws SQLException {
		Iterator<Item> itr = items.iterator();
		while (itr.hasNext()) {
			Item currItem = itr.next();
			if (currItem.getUserId().equals(userId)) {
				if (currItem.getId().equals(itemId)) {
					Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
					PreparedStatement preparedStatement = myCon.prepareStatement(deleteItem);
					preparedStatement.setString(1, userId);
					preparedStatement.setString(2, itemId);
					preparedStatement.executeUpdate();

					itr.remove();
				}
			}
		}
	}

	public void updateItem(String userId, String itemId, String name, double price, String description, String category,
			boolean status) throws SQLException {
		for (Item item : items) {
			if (item.getUserId().equals(userId)) {
				if (item.getId().equals(itemId)) {
					Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
					PreparedStatement preparedStatement = myCon.prepareStatement(updateItems);
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, description);
					preparedStatement.setDouble(3, price);
					preparedStatement.setBoolean(4, status);
					preparedStatement.setString(5, category);
					preparedStatement.setString(6, userId);
					preparedStatement.setString(7, itemId);
					preparedStatement.executeUpdate();

					Item newItem = new Item(itemId, name, description, price, status, userId, category);
					items.set(items.indexOf(item), newItem);
					System.out.println("Item updated successfully");
				}
			}
		}
	}

	public void addItem(Item item) throws SQLException {

		if (item != null) {
			Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			PreparedStatement preparedStatement = myCon.prepareStatement(insertItem);
			preparedStatement.setString(1, item.getId());
			preparedStatement.setString(2, item.getName());
			preparedStatement.setDouble(3, item.getPrice());
			preparedStatement.setString(4, item.getDescription());
			preparedStatement.setString(5, item.getCategory());
			preparedStatement.setBoolean(6, item.isActive());
			preparedStatement.setString(7, item.getUserId());
			preparedStatement.executeUpdate();

			items.add(item);
			System.out.println("Succsesfullfy add a item.");
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
					myRs.getString("category")));
		}
	}

}
