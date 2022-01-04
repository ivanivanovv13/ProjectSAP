package View;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ItemsController;
import Model.Category;
import Model.Item;
import Model.NotEmailAddressException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import java.awt.Choice;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AddItemView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private JPanel contentPane;
	private ItemsController items = null;
	private JLabel jlabel_Image;
	private File file;
	private Label label_Name;
	private Button button_addImage;
	private TextField name;
	private Label label_Price;
	private TextField price;
	private Label label_AddItem;
	private Label label_Category;
	private Label label_Description;
	private Choice category;
	private JTextArea desc;
	private JPanel panel;

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * @throws NotEmailAddressException
	 */
	public AddItemView(String databaseUrl, String databaseUser, String databasePassword, String userId)
			throws SQLException, NotEmailAddressException {
		items = new ItemsController(databaseUrl, databaseUser, databasePassword);
		this.userId=userId;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 762);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		label_Name = new Label("Name");
		label_Name.setForeground(new Color(153, 0, 153));
		label_Name.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Name.setBounds(76, 78, 59, 21);
		contentPane.add(label_Name);

		name = new TextField();
		name.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		name.setBounds(76, 105, 242, 28);
		contentPane.add(name);

		label_Price = new Label("Price:");
		label_Price.setForeground(new Color(153, 0, 153));
		label_Price.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Price.setBounds(76, 139, 98, 21);
		contentPane.add(label_Price);

		price = new TextField();
		price.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		price.setBounds(76, 166, 242, 28);
		contentPane.add(price);

		label_AddItem = new Label("Add item");
		label_AddItem.setAlignment(Label.CENTER);
		label_AddItem.setForeground(new Color(153, 0, 153));
		label_AddItem.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 24));
		label_AddItem.setBounds(87, 10, 195, 44);
		contentPane.add(label_AddItem);

		label_Category = new Label("Category\r\n");
		label_Category.setForeground(new Color(153, 0, 153));
		label_Category.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Category.setBounds(76, 200, 98, 21);
		contentPane.add(label_Category);

		label_Description = new Label("Description");
		label_Description.setForeground(new Color(153, 0, 153));
		label_Description.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Description.setBounds(76, 251, 98, 21);
		contentPane.add(label_Description);

		category = new Choice();
		category.setBounds(76, 227, 242, 28);
		contentPane.add(category);
		addCategories(items.getListCategory(), category);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(76, 287, 254, 213);
		contentPane.add(panel);
		panel.setLayout(null);

		 desc = new JTextArea();
		desc.setBounds(6, 15, 242, 192);
		panel.add(desc);
		desc.setBackground(UIManager.getColor("CheckBox.light"));
		desc.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));

		Button close = new Button("Close");
		close.addActionListener(e -> onButtonClickedClose(e));
		close.setBackground(new Color(153, 0, 153));
		close.setForeground(Color.WHITE);
		close.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		close.setBounds(10, 694, 82, 21);
		contentPane.add(close);

		Button button_Add = new Button("Add");
		button_Add.addActionListener(e -> onButtonClickAdd(e));
		button_Add.setForeground(Color.WHITE);
		button_Add.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button_Add.setBackground(new Color(153, 0, 153));
		button_Add.setBounds(294, 694, 82, 21);
		contentPane.add(button_Add);

		button_addImage = new Button("Add image.");
		button_addImage.addActionListener(e -> onButtonClickedAddImage(e));
		button_addImage.setForeground(new Color(255, 255, 255));
		button_addImage.setBackground(new Color(153, 0, 153));
		button_addImage.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button_addImage.setBounds(294, 667, 82, 21);
		contentPane.add(button_addImage);
		jlabel_Image = new JLabel("Image");
		jlabel_Image.setBounds(76, 510, 254, 153);
		contentPane.add(jlabel_Image);

	}

	private void onButtonClickAdd(ActionEvent e) {
		Blob blob = null;
		try {
			blob = convertFileContentToBlob(file.getAbsolutePath());
		} catch (SerialException e2) {
			JOptionPane.showMessageDialog(new JPanel(), e2.getMessage());
			e2.printStackTrace();
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(new JPanel(), e2.getMessage());
			e2.printStackTrace();
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(new JPanel(), e2.getMessage());
			e2.printStackTrace();
		}
		Item item = new Item(userId, name.getText(), Double.parseDouble(price.getText()), desc.getText(),
				category.getSelectedItem(), blob);
		try {
			if (items.addItem(item)) {
				contentPane.setVisible(false);
				dispose();
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		}
	}

	private void onButtonClickedAddImage(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(button_addImage);
		file = chooser.getSelectedFile();
		String name = file.getAbsolutePath();
		ImageIcon imgThisImg = new ImageIcon(name);
		Image myImage = imgThisImg.getImage();
		Image newImage = myImage.getScaledInstance(jlabel_Image.getWidth(), jlabel_Image.getHeight(),
				Image.SCALE_SMOOTH);
		ImageIcon img = new ImageIcon(newImage);
		jlabel_Image.setIcon(img);
	}
	
	private void onButtonClickedClose(ActionEvent e) {
		contentPane.setVisible(false);
		dispose();
	}

	private Choice addCategories(List<Category> list, Choice ch) {
		for (Category temp : list) {
			ch.addItem(temp.getName());
		}
		return ch;
	}

	private static Blob convertFileContentToBlob(String filePathStr) throws IOException, SerialException, SQLException {
		// get path object pointing to file
		Path filePath = Paths.get(filePathStr);
		// get byte array with file contents
		byte[] fileContent = Files.readAllBytes(filePath);
		Blob blob = null;
		blob = new SerialBlob(fileContent);
		return blob;
	}
}
