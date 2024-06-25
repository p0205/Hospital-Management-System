package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.http.HttpResponse;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import controller.MakeHttpRequest;

public class AuthenticationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtFldUsername;
	private JTextField txtFldPassword;
	private MakeHttpRequest req; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthenticationGUI frame = new AuthenticationGUI();
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
	public AuthenticationGUI() {
		req = new MakeHttpRequest();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Username :");
		lblNewLabel_1.setBounds(75, 78, 82, 16);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password :");
		lblNewLabel_1_1.setBounds(75, 117, 82, 16);
		getContentPane().add(lblNewLabel_1_1);
		
		txtFldUsername = new JTextField();
		txtFldUsername.setBounds(169, 73, 149, 26);
		getContentPane().add(txtFldUsername);
		txtFldUsername.setColumns(10);
		
		txtFldPassword = new JTextField();
		txtFldPassword.setColumns(10);
		txtFldPassword.setBounds(169, 112, 149, 26);
		getContentPane().add(txtFldPassword);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSONObject jsonParams = new JSONObject();
				jsonParams.put("username", txtFldUsername.getText());
				jsonParams.put("password", txtFldPassword.getText());
			
				HttpResponse<String> response = req.makeHttpRequest("http://localhost:8080/auth/login","POST", jsonParams);
				if(response.statusCode() == HttpStatus.SC_OK)
				{
					dispose();
					MainApplicationGUI appGUI = new MainApplicationGUI();
					appGUI.setVisible(true);
				}else if(response.statusCode() == HttpStatus.SC_UNAUTHORIZED)
				{
					JOptionPane.showMessageDialog(null, "Incorrect username or password");
				}else
				{
					JOptionPane.showMessageDialog(null, "Internal Server Error...");
				}
			}
		});
		btnNewButton.setBounds(169, 174, 117, 29);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Hospital Management System");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(110, 6, 219, 24);
		getContentPane().add(lblNewLabel);

}
}

