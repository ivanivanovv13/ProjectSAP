package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import Model.Item;
import Model.User;

public class FavouriteItems {
	private List<User> accounts;
	private List<Item> items;

	public FavouriteItems(List<User> accounts, List<Item> items) throws SQLException {
		this.accounts = accounts;
		this.items = items;
		fetchAllFavouriteItems();
	}

	public void addFavouriteItems(String userId, String itemId) throws SQLException {
		Item item = getItem(itemId);
		for (User temp : accounts) {
			if (temp.getId().equals(userId)) {
				Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root",
						"ivan1313");
				Statement myStmt = myCon.createStatement();
				String sql = "INSERT INTO favourite_items(user_id,item_id) " + "VALUES (\"" + userId + "\",\""
						+ item.getId() + "\");";
				myStmt.executeUpdate(sql);
				temp.favouriteItems.add(item);
			}
		}
	}

	public void getFavouriteItems(String userId) {
		for (User temp : accounts) {
			if (temp.getId().equals(userId)) {
				for (Item item : temp.favouriteItems) {
					System.out.println(item.toString());
				}
			}
		}
	}

	public void removeFavouriteItems(String userId, String itemId) throws SQLException {
		for (User temp : accounts) {
			if (temp.getId().equals(userId)) {
				Iterator<Item> itr = temp.favouriteItems.iterator();
				Item currItem;
				while (itr.hasNext()) {
					currItem = itr.next();
					if (currItem.getId().equals(itemId)) {
						Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root",
								"ivan1313");
						Statement myStmt = myCon.createStatement();
						String sql = "DELETE FROM favourite_items WHERE user_id ='" + userId + "'AND item_id='" + itemId
								+ "';";
						myStmt.executeUpdate(sql);

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

	private void fetchAllFavouriteItems() throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "ivan1313");
		Statement myStmt = myCon.createStatement();
		ResultSet myRs = myStmt.executeQuery("select * from favourite_items");

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
