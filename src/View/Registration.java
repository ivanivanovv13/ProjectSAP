package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.AccountController;
import Model.NotEmailAddressException;
import Model.User;

import java.awt.Label;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Button;

public class Registration extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 * 
	 * @throws NotEmailAddressException
	 * @throws SQLException
	 */
	public Registration(String databaseUrl, String databaseUser, String databasePassword)
			throws SQLException, NotEmailAddressException {
		AccountController accounts = new AccountController(databaseUrl, databaseUser, databasePassword);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Label label = new Label("Email:");
		label.setForeground(new Color(153, 0, 153));
		label.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label.setBounds(76, 98, 59, 21);
		contentPane.add(label);

		TextField email = new TextField();
		email.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		email.setBounds(76, 125, 242, 28);
		contentPane.add(email);

		Label label_1 = new Label("Password:");
		label_1.setForeground(new Color(153, 0, 153));
		label_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_1.setBounds(76, 159, 98, 21);
		contentPane.add(label_1);

		TextField password = new TextField();
		password.setEchoCharacter('*');
		password.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		password.setBounds(76, 186, 242, 28);
		contentPane.add(password);

		Label label_2 = new Label("Register");
		label_2.setAlignment(Label.CENTER);
		label_2.setForeground(new Color(153, 0, 153));
		label_2.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 24));
		label_2.setBounds(87, 10, 195, 44);
		contentPane.add(label_2);

		Label label_1_1 = new Label("First Name\r\n");
		label_1_1.setForeground(new Color(153, 0, 153));
		label_1_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_1_1.setBounds(76, 220, 98, 21);
		contentPane.add(label_1_1);

		TextField firstName = new TextField();
		firstName.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		firstName.setBounds(76, 253, 242, 28);
		contentPane.add(firstName);

		Label label_1_1_1 = new Label("Last Name\r\n");
		label_1_1_1.setForeground(new Color(153, 0, 153));
		label_1_1_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_1_1_1.setBounds(76, 287, 98, 21);
		contentPane.add(label_1_1_1);

		TextField lastName = new TextField();
		lastName.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		lastName.setBounds(76, 314, 242, 28);
		contentPane.add(lastName);

		Label label_1_1_1_1 = new Label("Phone number\r\n");
		label_1_1_1_1.setForeground(new Color(153, 0, 153));
		label_1_1_1_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_1_1_1_1.setBounds(76, 361, 98, 21);
		contentPane.add(label_1_1_1_1);

		TextField phoneNum = new TextField();
		phoneNum.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		phoneNum.setBounds(76, 388, 242, 28);
		contentPane.add(phoneNum);

		Button button = new Button("Sign up");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User obj = null;
				try {
					obj = new User(firstName.getText(), lastName.getText(), email.getText(), phoneNum.getText(),
							password.getText());
				} catch (NotEmailAddressException e1) {
					e1.printStackTrace();
				}
				try {
					if (accounts.addAccount(obj)) {
						System.out.println("You register successfuly!");
						contentPane.setVisible(false);
						dispose();
						try {
							LogIn log = new LogIn(databaseUrl, databaseUser, databasePassword);
							log.setVisible(true);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NotEmailAddressException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (NullPointerException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setForeground(new Color(255, 255, 255));
		button.setFont(new Font("Berlin Sans FB", Font.BOLD | Font.ITALIC, 14));
		button.setBackground(new Color(153, 0, 153));
		button.setBounds(130, 434, 111, 32);
		contentPane.add(button);

		Button button_1 = new Button("Already a member? Go to sign in.");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				try {
					LogIn log = new LogIn(databaseUrl, databaseUser, databasePassword);
					log.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotEmailAddressException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_1.setForeground(new Color(153, 0, 153));
		button_1.setFont(new Font("Berlin Sans FB", Font.BOLD | Font.ITALIC, 14));
		button_1.setBackground(Color.WHITE);
		button_1.setBounds(10, 499, 368, 28);
		contentPane.add(button_1);
	}
}
