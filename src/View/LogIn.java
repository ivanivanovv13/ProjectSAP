package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.AccountController;
import Model.NotEmailAddressException;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.Label;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Canvas;
import javax.swing.JInternalFrame;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class LogIn extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String databaseUrl = args[0];
					String databaseUser = args[1];
					String databasePassword = args[2];
					LogIn frame = new LogIn(databaseUrl, databaseUser, databasePassword);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws NotEmailAddressException
	 * @throws SQLException
	 */
	public LogIn(String databaseUrl, String databaseUser, String databasePassword)
			throws SQLException, NotEmailAddressException {
		AccountController accounts = new AccountController(databaseUrl, databaseUser, databasePassword);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Label label = new Label("Email:");
		label.setForeground(new Color(153, 0, 153));
		label.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label.setBounds(74, 81, 59, 21);
		contentPane.add(label);

		TextField textField = new TextField();
		textField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		textField.setBounds(74, 118, 242, 28);
		contentPane.add(textField);

		Label label_1 = new Label("Password:");
		label_1.setForeground(new Color(153, 0, 153));
		label_1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_1.setBounds(74, 162, 98, 21);
		contentPane.add(label_1);

		TextField textField_1 = new TextField();
		textField_1.setEchoCharacter('*');
		textField_1.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		textField_1.setBounds(74, 199, 242, 28);
		contentPane.add(textField_1);

		Label label_2 = new Label("Log In");
		label_2.setFont(new Font("Bauhaus 93", Font.PLAIN, 26));
		label_2.setForeground(new Color(153, 0, 153));
		label_2.setBounds(152, 10, 98, 51);
		contentPane.add(label_2);

		Button button = new Button("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = accounts.logIn(textField.getText(), textField_1.getText());
				if (userId != null) {
					System.out.println("Logged in successfully");
					contentPane.setVisible(false);
					dispose();
					Menu menu = null;
					try {
						menu = new Menu(databaseUrl, databaseUser, databasePassword, userId);
						menu.setVisible(true);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotEmailAddressException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		button.setFont(new Font("Berlin Sans FB", Font.PLAIN, 17));
		button.setBackground(new Color(153, 0, 153));
		button.setForeground(new Color(255, 255, 255));
		button.setBounds(138, 273, 130, 41);
		contentPane.add(button);

		Button button_1 = new Button("Don't have an account?Create.");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				Registration reg = null;
				try {
					reg = new Registration(databaseUrl, databaseUser, databasePassword);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (NotEmailAddressException e1) {
					e1.getMessage();
				}
				reg.setVisible(true);
			}
		});
		button_1.setForeground(new Color(153, 0, 153));
		button_1.setFont(new Font("Berlin Sans FB", Font.BOLD | Font.ITALIC, 14));
		button_1.setBackground(Color.WHITE);
		button_1.setBounds(74, 332, 242, 21);
		contentPane.add(button_1);
	}
}
