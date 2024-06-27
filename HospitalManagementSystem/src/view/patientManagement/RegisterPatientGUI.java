package view.patientManagement;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import controller.MakeHttpRequest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.net.http.HttpResponse;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterPatientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTxtField;
	private JTextField dobTxtFld;
	private JTextField phoneTxtFld;
	private JTextField emailTxtFld;
	private MakeHttpRequest req= new MakeHttpRequest();
	private String accessToken;

	/**
	 * Create the frame.
	 */
	public RegisterPatientGUI(String accessToken) {
		this.accessToken = accessToken;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLbl = new JLabel("Register Patient");
		titleLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		titleLbl.setBounds(157, 6, 135, 20);
		contentPane.add(titleLbl);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setBounds(52, 43, 61, 16);
		contentPane.add(nameLabel);
		
		JLabel dobLabel = new JLabel("DOB");
		dobLabel.setBounds(52, 71, 61, 16);
		contentPane.add(dobLabel);
		
		JLabel phoneLabel = new JLabel("Phone");
		phoneLabel.setBounds(52, 99, 61, 16);
		contentPane.add(phoneLabel);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(52, 132, 61, 16);
		contentPane.add(emailLabel);
		
		nameTxtField = new JTextField();
		nameTxtField.setBounds(157, 38, 130, 26);
		contentPane.add(nameTxtField);
		nameTxtField.setColumns(10);
		
		dobTxtFld = new JTextField();
		dobTxtFld.setColumns(10);
		dobTxtFld.setBounds(157, 66, 130, 26);
		contentPane.add(dobTxtFld);
		
		phoneTxtFld = new JTextField();
		phoneTxtFld.setColumns(10);
		phoneTxtFld.setBounds(157, 94, 130, 26);
		contentPane.add(phoneTxtFld);
		
		emailTxtFld = new JTextField();
		emailTxtFld.setColumns(10);
		emailTxtFld.setBounds(157, 127, 130, 26);
		contentPane.add(emailTxtFld);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setBounds(52, 173, 61, 16);
		contentPane.add(addressLabel);
		
		JTextArea addressTxtArea = new JTextArea();
		addressTxtArea.setBounds(157, 165, 130, 44);
		contentPane.add(addressTxtArea);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSONObject jsonParams = new JSONObject();
				jsonParams.put("address", addressTxtArea.getText());
		        jsonParams.put("dob", dobTxtFld.getText());
		        jsonParams.put("email", emailTxtFld.getText());
		        jsonParams.put("name", nameTxtField.getText());
		        jsonParams.put("phone", phoneTxtFld.getText());
			      
		        if(nameTxtField.getText().isEmpty()||dobTxtFld.getText().isEmpty()||addressTxtArea.getText().isEmpty()||emailTxtFld.getText().isEmpty()||phoneTxtFld.getText().isEmpty())
				 {
					 JOptionPane.showMessageDialog(null,"Please fill in all required field!");
				 }
		        else
		        {
		        	try {
						 HttpResponse<String> response = req.makeHttpRequest("http://localhost:5000/patient/add", "POST", jsonParams, accessToken);
						 
						 if(response.statusCode()==HttpStatus.SC_CREATED)
							 JOptionPane.showMessageDialog(null,"New patient is added successfully!");
						 else
							 JOptionPane.showMessageDialog(null,"Something went wrong...");
				
					 }catch(Exception e1)
					 {
						 e1.printStackTrace();
					 }
		        }
				
               
			}
		});
		registerButton.setBounds(316, 106, 117, 29);
		contentPane.add(registerButton);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				PatientApplicationGUI appGUI = new PatientApplicationGUI(accessToken);
				appGUI.setVisible(true);
			}
		});
		btnBack.setBounds(19, 4, 29, 29);
		contentPane.add(btnBack);
		ImageIcon backImage = createResizedIcon("/resources/BackButton.png", 25, 25);
		btnBack.setIcon(backImage);
	}
	
/* private ImageIcon createResizedIcon(String imagePath, int width, int height) {
	    
	    ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
	    Image originalImage = originalIcon.getImage();
	    Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    return new ImageIcon(resizedImage);
	} */
	private ImageIcon createResizedIcon(String imagePath, int width, int height) {
        URL resourceUrl = getClass().getClassLoader().getResource(imagePath);
        if (resourceUrl != null) {
            ImageIcon icon = new ImageIcon(resourceUrl);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } else {
            System.err.println("Resource not found: " + imagePath);
            return new ImageIcon(); // Return an empty icon or a default one if preferred
        }
    }
}
