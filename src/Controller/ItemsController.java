package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import Model.Item;

public class ItemsController {
	public List<Item> items = new ArrayList<Item>();

	public ItemsController() throws SQLException {
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
					Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root",
							"ivan1313");
					Statement myStmt = myCon.createStatement();
					String sql = "DELETE FROM items WHERE id ='" + itemId + "';";
					myStmt.executeUpdate(sql);

					itr.remove();
				}
			}
		}
	}

	public void updateItem(String userId, String itemId, String name, double price, String description, boolean status)
			throws SQLException {
		for (Item item : items) {
			if (item.getUserId().equals(userId)) {
				if (item.getId().equals(itemId)) {
					Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root",
							"ivan1313");
					Statement myStmt = myCon.createStatement();
					String sql = "UPDATE items " + "set name='" + name + "',description='" + description + "',price='"
							+ price + "' ,status='" + status + "' where id='" + itemId + "';";
					myStmt.executeUpdate(sql);

					Item newItem = new Item(itemId, name, description, price, status, userId);
					items.set(items.indexOf(item), newItem);
					System.out.println("Item updated successfully");
				}
			}
		}
	}

	public void addItem(Item item) throws SQLException {

		if (item != null) {
			Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "ivan1313");
			Statement myStmt = myCon.createStatement();
			String sql = "INSERT INTO items(id,name,description,price,status,user_id) " + "VALUES (\"" + item.getId()
					+ "\",\"" + item.getName() + "\",\"" + item.getDescription() + "\",\"" + item.getPrice() + "\",\""
					+ item.isActive() + "\",\"" + item.getUserId() + "\");";
			myStmt.executeUpdate(sql);

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

	public void fetchAllItems() throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "ivan1313");
		Statement myStmt = myCon.createStatement();
		ResultSet myRs = myStmt.executeQuery("select * from items");
		while (myRs.next()) {
			items.add(new Item(myRs.getString("id"), myRs.getString("name"), myRs.getString("description"),
					myRs.getDouble("price"), myRs.getBoolean("status"), myRs.getString("user_id")));
		}
	}

}
