package guiPages;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class welcome extends JFrame {

	private JPanel contentPane;
	public static welcome frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new welcome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public welcome() {
		setTitle("ALGORITHMIC TRADING WITH TECHNICAL ANLAYSIS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ALGORITHMIC TRADING WITH TECHNICAL ANALYSIS");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel.setBounds(10, 11, 686, 80);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Please log in or register");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_1.setBounds(20, 102, 213, 66);
		contentPane.add(lblNewLabel_1);

		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login login = new login();
				login.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogin.setBounds(10, 179, 226, 66);
		contentPane.add(btnLogin);

		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register register = new register();
				register.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRegister.setBounds(265, 179, 226, 66);
		contentPane.add(btnRegister);
	}
}
