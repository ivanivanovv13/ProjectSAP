package View;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import Controller.AccountController;
import Controller.AdministratorController;
import Controller.FavouriteItems;
import Controller.ItemsController;
import Model.Item;
import Model.NotEmailAddressException;
import Model.User;

public class App {

	public static void main(String[] args) throws NotEmailAddressException, SQLException {
		if (args.length != 3) {
			System.out.println("invalid arguments. Usage ./program-name argument-1 argument2 argument-3");
			System.exit(1);
		}
		String databaseUrl = args[0];
		String databaseUser = args[1];
		String databasePassword = args[2];
		AccountController accounts = new AccountController(databaseUrl, databaseUser, databasePassword);
		ItemsController items = new ItemsController(databaseUrl, databaseUser, databasePassword);
		FavouriteItems faveItems = new FavouriteItems(accounts.accounts, items.items, databaseUrl, databaseUser,
				databasePassword);
		AdministratorController administrators = new AdministratorController(databaseUrl, databaseUser,
				databasePassword, items.items, items.category);

		String userId;
		String email;
		String firstName;
		String lastName;
		String password;
		String phoneNum;
		String name;
		String description;
		String data;
		String category;
		boolean status;
		double price = 0.0;
		String itemId;
		LocalDate dateFrom;
		LocalDate dateTo;
		Scanner in = new Scanner(System.in);

		System.out.println("Press 1 for register.");
		System.out.println("Press 2 for Log in as user.");
		System.out.println("Press 3 for Log in as administrator.");
		System.out.println("Press 0 for Stopping the program.");

		while (true) {
			switch (in.nextInt()) {
			case 1: {
				System.out.println("Insert email:");
				email = in.next();
				System.out.println("Insert First Name:");
				firstName = in.next();
				System.out.println("Insert Last Name:");
				lastName = in.next();
				System.out.println("Insert Password:");
				password = in.next();
				System.out.println("Insert Phone Number:");
				phoneNum = in.next();
				User obj = new User(firstName, lastName, email, phoneNum, password);
				if (accounts.addAccount(obj)) {
					System.out.println("You register successfuly!For log in press 2.");
				}
				break;
			}
			case 2: {
				System.out.println("Insert email:");
				email = in.next();
				System.out.println("Insert Password:");
				password = in.next();
				userId = accounts.logIn(email, password);
				if (userId != null) {
					System.out.println("Logged in successfully");
					while (true) {
						System.out.println("Press 1 for all items.");
						System.out.println("Press 2 for your items.");
						System.out.println("Press 3 for add a item.");
						System.out.println("Press 4 for favourite items.");
						System.out.println("Press 0 for Stopping the program.");
						switch (in.nextInt()) {
						case 1: {
							items.getItems().forEach(it -> System.out.println(it.toString()));
							System.out.println("Press 1 buying item.");
							System.out.println("Press 2 to add item as favourite.");
							System.out.println("Press 3 to search by date.");
							System.out.println("Press 0 for going back.");
							switch (in.nextInt()) {
							case 1:
								System.out.println("Insert item's id:");
								data = accounts.getUserPhoneNumber(items.getUser(in.next()));
								if (data != null) {
									System.out.println("Call seller :" + data);
								}
								break;
							case 2:
								System.out.println("Insert item's id:");
								faveItems.addFavouriteItems(userId, in.next());
								System.out.println("Item added to favourites.");
								break;
							case 3:
								System.out.println("Insert date from (YYYY-MM-DD):");
								dateFrom = LocalDate.parse(in.next());
								System.out.println("Insert date to (YYYY-MM-DD):");
								dateTo = LocalDate.parse(in.next());
								items.getItemsByDate(dateFrom, dateTo)
										.forEach(itemByDate -> System.out.println(itemByDate.toString()));
								break;
							}
							break;
						}
						case 2: {
							items.getUsersItems(userId).forEach(itemUsers -> System.out.println(itemUsers.toString()));

							System.out.println("Press 1 for update item.");
							System.out.println("Press 2 for deleting item.");
							System.out.println("Press 0 for going back.");
							switch (in.nextInt()) {
							case 1:
								System.out.println("Insert id of the item you want to update :");
								itemId = in.next();
								System.out.println("Insert name:");
								name = in.next();
								System.out.println("Insert price:");
								price = in.nextDouble();
								System.out.println("Insert descrption:");
								description = in.next();
								items.category.forEach(cat -> {
									System.out.println(cat.getName());
								});
								System.out.println("Insert category:");
								category = in.next();
								while (!items.getCategory(category)) {
									System.out.println("Insert valid category.");
									category = in.next();
								}
								System.out.println("Insert status:");
								status = in.nextBoolean();
								if (items.updateItem(userId, itemId, name, price, description, category, status)) {
									System.out.println("Item updated successfully");
								}
								break;
							case 2:
								System.out.println("Insert id of the item you want to delete :");
								items.deleteItem(userId, in.next());
								System.out.println("Item deleted!");
								break;
							case 0:
								break;
							}
							break;

						}
						case 3:
							System.out.println("Insert name:");
							name = in.next();
							System.out.println("Insert price:");
							price = in.nextDouble();
							System.out.println("Insert descption:");
							description = in.next();
							items.category.forEach(cat -> {
								System.out.println(cat.getName());
							});
							System.out.println("Insert category:");
							category = in.next();
							while (!items.getCategory(category)) {
								System.out.println("Insert valid category.");
								category = in.next();
							}
							Item item = new Item(userId, name, price, description, category);
							if (items.addItem(item)) {
								System.out.println("Succsesfullfy add a item.");
							}
							break;

						case 4:
							faveItems.getFavouriteItems(userId)
									.forEach(faveItem -> System.out.println(faveItem.toString()));
							;
							System.out.println("Press 1 to remove item from favourites.");
							System.out.println("Press 0 to go back.");
							switch (in.nextInt()) {
							case 1:
								System.out.println("Insert items id:");
								faveItems.removeFavouriteItems(userId, in.next());
								break;
							}
							break;
						case 0:
							System.exit(0);
							break;
						}
					}

				} else {
					System.out.println("Invalid email or password");
				}
				break;
			}
			case 3:
				System.out.println("Insert email:");
				email = in.next();
				System.out.println("Insert Password:");
				password = in.next();

				if (administrators.logIn(email, password)) {
					System.out.println("Logged in successfully");
					while (true) {
						System.out.println("Press 1 for all items.");
						System.out.println("Press 2 for all deactivated items.");
						System.out.println("Press 3 for categories.");
						System.out.println("Press 0 for Stopping the program.");

						switch (in.nextInt()) {
						case 1:
							administrators.getActiveItems();
							System.out.println("Press 1 to search by date:");
							System.out.println("Press 0 for going back:");
							switch (in.nextInt()) {
							case 1:
								System.out.println("Insert date from (YYYY-MM-DD):");
								dateFrom = LocalDate.parse(in.next());
								System.out.println("Insert date to (YYYY-MM-DD):");
								dateTo = LocalDate.parse(in.next());
								items.getItemsByDate(dateFrom, dateTo).forEach(activeItemsByDate -> System.out.println(activeItemsByDate.toString()));
								break;
							}
							break;
						case 2:
							administrators.getInactiveItems().forEach(inactiveItems -> System.out.println(inactiveItems.toString()));
							System.out.println("Press 1 to search by date:");
							System.out.println("Press 0 for going back:");
							switch (in.nextInt()) {
							case 1:
								System.out.println("Insert date from (YYYY-MM-DD):");
								dateFrom = LocalDate.parse(in.next());
								System.out.println("Insert date to (YYYY-MM-DD):");
								dateTo = LocalDate.parse(in.next());
								administrators.getInactiveItemsByDate(dateFrom, dateTo).forEach(inactiveItemsByDate -> System.out.println(inactiveItemsByDate.toString()));
								break;
							}
							break;
						case 3:
							administrators.getCategories().forEach(ct -> System.out.println(ct.getName()));
							System.out.println("Press 1 to add category.");
							System.out.println("Press 2 to update category.");
							System.out.println("Press 3 to delete category.");
							System.out.println("Press 0 for going back.");

							switch (in.nextInt()) {
							case 1:
								System.out.println("Insert name of category:");
								administrators.addCategory(in.next());
								break;
							case 2:
								System.out.println(
										"Insert the name of the category you want to change and the new name:");
								if (administrators.updateCategory(in.next(), in.next())) {
									System.out.println("Category updated successfully");
								}
								break;
							case 3:
								System.out.println("Insert name of category you want to delete:");
								administrators.deleteCategory(in.next());
								break;
							}
							break;
						case 0:
							System.exit(0);
						}
					}
				} else {
					System.out.println("Invalid email or password");
				}

				break;
			case 0:
				System.exit(0);
			}
		}
	}
}
