package guiPages;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import process.mainProcess;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.MatteBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class mainPage extends JFrame {

	public JPanel contentPane;
	public String filePath = "";
	@SuppressWarnings("rawtypes")
	SwingWorker worker;
	public static mainPage frame;
	private JTable table;
	List<Double> getPoints;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new mainPage("");
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
	public mainPage(String username) {
		setTitle("ANALYZE");
		mainProcess myMain = new mainProcess();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDate = new JLabel("DATE");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDate.setBounds(10, 11, 207, 25);
		contentPane.add(lblDate);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		lblDate.setText(formatter.format(date));

		JLabel lblUsername = new JLabel("");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.ITALIC, 17));
		lblUsername.setBounds(536, 1, 160, 31);
		contentPane.add(lblUsername);
		lblUsername.setText(username);

		JLabel lblOutput = new JLabel(
				"<html>\r\n-><br>\r\nO<br>\r\nU<br>\r\nT<br>\r\nP<br>\r\nU<br>\r\nT<br>\r\n-><br></html>");
		lblOutput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutput.setBounds(304, 67, 66, 272);
		contentPane.add(lblOutput);

		JComboBox<String> comboStockName = new JComboBox<String>();
		comboStockName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// stock name secildi
			}
		});
		comboStockName.setMaximumRowCount(100);
		comboStockName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		comboStockName.setBounds(10, 108, 139, 25);
		contentPane.add(comboStockName);

		JComboBox<String> comboAlgoSelect = new JComboBox<String>();
		comboAlgoSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// algo ismi se�ildi
			}
		});
		comboAlgoSelect.setFont(new Font("Tahoma", Font.PLAIN, 17));
		comboAlgoSelect.setBounds(159, 108, 139, 25);
		contentPane.add(comboAlgoSelect);

		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnExit.setBounds(576, 33, 89, 23);
		contentPane.add(btnExit);

		// List<String> myStocks =
		// textFiles("C:\\Users\\ahmet\\OneDrive\\Masa�st�\\piyasa\\analiz\\");
		List<String> myStocks = textFiles("C:\\Users\\user\\Desktop\\analiz\\");

		for (int i = 0; i < myStocks.size(); i++) {
			comboStockName.addItem(myStocks.get(i));
		}
		comboAlgoSelect.addItem("Kaufmann");
		comboAlgoSelect.addItem("Traditional SMA. *****NOT AVAILABLE");

		JTextArea outputResult = new JTextArea();
		JScrollPane spEditor = new JScrollPane(outputResult);
		spEditor.setBounds(368, 67, 326, 363);
		contentPane.add(spEditor);

		JLabel lblStockName = new JLabel("");
		lblStockName.setHorizontalAlignment(SwingConstants.CENTER);
		lblStockName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblStockName.setBounds(10, 317, 106, 59);
		contentPane.add(lblStockName);

		table = new JTable();
		table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, },
				new String[] { "Take Profit", "Stop Loss", "Confirmation" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.setBounds(126, 342, 232, 16);
		contentPane.add(table);

		JLabel lblAfterAnalyze = new JLabel(
				"<html>You are expected to do your analyze.<br> \r\nThis indicator may not be perfect with selected stock.<br> \r\nPlease be awere of it and try to find the best indicator for each stock.</html>");
		lblAfterAnalyze.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAfterAnalyze.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAfterAnalyze.setBounds(10, 199, 315, 85);
		contentPane.add(lblAfterAnalyze);
		lblAfterAnalyze.setVisible(false);

		JButton btnAnalyze = new JButton("ANALYZE");
		btnAnalyze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblAfterAnalyze.setVisible(true);
				try {
					filePath = comboStockName.getSelectedItem().toString();
					System.out.println("C:\\Users\\user\\Desktop\\analiz\\" + filePath);
					List<Double> getPoints = myMain.getParameters(filePath);
					lblStockName.setText(filePath);
					table.setValueAt(getPoints.get(0).toString(), 0, 0);
					table.setValueAt(getPoints.get(1).toString(), 0, 1);
					table.setValueAt(getPoints.get(2).toString(), 0, 2);
					getOutput(outputResult,
							myMain.trade(getPoints.get(0), getPoints.get(1), getPoints.get(2), filePath));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAnalyze.setFont(new Font("Tahoma", Font.PLAIN, 29));
		btnAnalyze.setBounds(10, 387, 288, 43);
		contentPane.add(btnAnalyze);

		JLabel lblNewLabel = new JLabel("Select to analyze");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(10, 72, 139, 25);
		contentPane.add(lblNewLabel);

		JLabel lblSelectIndicator = new JLabel("Select indicator");
		lblSelectIndicator.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectIndicator.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSelectIndicator.setBounds(159, 72, 139, 25);
		contentPane.add(lblSelectIndicator);

		JLabel lblNewLabel_1 = new JLabel("Take Profit");
		lblNewLabel_1.setBounds(126, 317, 75, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Stop Loss");
		lblNewLabel_1_1.setBounds(204, 317, 75, 14);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Confirmation");
		lblNewLabel_1_2.setBounds(275, 317, 75, 14);
		contentPane.add(lblNewLabel_1_2);

	}

	List<String> textFiles(String directory) {
		List<String> textFiles = new ArrayList<String>();
		File dir = new File(directory);
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith((".csv"))) {
				textFiles.add(file.getName());
			}
		}
		return textFiles;
	}

	void getOutput(JTextArea tArea, String s) {
		tArea.setText("");
		if (worker != null) {
			worker.cancel(true);
		}
		worker = new SwingWorker<Object, Object>() {
			@Override
			protected Integer doInBackground()// Perform the required GUI update here.
			{
				try {
					for (int i = 0; i < s.length(); i++) {
						tArea.append(String.valueOf(s.charAt(i)));
						Thread.sleep(3);
					}
				} catch (Exception ex) {
				}
				return 0;
			}
		};
		worker.execute();// Schedules this SwingWorker for execution on a worker thread.

	}
}
