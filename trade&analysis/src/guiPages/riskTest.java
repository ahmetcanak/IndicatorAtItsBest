package guiPages;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class riskTest extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JFrame frame;

	public riskTest(String username) {

		frame = new JFrame("Risk Test");
		frame.setSize(1500, 1000);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setSize(1000, 1000);
		panel.setLayout(null);

		Question[] questions = new Question[10];
		Question q1 = new Question();
		q1.Question = "1) What is your Age?";
		q1.A = "16";
		q1.B = "18+ *****";
		q1.C = "17";
		q1.D = "12";
		questions[0] = q1;

		Question q2 = new Question();
		q2.Question = "2) What is your risk and return preference?";
		q2.A = "Avoids risk as much as possible, prefers safe investment instruments, produces a small but regular return"
				+ "I prefer to provide. (May my principal be preserved)";
		q2.B = "With the expectation of returns above inflation, I can invest in low-risk products on the investment. (I can afford to lose a very small amount of the principal)*****";
		q2.C = "In general, preferring medium risk products, increasing the total return in the long term.\r\n"
				+ "I can reasonably invest in risky products for the purpose of (To lose some of the principal\r\n"
				+ "I can afford)";
		q2.D = "I can invest in high-risk products with the expectation of high returns\r\n" + "I can afford.)";
		questions[1] = q2;

		Question q3 = new Question();
		q3.Question = "3) You may lose money while using this algorithim. Are you aware of that?";
		q3.A = "Yes, I am.*****";
		q3.B = "No, I will be rich with this algorithm.";
		questions[2] = q3;

		Question q4 = new Question();
		q4.Question = "4) Algorithm is not miracle. Do you know this?";
		q4.A = "I don't think so.";
		q4.B = "Yes, ofcourse. *****";
		questions[3] = q4;

		Question q5 = new Question();
		q5.Question = "5) There are lots of financial crises in financial markets. Is your mental health can handle losing money?";
		q5.A = "No.";
		q5.B = "Yes, I am aware of those crises. *****";
		questions[4] = q5;

		JLabel Question = new JLabel(questions[0].Question);
		Question.setBounds(50, 0, 1500, 50);
		panel.add(Question);
		Question.setFont(new Font(Question.getFont().getName(), Font.PLAIN, 25));
		Question.setVisible(true);

		JButton submitButton = new JButton();
		submitButton.setBounds(50, 250, 150, 50);
		submitButton.setText("Submit");
		panel.add(submitButton);
		submitButton.setVisible(true);

		JRadioButton OptionA = new JRadioButton(questions[0].A);
		OptionA.setBounds(50, 50, 1111, 57);
		panel.add(OptionA);
		OptionA.setVisible(true);

		JRadioButton OptionB = new JRadioButton(questions[0].B);
		OptionB.setBounds(50, 100, 1111, 50);
		panel.add(OptionB);
		OptionB.setVisible(true);

		JRadioButton OptionC = new JRadioButton(questions[0].C);
		OptionC.setBounds(50, 150, 1122, 50);
		panel.add(OptionC);
		OptionC.setVisible(true);

		JRadioButton OptionD = new JRadioButton(questions[0].D);
		OptionD.setBounds(50, 200, 1122, 50);
		panel.add(OptionD);
		OptionD.setVisible(true);

		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(OptionA);
		radioGroup.add(OptionB);
		radioGroup.add(OptionC);
		radioGroup.add(OptionD);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (submitButton.getText().equals("Submit") || submitButton.getText().equals("Finish test")) {
					if (OptionA.isSelected()) {
						getAnswers.append("a");
					} else if (OptionB.isSelected()) {
						getAnswers.append("b");
					} else if (OptionC.isSelected()) {
						getAnswers.append("c");
					} else if (OptionD.isSelected()) {
						getAnswers.append("d");
					}
				}
				count++;
				System.out.println(count);
				System.out.println(getAnswers.toString());
				if (count == 4) {
					submitButton.setText("Finish test");
				}
				if (count < 5) {
					Question q = questions[count];
					Question.setText(q.Question);
					setAnswers(q.A, q.B, q.C, q.D, OptionA, OptionB, OptionC, OptionD);
				}
				if (count == 5) {
					try {
						@SuppressWarnings("unused")
						testResult newframe = new testResult(isSuccess(username), username);
						// newframe.setVisible(true);
						frame.setVisible(false);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// test sonu� ekran�na yans�taca��m.
				}
			}
		});

	}

	public static class Question {

		String Question;
		String userAns;
		String realAns;
		public String B;
		public String A;
		public String C;
		public String D;
	}

	static boolean isSuccess(String username) throws SQLException {

		if (answers.equals(getAnswers.toString())) {
			Connection con;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschema", "root", "123456");
			String sql = "update myschema.user_db (canTrade)" + " value (1)";
			sql = "UPDATE `myschema`.`user_db` SET `canTrade` = '1' WHERE (`username` = '" + username + "')";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.execute();
			return true;
		}
		return false;
	}

	static void setAnswers(String A, String B, String C, String D, JRadioButton a, JRadioButton b, JRadioButton c,
			JRadioButton d) {
		List<String> answers = Arrays.asList(A, B, C, D);
		a.setText(answers.get(0));
		b.setText(answers.get(1));
		c.setText(answers.get(2));
		d.setText(answers.get(3));
	}

	static StringBuilder getAnswers = new StringBuilder();
	static String answers = "bbabb";
	static int count = 0;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					riskTest frame = new riskTest("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
