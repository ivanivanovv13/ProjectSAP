package View;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import Controller.AccountController;
import Controller.FavouriteItems;
import Controller.ItemsController;
import Model.NotEmailAddressException;
import Model.User;

import java.awt.Color;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.AccessController;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Font;
import java.awt.Label;

public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Button Previous;
	private Button Next;
	private Label name_1;
	private Label name_2;
	private Label name_3;
	private Label name_4;
	private Label price_1;
	private Label price_2;
	private Label price_3;
	private Label price_4;
	private int index = 0;
	private String page;
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
	public Menu(String databaseUrl, String databaseUser, String databasePassword, String userId)
			throws SQLException, NotEmailAddressException {
		AccountController accounts = new AccountController(databaseUrl, databaseUser, databasePassword);
		ItemsController items = new ItemsController(databaseUrl, databaseUser, databasePassword);
		FavouriteItems faveItems = new FavouriteItems(accounts.accounts, items.items, databaseUrl, databaseUser,
				databasePassword);
		this.userId = userId;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Button button = new Button("Show all Items.");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fetch(items,faveItems,accounts);
				page = "allItems";
				index = 0;
				showItems(index, items);
			}
		});
		button.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		button.setBackground(new Color(153, 0, 153));
		button.setBounds(0, 0, 245, 80);
		contentPane.add(button);

		Button button_1 = new Button("Show your items.");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fetch(items,faveItems,accounts);
				page = "myItems";
				index = 0;
				showUsersItems(index, items);
			}
		});
		button_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button_1.setBackground(new Color(153, 0, 153));
		button_1.setBounds(0, 73, 245, 80);
		contentPane.add(button_1);

		Button button_2 = new Button("Show your favourite items.");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fetch(items,faveItems,accounts);
				page = "favouriteItems";
				index = 0;
				showFavouriteItems(index, faveItems);
			}
		});
		button_2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button_2.setBackground(new Color(153, 0, 153));
		button_2.setBounds(241, 0, 245, 80);
		contentPane.add(button_2);

		Button button_2_1 = new Button("Add a item.");
		button_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddItem add = new AddItem(databaseUrl, databaseUser, databasePassword, userId);
					add.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (NotEmailAddressException e1) {
					e1.getMessage();
				}
			}
		});
		button_2_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button_2_1.setBackground(new Color(153, 0, 153));
		button_2_1.setBounds(241, 73, 245, 80);
		contentPane.add(button_2_1);

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

		Next = new Button("Next Items");
		Next.setForeground(new Color(0, 0, 0));
		Next.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		Next.setBackground(new Color(153, 0, 153));
		Next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index += 4;
				switch (page) {
				case "allItems":
					showItems(index, items);
					break;
				case "myItems":
					showUsersItems(index, items);
					break;
				}
			}
		});
		Next.setBounds(201, 415, 118, 38);
		contentPane.add(Next);

		Previous = new Button("Previous Items");
		Previous.setForeground(new Color(0, 0, 0));
		Previous.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		Previous.setBackground(new Color(153, 0, 153));
		Previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index -= 4;
				switch (page) {
				case "allItems":
					showItems(index, items);
					break;
				case "myItems":
					showUsersItems(index, items);
					break;
				}

			}
		});
		Previous.setBounds(201, 159, 118, 38);
		contentPane.add(Previous);

		showItem_1 = new Button("Show Item");
		showItem_1.setForeground(new Color(0, 0, 0));
		showItem_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		showItem_1.setBackground(new Color(153, 0, 153));
		showItem_1.setVisible(false);
		showItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buttonShow(0,databaseUrl,databaseUser,databasePassword,items,faveItems);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		showItem_1.setBounds(385, 219, 78, 21);
		contentPane.add(showItem_1);

		showItem_2 = new Button("Show Item");
		showItem_2.setForeground(new Color(0, 0, 0));
		showItem_2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		showItem_2.setBackground(new Color(153, 0, 153));
		showItem_2.setVisible(false);
		showItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buttonShow(1,databaseUrl,databaseUser,databasePassword,items,faveItems);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		showItem_2.setBounds(385, 262, 78, 21);
		contentPane.add(showItem_2);
		
		
		

		 
		showItem_3 = new Button("Show Item");
		showItem_3.setForeground(new Color(0, 0, 0));
		showItem_3.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		showItem_3.setBackground(new Color(153, 0, 153));
		showItem_3.setVisible(false);
		showItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buttonShow(2,databaseUrl,databaseUser,databasePassword,items,faveItems);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
					
		});
		showItem_3.setBounds(385, 310, 78, 21);
		contentPane.add(showItem_3);

		showItem_4 = new Button("Show Item");
		showItem_4.setForeground(new Color(0, 0, 0));
		showItem_4.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		showItem_4.setBackground(new Color(153, 0, 153));
		showItem_4.setVisible(false);
		showItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buttonShow(3,databaseUrl,databaseUser,databasePassword,items,faveItems);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
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

	private void showItems(int index, ItemsController items) {
		if (index == 0) {
			Previous.setEnabled(false);
		} else {
			Previous.setEnabled(true);
		}
		if (index == items.getItems().size() || (index + 1) == items.getItems().size()
				|| (index + 2) == items.getItems().size() || (index + 3) == items.getItems().size()) {
			Next.setEnabled(false);
		} else {
			Next.setEnabled(true);
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
			Previous.setEnabled(false);
		} else {
			Previous.setEnabled(true);
		}
		if (index == items.getUsersItems(userId).size() || (index + 1) == items.getUsersItems(userId).size()
				|| (index + 2) == items.getUsersItems(userId).size()
				|| (index + 3) == items.getUsersItems(userId).size()) {
			Next.setEnabled(false);
		} else {
			Next.setEnabled(true);
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
			Previous.setEnabled(false);
		} else {
			Previous.setEnabled(true);
		}
		if (index == faveItems.getFavouriteItems(userId).size()-1
				|| (index + 1) == faveItems.getFavouriteItems(userId).size()-1
				|| (index + 2) == faveItems.getFavouriteItems(userId).size()-1
				|| (index + 3) == faveItems.getFavouriteItems(userId).size()-1) {
			Next.setEnabled(false);
		} else {
			Next.setEnabled(true);
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
	private void buttonShow(int i,String databaseUrl,String databaseUser,String databasePassword,ItemsController items,FavouriteItems faveItems) throws IOException {
		showItem show = null;
		switch (page) {
		case "allItems":
			try {
				show = new showItem(items.getItems().get(index + i), page, databaseUrl, databaseUser, databasePassword,
						userId);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotEmailAddressException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			show.setVisible(true);
			break;
		case "myItems":
			try {
				show = new showItem(items.getUsersItems(userId).get(index + i), page, databaseUrl, databaseUser,
						databasePassword, userId);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotEmailAddressException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			show.setVisible(true);
			break;
		case "favouriteItems":
			try {
				show = new showItem(faveItems.getFavouriteItems(userId).get(index + i), page, databaseUrl, databaseUser,
						databasePassword, userId);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotEmailAddressException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			show.setVisible(true);
			break;
		default:
			break;

		}
	}
	
	private void fetch(ItemsController items,FavouriteItems faveItems,AccountController accounts) {
		items.items.clear();
		for (User temp : accounts.accounts) {
			temp.favouriteItems.clear();
		}
		try {
			items.fetchAllItems();
			faveItems.fetchAllFavouriteItems();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
