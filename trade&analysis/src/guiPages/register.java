package guiPages;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class register extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField getUsername;
	private JTextField getPassword1;
	private JTextField getPassword2;
	private JButton btnRegister;
	public static register frame;
	private JLabel lblError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new register();
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
	public register() {
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		getUsername = new JTextField();
		getUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		getUsername.setToolTipText("");
		getUsername.setBounds(232, 48, 207, 42);
		contentPane.add(getUsername);
		getUsername.setColumns(10);

		getPassword1 = new JPasswordField();
		getPassword1.setToolTipText("");
		getPassword1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		getPassword1.setColumns(10);
		getPassword1.setBounds(232, 105, 207, 42);
		contentPane.add(getPassword1);

		getPassword2 = new JPasswordField();
		getPassword2.setToolTipText("");
		getPassword2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		getPassword2.setColumns(10);
		getPassword2.setBounds(232, 165, 207, 42);
		contentPane.add(getPassword2);

		btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con;
				try {
					if(getUsername.getText().equals("") || getPassword1.getText().equals("") || getPassword2.getText().equals("")) {
						lblError.setText("Please enter values.");
					}
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschema", "root", "123456");
					String sql = "Select * from user_db where username='" + getUsername.getText() + "'";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					if (rs.next()) {
						lblError.setText("USERNAME ALREADY TAKEN");
					}

					else if (getPassword1.getText().equals(getPassword2.getText()) && !getPassword1.getText().equals("")) {

						sql = "insert into myschema.user_db (username, password)" + " values (?, ?)";
						PreparedStatement pstmt = con.prepareStatement(sql);
						pstmt.setString(1, getUsername.getText());
						pstmt.setString(2, getPassword1.getText());
						pstmt.execute();
						con.close();
						setVisible(false);
						riskTest riskTest = new riskTest(getUsername.getText());
						riskTest.frame.setVisible(true);
					}
				} catch (Exception e1) {
					System.out.println(e1);
					// TODO Auto-generated catch block
					e1.printStackTrace();

					// password match, successfully registered.
				}
				// username must be unique
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRegister.setBounds(232, 230, 207, 31);
		contentPane.add(btnRegister);

		JLabel lblEnterUsername = new JLabel("Enter username:");
		lblEnterUsername.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblEnterUsername.setBounds(10, 48, 224, 42);
		contentPane.add(lblEnterUsername);

		JLabel lblEnterPassword1 = new JLabel("Enter password:");
		lblEnterPassword1.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblEnterPassword1.setBounds(10, 105, 224, 42);
		contentPane.add(lblEnterPassword1);

		JLabel lblEnterPassword2 = new JLabel("Match Password:");
		lblEnterPassword2.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblEnterPassword2.setBounds(10, 165, 224, 42);
		contentPane.add(lblEnterPassword2);

		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(10, 230, 212, 31);
		contentPane.add(lblError);

	}
}
