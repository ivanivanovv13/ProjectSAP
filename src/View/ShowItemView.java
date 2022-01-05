package View;

import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Controller.AccountController;
import Controller.FavouriteItems;
import Controller.ItemsController;
import Model.Item;
import Model.NotEmailAddressException;
import java.awt.Label;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Button;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Image;

public class ShowItemView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AccountController accounts;
	private ItemsController items;
	private FavouriteItems faveItems;
	private JPanel contentPane;
	private Button button_AddFavourite;
	private Button button_UpdateItem;
	private Button button_DeleteItem;
	private String databaseUrl;
	private String databaseUser;
	private String databasePassword;
	private Item item;
	private String userId;

	/**
	 * Create the frame.
	 * 
	 * @throws NotEmailAddressException
	 * @throws SQLException
	 * @throws IOException
	 */
	public ShowItemView(Item item, String type, String databaseUrl, String databaseUser, String databasePassword,
			String userId) throws SQLException, NotEmailAddressException, IOException {
		accounts = new AccountController(databaseUrl, databaseUser, databasePassword);
		items = new ItemsController(databaseUrl, databaseUser, databasePassword);
		faveItems = new FavouriteItems(accounts.getAccounts(), items.getListItems(), databaseUrl, databaseUser, databasePassword);
		this.databasePassword=databasePassword;
		this.databaseUrl=databaseUrl;
		this.databaseUser=databaseUser;
		this.item = item;
		this.userId = userId;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel jlabel_Image = new JLabel("Image");
		jlabel_Image.setForeground(Color.BLACK);
		jlabel_Image.setBackground(UIManager.getColor("Button.darkShadow"));
		jlabel_Image.setBounds(30, 25, 172, 166);
		contentPane.add(jlabel_Image);
		if (item.getImage() != null) {
			InputStream in = item.getImage().getBinaryStream();
			BufferedImage image = ImageIO.read(in);
			Image myImage = image;
			Image newImage = myImage.getScaledInstance(jlabel_Image.getWidth(), jlabel_Image.getHeight(),
					Image.SCALE_SMOOTH);
			ImageIcon img = new ImageIcon(newImage);
			jlabel_Image.setIcon(img);
		}

		Label label_Name = new Label("Name: " + item.getName());
		label_Name.setBounds(239, 25, 219, 21);
		contentPane.add(label_Name);

		Label label_Price = new Label("Price: " + String.valueOf(item.getPrice()));
		label_Price.setBounds(239, 65, 219, 21);
		contentPane.add(label_Price);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(233, 208, 231, 151);
		contentPane.add(panel);
		panel.setLayout(null);

		Label label_Description = new Label("Description: " + item.getDescription());
		label_Description.setBounds(6, 15, 219, 130);
		panel.add(label_Description);

		Label label_3 = new Label(accounts.getUserPhoneNumber(items.getUser(item.getId())));
		label_3.setBounds(239, 145, 219, 21);
		contentPane.add(label_3);

		Button button_Close = new Button("Close");
		button_Close.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		button_Close.setBackground(new Color(153, 0, 153));
		button_Close.addActionListener(e -> ClosePane());
		button_Close.setBounds(10, 332, 79, 21);
		contentPane.add(button_Close);

		button_AddFavourite = new Button("Add to favourite");
		button_AddFavourite.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		button_AddFavourite.setBackground(new Color(153, 0, 153));
		button_AddFavourite.addActionListener(e -> onButtonClickedAddFavourites(e));
		button_AddFavourite.setBounds(10, 221, 192, 21);
		contentPane.add(button_AddFavourite);

		button_UpdateItem = new Button("Update Item");
		button_UpdateItem.addActionListener(e -> onButtonClickedUpdateItems(e));
		button_UpdateItem.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		button_UpdateItem.setBackground(new Color(153, 0, 153));
		button_UpdateItem.setBounds(10, 248, 192, 21);
		contentPane.add(button_UpdateItem);

		button_DeleteItem = new Button("Delete Item");
		button_DeleteItem.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		button_DeleteItem.setBackground(new Color(153, 0, 153));
		button_DeleteItem.addActionListener(e->onButtonClickedDeleteItem(e));
		button_DeleteItem.setBounds(10, 275, 192, 21);
		contentPane.add(button_DeleteItem);

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
			button_UpdateItem.setVisible(true);
			button_DeleteItem.setVisible(true);
			button_AddFavourite.setVisible(false);
			break;
		case "allItems":
			button_UpdateItem.setVisible(false);
			button_DeleteItem.setVisible(false);
			button_AddFavourite.setVisible(true);
			break;
		case "favouriteItems":
			button_UpdateItem.setVisible(false);
			button_DeleteItem.setVisible(false);
			button_AddFavourite.setVisible(true);
			button_AddFavourite.setLabel("Remove item from Favourite");
			break;
		}
	}

	private void onButtonClickedDeleteItem(ActionEvent e) {
		try {
			items.deleteItem(item.getUserId(), item.getId());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ClosePane();
	}

	private void onButtonClickedUpdateItems(ActionEvent e) {
		UpdateItemView update;
		try {
			update = new UpdateItemView(databaseUrl, databaseUser, databasePassword, userId, item);
			update.setVisible(true);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		} catch (NotEmailAddressException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		}
	}


	private void onButtonClickedAddFavourites(ActionEvent e) {
		if (button_AddFavourite.getLabel().equals("Remove item from Favourite")) {
			try {
				faveItems.removeFavouriteItems(userId, item.getId());
				button_AddFavourite.setEnabled(false);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
				e1.printStackTrace();
			}
		} else {
			try {
				faveItems.addFavouriteItems(userId, item.getId());
				button_AddFavourite.setEnabled(false);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
				e1.printStackTrace();
			}
		}
	}

	private void ClosePane() {
		contentPane.setVisible(false);
		dispose();
	}

}
