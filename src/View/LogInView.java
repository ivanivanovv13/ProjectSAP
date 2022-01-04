package View;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Controller.AccountController;
import Model.NotEmailAddressException;
import java.awt.Color;
import java.awt.Label;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Button;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class LogInView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String databaseUrl;
	private String databaseUser;
	private String databasePassword;
	private AccountController accounts;
	private Label label_Email;
	private TextField textField_Email;
	private Label label_Password;
	private TextField textField_Password;
	private Label label_Tittle;

	/**
	 * Create the frame.
	 * 
	 * @throws NotEmailAddressException
	 * @throws SQLException
	 */
	@SuppressWarnings("deprecation")
	public LogInView(String databaseUrl, String databaseUser, String databasePassword)
			throws SQLException, NotEmailAddressException {
		this.databaseUrl=databaseUrl;
		this.databasePassword=databasePassword;
		this.databaseUser=databaseUser;
		accounts = new AccountController(databaseUrl, databaseUser, databasePassword);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		label_Email = new Label("Email:");
		label_Email.setForeground(new Color(153, 0, 153));
		label_Email.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Email.setBounds(74, 81, 59, 21);
		contentPane.add(label_Email);

		textField_Email = new TextField();
		textField_Email.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		textField_Email.setBounds(74, 118, 242, 28);
		contentPane.add(textField_Email);

		label_Password = new Label("Password:");
		label_Password.setForeground(new Color(153, 0, 153));
		label_Password.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		label_Password.setBounds(74, 162, 98, 21);
		contentPane.add(label_Password);

		textField_Password = new TextField();
		textField_Password.setEchoCharacter('*');
		textField_Password.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		textField_Password.setBounds(74, 199, 242, 28);
		contentPane.add(textField_Password);

		label_Tittle = new Label("Log In");
		label_Tittle.setFont(new Font("Bauhaus 93", Font.PLAIN, 26));
		label_Tittle.setForeground(new Color(153, 0, 153));
		label_Tittle.setBounds(152, 10, 98, 51);
		contentPane.add(label_Tittle);

		Button button_Submit = new Button("Submit");
		button_Submit.addActionListener(e -> onButtonClickedSubmit(e));
		button_Submit.setFont(new Font("Berlin Sans FB", Font.PLAIN, 17));
		button_Submit.setBackground(new Color(153, 0, 153));
		button_Submit.setForeground(new Color(255, 255, 255));
		button_Submit.setBounds(138, 273, 130, 41);
		contentPane.add(button_Submit);

		Button button_Register = new Button("Don't have an account?Create.");
		button_Register.addActionListener(e -> onButtonClickedCreate(e));
		button_Register.setForeground(new Color(153, 0, 153));
		button_Register.setFont(new Font("Berlin Sans FB", Font.BOLD | Font.ITALIC, 14));
		button_Register.setBackground(Color.WHITE);
		button_Register.setBounds(74, 332, 242, 21);
		contentPane.add(button_Register);
	}

	private void onButtonClickedCreate(ActionEvent e) {
		contentPane.setVisible(false);
		dispose();
		RegistrationView reg = null;
		try {
			reg = new RegistrationView(databaseUrl, databaseUser, databasePassword);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.printStackTrace();
		} catch (NotEmailAddressException e1) {
			JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
			e1.getMessage();
		}
		reg.setVisible(true);
	}

	private void onButtonClickedSubmit(ActionEvent e) {
		String userId = accounts.logIn(textField_Email.getText(), textField_Password.getText());
		if (userId != null) {
			contentPane.setVisible(false);
			dispose();
			MenuView menu = null;
			try {
				menu = new MenuView(databaseUrl, databaseUser, databasePassword, userId);
				menu.setVisible(true);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
				e1.printStackTrace();
			} catch (NotEmailAddressException e1) {
				JOptionPane.showMessageDialog(new JPanel(), e1.getMessage());
				e1.printStackTrace();
			}
		}
	}
}
