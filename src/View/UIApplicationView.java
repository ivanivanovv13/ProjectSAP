package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UIApplicationView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String databaseUrl = args[0];
					String databaseUser = args[1];
					String databasePassword = args[2];
					LogInView frame = new LogInView(databaseUrl, databaseUser, databasePassword);
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JPanel(), e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}
