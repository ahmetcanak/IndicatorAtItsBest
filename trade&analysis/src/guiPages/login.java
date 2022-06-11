package guiPages;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.sql.*;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class login extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField getUsername;
	private JTextPane password;
	private JPasswordField getPassword;
	public static login frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Login");
		setBounds(100, 100, 720, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		getUsername = new JTextField();
		getUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		getUsername.setToolTipText("");
		getUsername.setBounds(190, 70, 240, 41);
		getContentPane().add(getUsername);
		getUsername.setColumns(10);
		
		JTextPane username = new JTextPane();
		username.setEditable(false);
		username.setFont(new Font("Tahoma", Font.PLAIN, 29));
		username.setText("Username:");
		username.setBounds(42, 70, 148, 41);
		getContentPane().add(username);
		
		password = new JTextPane();
		password.setText("Password:");
		password.setFont(new Font("Tahoma", Font.PLAIN, 29));
		password.setEditable(false);
		password.setBounds(42, 137, 148, 41);
		getContentPane().add(password);
		
		getPassword = new JPasswordField();
		getPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		getPassword.setBounds(190, 137, 240, 41);
		getContentPane().add(getPassword);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschema","root","123456");
					Statement stmt = con.createStatement();
					@SuppressWarnings("deprecation")
					String sql = "Select * from user_db where username='"+getUsername.getText()+"' and password='"+getPassword.getText()+"'";
					ResultSet rs = stmt.executeQuery(sql);
					if(rs.next()) {
						System.out.println("login success");
						rs = stmt.executeQuery("Select * from user_db where username='"+getUsername.getText()+"' and canTrade=0");
						if(rs.next()) {
							System.out.println("risk testine yönlendirildi");
							setVisible(false);
							riskTest riskTest = new riskTest(getUsername.getText());
							riskTest.frame.setVisible(true);
							//kullanıcı risk testine girecek
						}
						else {
							System.out.println("anasayfaya yönlendirildi");
							setVisible(false);
							mainPage mainPage = new mainPage(getUsername.getText());
							mainPage.setVisible(true);
							//kullanıcı doğrudan anasayfaya aktarılacak
						}
					}
					else {
						System.out.println("login failed.");
					}
					con.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//getPassword().getText();
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogin.setBounds(190, 202, 240, 33);
		getContentPane().add(btnLogin);
	}
}
