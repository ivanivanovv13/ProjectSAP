package View;



import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import java.sql.SQLException;
import java.awt.Button;

public class RegistrationView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String databaseUrl;
	private String databaseUser;
	private String databasePassword;
	private AccountController accounts;
	TextField email;
	TextField password;
	TextField firstName;
	Label label_LastName;
	TextField lastName;
	TextField phoneNum;

	/**
	 * Create the frame.
	 * 
	 * @throws NotEmailAddressException
	 * @throws SQLException
	 */
	@SuppressWarnings("deprecation")
	public RegistrationView(String databaseUrl, String databaseUser, String databasePassword)
			throws SQLException, NotEmailAddressException {
		this.databasePassword = databasePassword;
		this.databaseUrl = databaseUrl;
		this.databaseUser = databaseUser;
		accounts = new AccountController(databaseUrl, databaseUser, databasePassword);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Label label_Email = new Label("Email:");
		label_Email.setForeground(new Color(153, 0, 153));
		label_Email.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Email.setBounds(76, 98, 59, 21);
		contentPane.add(label_Email);

		email = new TextField();
		email.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		email.setBounds(76, 125, 242, 28);
		contentPane.add(email);

		Label label_Password = new Label("Password:");
		label_Password.setForeground(new Color(153, 0, 153));
		label_Password.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Password.setBounds(76, 159, 98, 21);
		contentPane.add(label_Password);

		password = new TextField();
		password.setEchoCharacter('*');
		password.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		password.setBounds(76, 186, 242, 28);
		contentPane.add(password);

		Label label_Register = new Label("Register");
		label_Register.setAlignment(Label.CENTER);
		label_Register.setForeground(new Color(153, 0, 153));
		label_Register.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 24));
		label_Register.setBounds(87, 10, 195, 44);
		contentPane.add(label_Register);

		Label label_FirstName = new Label("First Name\r\n");
		label_FirstName.setForeground(new Color(153, 0, 153));
		label_FirstName.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_FirstName.setBounds(76, 220, 98, 21);
		contentPane.add(label_FirstName);

		firstName = new TextField();
		firstName.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		firstName.setBounds(76, 253, 242, 28);
		contentPane.add(firstName);

		Label label_LastName = new Label("Last Name\r\n");
		label_LastName.setForeground(new Color(153, 0, 153));
		label_LastName.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_LastName.setBounds(76, 287, 98, 21);
		contentPane.add(label_LastName);

		lastName = new TextField();
		lastName.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		lastName.setBounds(76, 314, 242, 28);
		contentPane.add(lastName);

		Label label_PhoneNumber = new Label("Phone number\r\n");
		label_PhoneNumber.setForeground(new Color(153, 0, 153));
		label_PhoneNumber.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_PhoneNumber.setBounds(76, 361, 98, 21);
		contentPane.add(label_PhoneNumber);

		phoneNum = new TextField();
		phoneNum.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		phoneNum.setBounds(76, 388, 242, 28);
		contentPane.add(phoneNum);

		Button button_SignUp = new Button("Sign up");
		button_SignUp.addActionListener(e -> onButtonClickedSignUp(e));
		button_SignUp.setForeground(new Color(255, 255, 255));
		button_SignUp.setFont(new Font("Berlin Sans FB", Font.BOLD | Font.ITALIC, 14));
		button_SignUp.setBackground(new Color(153, 0, 153));
		button_SignUp.setBounds(130, 434, 111, 32);
		contentPane.add(button_SignUp);

		Button button_SignIn = new Button("Already a member? Go to sign in.");
		button_SignIn.addActionListener(e -> onButtonClickedSignIn(e));
		button_SignIn.setForeground(new Color(153, 0, 153));
		button_SignIn.setFont(new Font("Berlin Sans FB", Font.BOLD | Font.ITALIC, 14));
		button_SignIn.setBackground(Color.WHITE);
		button_SignIn.setBounds(10, 499, 368, 28);
		contentPane.add(button_SignIn);
	}

	private void onButtonClickedSignIn(ActionEvent e) {
		contentPane.setVisible(false);
		dispose();
		try {
			LogInView log = new LogInView(databaseUrl, databaseUser, databasePassword);
			log.setVisible(true);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		} catch (NotEmailAddressException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		}
	}

	private void onButtonClickedSignUp(ActionEvent e) {
		User obj = null;
		try {
			obj = new User(firstName.getText(), lastName.getText(), email.getText(), phoneNum.getText(),
					password.getText());
		} catch (NotEmailAddressException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		}
		try {
			if (accounts.addAccount(obj)) {
				System.out.println("You register successfuly!");
				contentPane.setVisible(false);
				dispose();
				try {
					LogInView log = new LogInView(databaseUrl, databaseUser, databasePassword);
					log.setVisible(true);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
					e1.printStackTrace();
				} catch (NotEmailAddressException e1) {
					JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
					e1.printStackTrace();
				}
			}
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
