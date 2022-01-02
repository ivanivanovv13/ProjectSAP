package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import Controller.AccountController;
import Controller.FavouriteItems;
import Controller.ItemsController;
import Model.Item;
import Model.NotEmailAddressException;

import java.awt.Label;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Image;

public class showItem extends JFrame {

	private JPanel contentPane;
	Button button_1;
	Button button_2;
	Button button_3;

	/**
	 * Create the frame.
	 * 
	 * @throws NotEmailAddressException
	 * @throws SQLException
	 * @throws IOException
	 */
	public showItem(Item item, String type, String databaseUrl, String databaseUser, String databasePassword,
			String userId) throws SQLException, NotEmailAddressException, IOException {
		AccountController accounts = new AccountController(databaseUrl, databaseUser, databasePassword);
		ItemsController items = new ItemsController(databaseUrl, databaseUser, databasePassword);
		FavouriteItems faveItems = new FavouriteItems(accounts.accounts, items.items, databaseUrl, databaseUser,
				databasePassword);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Image");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBackground(UIManager.getColor("Button.darkShadow"));
		lblNewLabel.setBounds(30, 25, 172, 166);
		contentPane.add(lblNewLabel);
		if (item.getImage() != null) {
			InputStream in = item.getImage().getBinaryStream();
			BufferedImage image = ImageIO.read(in);
			Image myImage = image;
			Image newImage= myImage.getScaledInstance(lblNewLabel.getWidth(),lblNewLabel.getHeight(),Image.SCALE_SMOOTH);
			ImageIcon img = new ImageIcon(newImage);
			lblNewLabel.setIcon(img);
		}
		
			Label label = new Label("Name: " + item.getName());
			label.setBounds(239, 25, 219, 21);
			contentPane.add(label);

			Label label_1 = new Label("Price: " + String.valueOf(item.getPrice()));
			label_1.setBounds(239, 65, 219, 21);
			contentPane.add(label_1);

			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(233, 208, 231, 151);
			contentPane.add(panel);
			panel.setLayout(null);

			Label label_2 = new Label("Description: " + item.getDescription());
			label_2.setBounds(6, 15, 219, 130);
			panel.add(label_2);
			Border border = BorderFactory.createLineBorder(Color.blue, 5);

			Label label_3 = new Label(accounts.getUserPhoneNumber(items.getUser(item.getId())));
			label_3.setBounds(239, 145, 219, 21);
			contentPane.add(label_3);

			Button button = new Button("Close");
			button.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
			button.setBackground(new Color(153, 0, 153));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ClosePane();
				}
			});
			button.setBounds(10, 332, 79, 21);
			contentPane.add(button);

			button_1 = new Button("Add to favourite");
			button_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
			button_1.setBackground(new Color(153, 0, 153));
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (button_1.getLabel().equals("Remove item from Favourite")) {
						try {
							faveItems.removeFavouriteItems(item.getUserId(), item.getId());
							button_1.setEnabled(false);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else {
						try {
							faveItems.addFavouriteItems(userId, item.getId());
							button_1.setEnabled(false);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			button_1.setBounds(10, 221, 192, 21);
			contentPane.add(button_1);

			button_2 = new Button("Update Item");
			button_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UpdateItem update;
					try {
						update = new UpdateItem(databaseUrl, databaseUser, databasePassword, userId, item);
						update.setVisible(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NotEmailAddressException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
			button_2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
			button_2.setBackground(new Color(153, 0, 153));
			button_2.setBounds(10, 248, 192, 21);
			contentPane.add(button_2);

			button_3 = new Button("Delete Item");
			button_3.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
			button_3.setBackground(new Color(153, 0, 153));
			button_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						items.deleteItem(item.getUserId(), item.getId());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					ClosePane();
				}
			});
			button_3.setBounds(10, 275, 192, 21);
			contentPane.add(button_3);

			Label date = new Label("");
			date.setBounds(239, 92, 219, 21);
			date.setText(String.valueOf(item.getDate()));
			contentPane.add(date);

			Label status = new Label("");
			status.setBounds(239, 119, 219, 21);
			status.setText("Active: " + item.isActive());
			contentPane.add(status);

			switch (type) {
			case "myItems":
				button_2.setVisible(true);
				button_3.setVisible(true);
				button_1.setVisible(false);
				break;
			case "allItems":
				button_2.setVisible(false);
				button_3.setVisible(false);
				button_1.setVisible(true);
				break;
			case "favouriteItems":
				button_2.setVisible(false);
				button_3.setVisible(false);
				button_1.setVisible(true);
				button_1.setLabel("Remove item from Favourite");
				break;
			}
		}

	private void ClosePane() {
		contentPane.setVisible(false);
		dispose();
	}
	

}
