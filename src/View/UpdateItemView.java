package View;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Controller.ItemsController;
import Model.Category;
import Model.Item;
import Model.NotEmailAddressException;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class UpdateItemView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ItemsController items;
	private String userId;
	private Item item;
	private JRadioButton rdbtnInactive;
	private JRadioButton rdbtnActive;
	private File file;
	private JLabel jlabel_Image;
	private Button button_AddImage;
	private TextField name;
	private TextField price;
	private Choice category;
	private JTextArea description;

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public UpdateItemView(String databaseUrl, String databaseUser, String databasePassword, String userId, Item item)
			throws SQLException, NotEmailAddressException, IOException {
		items = new ItemsController(databaseUrl, databaseUser, databasePassword);
		this.item = item;
		this.userId = userId;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Label label_Name = new Label("Name");
		label_Name.setForeground(new Color(153, 0, 153));
		label_Name.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Name.setBounds(76, 78, 59, 21);
		contentPane.add(label_Name);

		name = new TextField();
		name.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		name.setBounds(76, 105, 242, 28);
		name.setText(item.getName());
		contentPane.add(name);

		Label label_Price = new Label("Price:");
		label_Price.setForeground(new Color(153, 0, 153));
		label_Price.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Price.setBounds(76, 139, 98, 21);
		contentPane.add(label_Price);

		price = new TextField();
		price.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		price.setBounds(76, 166, 242, 28);
		price.setText(String.valueOf(item.getPrice()));
		contentPane.add(price);

		Label label_Tittle = new Label("Update item");
		label_Tittle.setAlignment(Label.CENTER);
		label_Tittle.setForeground(new Color(153, 0, 153));
		label_Tittle.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 24));
		label_Tittle.setBounds(87, 10, 231, 44);
		contentPane.add(label_Tittle);

		Label label_Category = new Label("Category\r\n");
		label_Category.setForeground(new Color(153, 0, 153));
		label_Category.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Category.setBounds(76, 200, 98, 21);
		contentPane.add(label_Category);

		Label label_Description = new Label("Description");
		label_Description.setForeground(new Color(153, 0, 153));
		label_Description.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Description.setBounds(76, 251, 98, 21);
		contentPane.add(label_Description);

		category = new Choice();
		category.setBounds(76, 227, 242, 28);
		addCategories(items.getListCategory(), category);
		category.select(item.getCategory());

		contentPane.add(category);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(76, 287, 254, 213);
		contentPane.add(panel);
		panel.setLayout(null);

		description = new JTextArea();
		description.setBounds(6, 15, 242, 192);
		panel.add(description);
		description.setBackground(UIManager.getColor("CheckBox.light"));
		description.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		description.setText(item.getDescription());

		Button close = new Button("Close");
		close.addActionListener(e -> onButtonClose(e));
		close.setBackground(new Color(153, 0, 153));
		close.setForeground(Color.WHITE);
		close.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		close.setBounds(10, 732, 82, 21);
		contentPane.add(close);

		Button button_Update = new Button("Update");
		button_Update.addActionListener(e -> onButtonClickedUpdate(e));
		button_Update.setForeground(Color.WHITE);
		button_Update.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button_Update.setBackground(new Color(153, 0, 153));
		button_Update.setBounds(294, 732, 82, 21);
		contentPane.add(button_Update);

		rdbtnActive = new JRadioButton("Active");
		rdbtnActive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnActive.isSelected()) {
					rdbtnInactive.setSelected(false);
				}
			}
		});
		rdbtnActive.setSelected(true);
		rdbtnActive.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		rdbtnActive.setBounds(76, 506, 124, 37);
		contentPane.add(rdbtnActive);

		rdbtnInactive = new JRadioButton("Inactive");
		rdbtnInactive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnInactive.isSelected()) {
					rdbtnActive.setSelected(false);
				}
			}
		});
		rdbtnInactive.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		rdbtnInactive.setBounds(206, 506, 124, 37);
		contentPane.add(rdbtnInactive);

		jlabel_Image = new JLabel("Image");
		jlabel_Image.setBounds(76, 548, 254, 153);
		if (item.getImage() != null) {
			InputStream in = item.getImage().getBinaryStream();
			BufferedImage image = ImageIO.read(in);
			Image myImage = image;
			Image newImage = myImage.getScaledInstance(jlabel_Image.getWidth(), jlabel_Image.getHeight(),
					Image.SCALE_SMOOTH);
			ImageIcon img = new ImageIcon(newImage);
			jlabel_Image.setIcon(img);
			contentPane.add(jlabel_Image);
		}

		button_AddImage = new Button("Add image.");
		button_AddImage.addActionListener(e -> onButtonAddImage(e));
		button_AddImage.setForeground(Color.WHITE);
		button_AddImage.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button_AddImage.setBackground(new Color(153, 0, 153));
		button_AddImage.setBounds(294, 705, 82, 21);
		contentPane.add(button_AddImage);
	}

	private void onButtonClickedUpdate(ActionEvent e) {

		Blob blob = null;
		try {
			if (file != null) {
				blob = convertFileContentToBlob(file.getAbsolutePath());
			} else if (item.getImage() != null) {
				blob = item.getImage();
			} else {
				blob = null;
			}

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
		try {
			if (items.updateItem(userId, item.getId(), name.getText(), Double.parseDouble(price.getText()),
					description.getText(), category.getSelectedItem(), rdbtnActive.isSelected(), blob)) {
				contentPane.setVisible(false);
				dispose();
			}

		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		}
	}

	private void onButtonAddImage(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(button_AddImage);
		file = chooser.getSelectedFile();
		String name = file.getAbsolutePath();
		ImageIcon imgThisImg = new ImageIcon(name);
		jlabel_Image.setIcon(imgThisImg);
	}

	private void onButtonClose(ActionEvent e) {
		contentPane.setVisible(false);
		dispose();
	}

	private Choice addCategories(List<Category> list, Choice ch) {
		for (Category temp : list) {
			ch.addItem(temp.getName());
		}
		return ch;
	}

	public static Blob convertFileContentToBlob(String filePathStr) throws IOException, SerialException, SQLException {
		// get path object pointing to file
		Path filePath = Paths.get(filePathStr);
		// get byte array with file contents
		byte[] fileContent = Files.readAllBytes(filePath);
		Blob blob = null;
		blob = new SerialBlob(fileContent);
		return blob;
	}
}
