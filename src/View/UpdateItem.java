package View;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
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

public class UpdateItem extends JFrame {

	private JPanel contentPane;
	JRadioButton rdbtnInactive;
	JRadioButton rdbtnNewRadioButton;
	File file;

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public UpdateItem(String databaseUrl, String databaseUser, String databasePassword, String userId, Item item)
			throws SQLException, NotEmailAddressException, IOException {
		ItemsController items = new ItemsController(databaseUrl, databaseUser, databasePassword);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Label label = new Label("Name");
		label.setForeground(new Color(153, 0, 153));
		label.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label.setBounds(76, 78, 59, 21);
		contentPane.add(label);

		TextField name = new TextField();
		name.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		name.setBounds(76, 105, 242, 28);
		name.setText(item.getName());
		contentPane.add(name);

		Label label_1 = new Label("Price:");
		label_1.setForeground(new Color(153, 0, 153));
		label_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_1.setBounds(76, 139, 98, 21);
		contentPane.add(label_1);

		TextField price = new TextField();
		price.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		price.setBounds(76, 166, 242, 28);
		price.setText(String.valueOf(item.getPrice()));
		contentPane.add(price);

		Label label_2 = new Label("Update item");
		label_2.setAlignment(Label.CENTER);
		label_2.setForeground(new Color(153, 0, 153));
		label_2.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 24));
		label_2.setBounds(87, 10, 231, 44);
		contentPane.add(label_2);

		Label label_1_1 = new Label("Category\r\n");
		label_1_1.setForeground(new Color(153, 0, 153));
		label_1_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_1_1.setBounds(76, 200, 98, 21);
		contentPane.add(label_1_1);

		Label label_1_1_1 = new Label("Description");
		label_1_1_1.setForeground(new Color(153, 0, 153));
		label_1_1_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_1_1_1.setBounds(76, 251, 98, 21);
		contentPane.add(label_1_1_1);

		Choice category = new Choice();
		category.setBounds(76, 227, 242, 28);
		addCategories(items.category, category);
		category.select(item.getCategory());

		contentPane.add(category);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(76, 287, 254, 213);
		contentPane.add(panel);
		panel.setLayout(null);

		JTextArea desc = new JTextArea();
		desc.setBounds(6, 15, 242, 192);
		panel.add(desc);
		desc.setBackground(UIManager.getColor("CheckBox.light"));
		desc.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		desc.setText(item.getDescription());

		Button close = new Button("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
			}
		});
		close.setBackground(new Color(153, 0, 153));
		close.setForeground(Color.WHITE);
		close.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		close.setBounds(10, 732, 82, 21);
		contentPane.add(close);

		Button close_1 = new Button("Update");
		close_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Blob blob = null;
					try {
						blob = convertFileContentToBlob(file.getAbsolutePath());
					} catch (SerialException e2) {
						e2.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					if (items.updateItem(userId, item.getId(), name.getText(), Double.parseDouble(price.getText()), desc.getText(),
							category.getSelectedItem(),rdbtnNewRadioButton.isSelected(),blob)) {
						contentPane.setVisible(false);
						dispose();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		close_1.setForeground(Color.WHITE);
		close_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		close_1.setBackground(new Color(153, 0, 153));
		close_1.setBounds(294, 732, 82, 21);
		contentPane.add(close_1);

		rdbtnNewRadioButton = new JRadioButton("Active");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNewRadioButton.isSelected()) {
					rdbtnInactive.setSelected(false);
				}
			}
		});
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		rdbtnNewRadioButton.setBounds(76, 506, 124, 37);
		contentPane.add(rdbtnNewRadioButton);

		rdbtnInactive = new JRadioButton("Inactive");
		rdbtnInactive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnInactive.isSelected()) {
					rdbtnNewRadioButton.setSelected(false);
				}
			}
		});
		rdbtnInactive.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		rdbtnInactive.setBounds(206, 506, 124, 37);
		contentPane.add(rdbtnInactive);
		
		JLabel lblNewLabel = new JLabel("Image");
		lblNewLabel.setBounds(76, 548, 254, 153);
		if(item.getImage()!=null) {
		InputStream in = item.getImage().getBinaryStream();  
		BufferedImage image = ImageIO.read(in);
		Image myImage = image;
		Image newImage= myImage.getScaledInstance(lblNewLabel.getWidth(),lblNewLabel.getHeight(),Image.SCALE_SMOOTH);
		ImageIcon img = new ImageIcon(newImage);
		lblNewLabel.setIcon(img);
		contentPane.add(lblNewLabel);
		}
		
		Button button = new Button("Add image.");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(button);
				 file = chooser.getSelectedFile();
				String name = file.getAbsolutePath();
				ImageIcon imgThisImg = new ImageIcon(name);
				lblNewLabel.setIcon(imgThisImg);
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		button.setBackground(new Color(153, 0, 153));
		button.setBounds(294, 705, 82, 21);
		contentPane.add(button);
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
