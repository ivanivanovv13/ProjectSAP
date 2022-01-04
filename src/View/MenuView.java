package View;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import Controller.AccountController;
import Controller.FavouriteItems;
import Controller.ItemsController;
import Model.NotEmailAddressException;
import Model.User;

import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Font;
import java.awt.Label;

public class MenuView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AccountController accounts;
	private ItemsController items;
	FavouriteItems faveItems;
	private JPanel contentPane;
	private Button previous;
	private String databaseUrl;
	private String databaseUser;
	private String databasePassword;
	private Button next;
	private Label name_1;
	private Label name_2;
	private Label name_3;
	private Label name_4;
	private Label price_1;
	private Label price_2;
	private Label price_3;
	private Label price_4;
	private int index = 0;
	private enum page {
		allItems, myItems, favouriteItems
	}
	private page myPage;
	private String userId;
	private Label date_1;
	private Label date_2;
	private Label date_3;
	private Label date_4;
	private Button showItem_1;
	private Button showItem_2;
	private Button showItem_3;
	private Button showItem_4;

	/**
	 * Create the frame.
	 * 
	 * @throws NotEmailAddressException
	 * @throws SQLException
	 */
	public MenuView(String databaseUrl, String databaseUser, String databasePassword, String userId)
			throws SQLException, NotEmailAddressException {
		 accounts = new AccountController(databaseUrl, databaseUser, databasePassword);
		 items = new ItemsController(databaseUrl, databaseUser, databasePassword);
		 faveItems = new FavouriteItems(accounts.getAccounts(), items.getListItems(), databaseUrl, databaseUser,
				databasePassword);
		this.userId = userId;
		this.databasePassword=databasePassword;
		this.databaseUrl=databaseUrl;
		this.databaseUser=databaseUser;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Button button_ShowItems = new Button("Show all Items.");
		button_ShowItems.addActionListener(e -> onButtonClickedShowAllItems(e));
		button_ShowItems.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		button_ShowItems.setBackground(new Color(153, 0, 153));
		button_ShowItems.setBounds(0, 0, 245, 80);
		contentPane.add(button_ShowItems);

		Button button_ShowUserItems = new Button("Show your items.");
		button_ShowUserItems.addActionListener(e-> onButtonClickedShowItems(e));
		button_ShowUserItems.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button_ShowUserItems.setBackground(new Color(153, 0, 153));
		button_ShowUserItems.setBounds(0, 73, 245, 80);
		contentPane.add(button_ShowUserItems);

		Button button_ShowFavouriteItems = new Button("Show your favourite items.");
		button_ShowFavouriteItems.addActionListener(e-> onButtonClickedShowFavouriteItems(e));
		button_ShowFavouriteItems.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button_ShowFavouriteItems.setBackground(new Color(153, 0, 153));
		button_ShowFavouriteItems.setBounds(241, 0, 245, 80);
		contentPane.add(button_ShowFavouriteItems);

		Button button_AddItem = new Button("Add a item.");
		button_AddItem.addActionListener(e -> onButtonClickedAddItem(e));
		button_AddItem.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button_AddItem.setBackground(new Color(153, 0, 153));
		button_AddItem.setBounds(241, 73, 245, 80);
		contentPane.add(button_AddItem);

		name_1 = new Label("");
		name_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		name_1.setBounds(38, 219, 116, 21);
		contentPane.add(name_1);

		name_2 = new Label("");
		name_2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		name_2.setBounds(38, 262, 116, 21);
		contentPane.add(name_2);

		name_3 = new Label("");
		name_3.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		name_3.setBounds(38, 310, 116, 21);
		contentPane.add(name_3);

		name_4 = new Label("");
		name_4.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		name_4.setBounds(38, 359, 116, 21);
		contentPane.add(name_4);

		next = new Button("Next Items");
		next.setForeground(new Color(0, 0, 0));
		next.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		next.setBackground(new Color(153, 0, 153));
		next.addActionListener(e -> onButtonClickedNext(e));
		next.setBounds(201, 415, 118, 38);
		contentPane.add(next);

		previous = new Button("Previous Items");
		previous.setForeground(new Color(0, 0, 0));
		previous.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		previous.setBackground(new Color(153, 0, 153));
		previous.addActionListener(e -> onButtonClickedPrevious(e));
		previous.setBounds(201, 159, 118, 38);
		contentPane.add(previous);

		showItem_1 = new Button("Show Item");
		showItem_1.setForeground(new Color(0, 0, 0));
		showItem_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		showItem_1.setBackground(new Color(153, 0, 153));
		showItem_1.setVisible(false);
		showItem_1.addActionListener(e->onButtonClickedShowItem(e,0));
		showItem_1.setBounds(385, 219, 78, 21);
		contentPane.add(showItem_1);

		showItem_2 = new Button("Show Item");
		showItem_2.setForeground(new Color(0, 0, 0));
		showItem_2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		showItem_2.setBackground(new Color(153, 0, 153));
		showItem_2.setVisible(false);
		showItem_2.addActionListener(e -> onButtonClickedShowItem(e,1));
		showItem_2.setBounds(385, 262, 78, 21);
		contentPane.add(showItem_2);

		showItem_3 = new Button("Show Item");
		showItem_3.setForeground(new Color(0, 0, 0));
		showItem_3.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		showItem_3.setBackground(new Color(153, 0, 153));
		showItem_3.setVisible(false);
		showItem_3.addActionListener(e -> onButtonClickedShowItem(e,2));
		showItem_3.setBounds(385, 310, 78, 21);
		contentPane.add(showItem_3);

		showItem_4 = new Button("Show Item");
		showItem_4.setForeground(new Color(0, 0, 0));
		showItem_4.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		showItem_4.setBackground(new Color(153, 0, 153));
		showItem_4.setVisible(false);
		showItem_4.addActionListener(e -> onButtonClickedShowItem(e,3));
		showItem_4.setBounds(385, 359, 78, 21);
		contentPane.add(showItem_4);

		price_1 = new Label("");
		price_1.setBounds(160, 219, 59, 21);
		contentPane.add(price_1);

		price_2 = new Label("");
		price_2.setBounds(160, 262, 59, 21);
		contentPane.add(price_2);

		price_3 = new Label("");
		price_3.setBounds(160, 310, 59, 21);
		contentPane.add(price_3);

		price_4 = new Label("");
		price_4.setBounds(160, 359, 59, 21);
		contentPane.add(price_4);

		date_1 = new Label("");
		date_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		date_1.setBounds(255, 219, 104, 21);
		contentPane.add(date_1);

		date_2 = new Label("");
		date_2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		date_2.setBounds(255, 262, 104, 21);
		contentPane.add(date_2);

		date_3 = new Label("");
		date_3.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		date_3.setBounds(255, 310, 104, 21);
		contentPane.add(date_3);

		date_4 = new Label("");
		date_4.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		date_4.setBounds(255, 359, 104, 21);
		contentPane.add(date_4);
	}

	private void onButtonClickedShowItem(ActionEvent e,int index) {
		try {
			buttonShow(index, databaseUrl, databaseUser, databasePassword, items, faveItems);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		}
	}

	private void onButtonClickedShowAllItems(ActionEvent e) {
		fetch(items, faveItems, accounts);
		myPage=page.allItems ;
		index = 0;
		showItems(index, items);
	}

	private void onButtonClickedShowFavouriteItems(ActionEvent e) {
		fetch(items, faveItems, accounts);
		myPage=page.favouriteItems;
		index = 0;
		showFavouriteItems(index, faveItems);
	}

	private void onButtonClickedShowItems(ActionEvent e) {
		fetch(items, faveItems, accounts);
		myPage=page.myItems;
		index = 0;
		showUsersItems(index, items);
	}

	private void onButtonClickedAddItem(ActionEvent e) {
		try {
			AddItemView add = new AddItemView(databaseUrl, databaseUser, databasePassword, userId);
			add.setVisible(true);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		} catch (NotEmailAddressException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.getMessage();
		}
	}

	private void onButtonClickedPrevious(ActionEvent e) {
		index -= 4;
		switch (myPage) {
		case allItems:
			showItems(index, items);
			break;
		case myItems:
			showUsersItems(index, items);
			break;
		case favouriteItems:
			showFavouriteItems(index, faveItems);
			break;
		}
	}

	private void onButtonClickedNext(ActionEvent e) {
		index += 4;
		switch (myPage) {
		case allItems:
			showItems(index, items);
			break;
		case myItems:
			showUsersItems(index, items);
			break;
		case favouriteItems:
			showFavouriteItems(index, faveItems);
			break;
		}
	}

	private void showItems(int index, ItemsController items) {
		if (index == 0) {
			previous.setEnabled(false);
		} else {
			previous.setEnabled(true);
		}
		if (index == items.getItems().size() || (index + 1) == items.getItems().size()
				|| (index + 2) == items.getItems().size() || (index + 3) == items.getItems().size()) {
			next.setEnabled(false);
		} else {
			next.setEnabled(true);
		}
		if (index <= items.getItems().size() - 1) {
			showItem_1.setVisible(true);
			name_1.setText(items.getItems().get(0 + index).getName());
			price_1.setText(String.valueOf(items.getItems().get(0 + index).getPrice()) + "lv");
			date_1.setText(String.valueOf(items.getItems().get(0 + index).getDate()));
		} else {
			showItem_1.setVisible(false);
			name_1.setText("");
			price_1.setText("");
			date_1.setText("");
		}
		if ((index + 1) <= items.getItems().size() - 1) {
			showItem_2.setVisible(true);
			name_2.setText(items.getItems().get(1 + index).getName());
			price_2.setText(String.valueOf(items.getItems().get(1 + index).getPrice()) + "lv");
			date_2.setText(String.valueOf(items.getItems().get(1 + index).getDate()));
		} else {
			showItem_2.setVisible(false);
			name_2.setText("");
			price_2.setText("");
			date_2.setText("");
		}
		if ((index + 2) <= items.getItems().size() - 1) {
			showItem_3.setVisible(true);
			name_3.setText(items.getItems().get(2 + index).getName());
			price_3.setText(String.valueOf(items.getItems().get(2 + index).getPrice()) + "lv");
			date_3.setText(String.valueOf(items.getItems().get(2 + index).getDate()));
		} else {
			showItem_3.setVisible(false);
			name_3.setText("");
			price_3.setText("");
			date_3.setText("");
		}
		if ((index + 3) <= items.getItems().size() - 1) {
			showItem_4.setVisible(true);
			name_4.setText(items.getItems().get(3 + index).getName());
			price_4.setText(String.valueOf(items.getItems().get(3 + index).getPrice()) + "lv");
			date_4.setText(String.valueOf(items.getItems().get(3 + index).getDate()));
		} else {
			showItem_4.setVisible(false);
			name_4.setText("");
			price_4.setText("");
			date_4.setText("");
		}

	}

	private void showUsersItems(int index, ItemsController items) {
		if (index == 0) {
			previous.setEnabled(false);
		} else {
			previous.setEnabled(true);
		}
		if (index == items.getUsersItems(userId).size() || (index + 1) == items.getUsersItems(userId).size()
				|| (index + 2) == items.getUsersItems(userId).size()
				|| (index + 3) == items.getUsersItems(userId).size()) {
			next.setEnabled(false);
		} else {
			next.setEnabled(true);
		}
		if (index <= items.getUsersItems(userId).size() - 1) {
			showItem_1.setVisible(true);
			name_1.setText(items.getUsersItems(userId).get(index).getName());
			price_1.setText(String.valueOf(items.getUsersItems(userId).get(index).getPrice()) + "lv");
			date_1.setText(String.valueOf(items.getUsersItems(userId).get(index).getDate()));
		} else {
			showItem_1.setVisible(false);
			name_1.setText("");
			price_1.setText("");
			date_1.setText("");
		}
		if ((index + 1) <= items.getUsersItems(userId).size() - 1) {
			showItem_2.setVisible(true);
			name_2.setText(items.getUsersItems(userId).get(1 + index).getName());
			price_2.setText(String.valueOf(items.getUsersItems(userId).get(1 + index).getPrice()) + "lv");
			date_2.setText(String.valueOf(items.getUsersItems(userId).get(1 + index).getDate()));
		} else {
			showItem_2.setVisible(false);
			name_2.setText("");
			price_2.setText("");
			date_2.setText("");
		}
		if ((index + 2) <= items.getUsersItems(userId).size() - 1) {
			showItem_3.setVisible(true);
			name_3.setText(items.getUsersItems(userId).get(2 + index).getName());
			price_3.setText(String.valueOf(items.getUsersItems(userId).get(2 + index).getPrice()) + "lv");
			date_3.setText(String.valueOf(items.getUsersItems(userId).get(2 + index).getDate()));
		} else {
			showItem_3.setVisible(false);
			name_3.setText("");
			price_3.setText("");
			date_3.setText("");
		}
		if ((index + 3) <= items.getUsersItems(userId).size() - 1) {
			showItem_4.setVisible(true);
			name_4.setText(items.getUsersItems(userId).get(3 + index).getName());
			price_4.setText(String.valueOf(items.getUsersItems(userId).get(3 + index).getPrice()) + "lv");
			date_4.setText(String.valueOf(items.getUsersItems(userId).get(3 + index).getDate()));
		} else {
			showItem_4.setVisible(false);
			name_4.setText("");
			price_4.setText("");
			date_4.setText("");
		}

	}

	private void showFavouriteItems(int index, FavouriteItems faveItems) {
		if (index == 0) {
			previous.setEnabled(false);
		} else {
			previous.setEnabled(true);
		}
		if (index == faveItems.getFavouriteItems(userId).size() - 1
				|| (index + 1) == faveItems.getFavouriteItems(userId).size() - 1
				|| (index + 2) == faveItems.getFavouriteItems(userId).size() - 1
				|| (index + 3) == faveItems.getFavouriteItems(userId).size() - 1) {
			next.setEnabled(false);
		} else {
			next.setEnabled(true);
		}
		if (index <= faveItems.getFavouriteItems(userId).size() - 1) {
			showItem_1.setVisible(true);
			name_1.setText(faveItems.getFavouriteItems(userId).get(index).getName());
			price_1.setText(String.valueOf(faveItems.getFavouriteItems(userId).get(index).getPrice()) + "lv");
			date_1.setText(String.valueOf(faveItems.getFavouriteItems(userId).get(index).getDate()));
		} else {
			showItem_1.setVisible(false);
			name_1.setText("");
			price_1.setText("");
			date_1.setText("");
		}
		if ((index + 1) <= faveItems.getFavouriteItems(userId).size() - 1) {
			showItem_2.setVisible(true);
			name_2.setText(faveItems.getFavouriteItems(userId).get(1 + index).getName());
			price_2.setText(String.valueOf(faveItems.getFavouriteItems(userId).get(1 + index).getPrice()) + "lv");
			date_2.setText(String.valueOf(faveItems.getFavouriteItems(userId).get(1 + index).getDate()));
		} else {
			showItem_2.setVisible(false);
			name_2.setText("");
			price_2.setText("");
			date_2.setText("");
		}
		if ((index + 2) <= faveItems.getFavouriteItems(userId).size() - 1) {
			showItem_3.setVisible(true);
			name_3.setText(faveItems.getFavouriteItems(userId).get(2 + index).getName());
			price_3.setText(String.valueOf(faveItems.getFavouriteItems(userId).get(2 + index).getPrice()) + "lv");
			date_3.setText(String.valueOf(faveItems.getFavouriteItems(userId).get(2 + index).getDate()));
		} else {
			showItem_3.setVisible(false);
			name_3.setText("");
			price_3.setText("");
			date_3.setText("");
			name_3.setText("");
		}
		if ((index + 3) <= faveItems.getFavouriteItems(userId).size() - 1) {
			showItem_4.setVisible(true);
			name_4.setText(faveItems.getFavouriteItems(userId).get(3 + index).getName());
			price_4.setText(String.valueOf(faveItems.getFavouriteItems(userId).get(3 + index).getPrice()) + "lv");
			date_4.setText(String.valueOf(faveItems.getFavouriteItems(userId).get(3 + index).getDate()));
		} else {
			showItem_4.setVisible(false);
			name_4.setText("");
			price_4.setText("");
			date_4.setText("");
		}

	}

	private void buttonShow(int i, String databaseUrl, String databaseUser, String databasePassword,
			ItemsController items, FavouriteItems faveItems) throws IOException {
		ShowItemView show = null;
		switch (myPage) {
		case allItems:
			try {
				show = new ShowItemView(items.getItems().get(index + i), myPage.toString(), databaseUrl, databaseUser,
						databasePassword, userId);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
				e1.printStackTrace();
			} catch (NotEmailAddressException e1) {
				JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
				e1.printStackTrace();
			}
			show.setVisible(true);
			break;
		case myItems:
			try {
				show = new ShowItemView(items.getUsersItems(userId).get(index + i), myPage.toString(), databaseUrl, databaseUser,
						databasePassword, userId);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
				e1.printStackTrace();
			} catch (NotEmailAddressException e1) {
				JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
				e1.printStackTrace();
			}
			show.setVisible(true);
			break;
		case favouriteItems:
			try {
				show = new ShowItemView(faveItems.getFavouriteItems(userId).get(index + i),myPage.toString(), databaseUrl,
						databaseUser, databasePassword, userId);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
				e1.printStackTrace();
			} catch (NotEmailAddressException e1) {
				JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
				e1.printStackTrace();
			}
			show.setVisible(true);
			break;
		default:
			break;

		}
	}

	private void fetch(ItemsController items, FavouriteItems faveItems, AccountController accounts) {
		items.getListItems().clear();
		for (User temp : accounts.getAccounts()) {
			temp.getFavouriteItems().clear();
		}
		try {
			items.fetchAllItems();
			faveItems.fetchAllFavouriteItems();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		}
	}
}
