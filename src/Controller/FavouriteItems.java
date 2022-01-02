package Controller;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Model.Item;
import Model.User;

public class FavouriteItems {
	private List<User> accounts;
	private List<Item> items;
	private static final String allFavouriteItems = "select * from favourite_items";
	private static final String insertFavouriteItems = "INSERT INTO favourite_items(user_id,item_id) VALUES (?,?); ";
	private static final String deleteFavouriteItems = "DELETE FROM favourite_items WHERE user_id =? AND item_id=?";
	private String databaseUrl;
	private String databaseUser;
	private String databasePassword;

	public FavouriteItems(List<User> accounts, List<Item> items, String databaseUrl, String databaseUser,
			String databasePassword) throws SQLException {
		this.accounts = accounts;
		this.items = items;
		this.databaseUrl = databaseUrl;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
		fetchAllFavouriteItems();
	}

	public void addFavouriteItems(String userId, String itemId) throws SQLException {
		Item item = getItem(itemId);
		for (User temp : accounts) {
			if (temp.getId().equals(userId)) {
				Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
				PreparedStatement preparedStatement = myCon.prepareStatement(insertFavouriteItems);
				preparedStatement.setString(1, userId);
				preparedStatement.setString(2, itemId);
				preparedStatement.executeUpdate();

				temp.favouriteItems.add(item);
			}
		}
	}

	public List<Item> getFavouriteItems(String userId) {
		List<Item> list = new ArrayList<Item>();
		for (User temp : accounts) {
			if (temp.getId().equals(userId)) {
				for (Item item : temp.favouriteItems) {
					list.add(item);
				}
			}
		}
		return list;
	}

	public void removeFavouriteItems(String userId, String itemId) throws SQLException {
		for (User temp : accounts) {
			if (temp.getId().equals(userId)) {
				Iterator<Item> itr = temp.favouriteItems.iterator();
				Item currItem;
				while (itr.hasNext()) {
					currItem = itr.next();
					if (currItem.getId().equals(itemId)) {
						Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
						PreparedStatement preparedStatement = myCon.prepareStatement(deleteFavouriteItems);
						preparedStatement.setString(1, userId);
						preparedStatement.setString(2, itemId);
						preparedStatement.executeUpdate();

						itr.remove();
					}
				}
			}
		}
	}

	public Item getItem(String itemId) {
		for (Item item : items) {
			if (item.getId().equals(itemId)) {
				return item;
			}
		}
		return null;
	}

	public void fetchAllFavouriteItems() throws SQLException {
		Connection myCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
		Statement myStmt = myCon.createStatement();
		ResultSet myRs = myStmt.executeQuery(allFavouriteItems);

		Item item;
		String itemId;
		String userId;
		while (myRs.next()) {
			itemId = myRs.getString("item_id");
			userId = myRs.getString("user_id");
			item = getItem(itemId);

			for (User temp : accounts) {
				if (temp.getId().equals(userId)) {
					temp.favouriteItems.add(item);
				}
			}

		}
	}

}
