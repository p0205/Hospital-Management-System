package view.patientManagement;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import controller.MakeHttpRequest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewPatientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	
	private JTextField idTxtField;
	
	private JLabel lblName;
	private JTextField nameTxtField;
	private JLabel lblDob;
	private JTextField dobTxtFld;
	private JLabel lblPhone ;
	private JTextField phoneTxtFld;
	private JLabel lblEmail ;
	private JTextField emailTxtFld;
	private JLabel lblAddress ;
	private JTextArea addressTxtArea ;
	
	private JButton searchBtn ;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnBack;
	private boolean editMode;
	private MakeHttpRequest req; 
	private String patientId;
	private String accessToken;

	/**
	 * Create the frame.
	 */
	public ViewPatientGUI(String accessToken) {
		this.accessToken = accessToken;
		editMode = false;
		req  = new MakeHttpRequest();
		
		initialize();
		
	}
	
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel viewLabel = new JLabel("Patient Details");
		viewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		viewLabel.setBounds(176, 4, 103, 16);
		contentPane.add(viewLabel);
		
		JLabel lblNewLabel = new JLabel("Enter Patient ID : ");
		lblNewLabel.setBounds(60, 45, 119, 16);
		contentPane.add(lblNewLabel);
		
		idTxtField = new JTextField();
		idTxtField.setBounds(212, 40, 130, 26);
		contentPane.add(idTxtField);
		idTxtField.setColumns(10);
		
		lblName = new JLabel("Name");
		lblName.setBounds(60, 93, 119, 16);
		contentPane.add(lblName);
		
		lblDob = new JLabel("DOB");
		lblDob.setBounds(60, 121, 119, 16);
		contentPane.add(lblDob);
		
		 lblPhone = new JLabel("Phone");
		lblPhone.setBounds(60, 149, 119, 16);
		contentPane.add(lblPhone);
		
		 lblEmail = new JLabel("Email");
		lblEmail.setBounds(60, 179, 119, 16);
		contentPane.add(lblEmail);
		
		 lblAddress = new JLabel("Address");
		lblAddress.setBounds(60, 207, 119, 16);
		contentPane.add(lblAddress);
		
		nameTxtField = new JTextField();
		nameTxtField.setEditable(false);
		nameTxtField.setColumns(10);
		nameTxtField.setBounds(135, 88, 167, 26);
		contentPane.add(nameTxtField);
		
		dobTxtFld = new JTextField();
		dobTxtFld.setEditable(false);
		dobTxtFld.setColumns(10);
		dobTxtFld.setBounds(135, 116, 167, 26);
		contentPane.add(dobTxtFld);
		
		phoneTxtFld = new JTextField();
		phoneTxtFld.setEditable(false);
		phoneTxtFld.setColumns(10);
		phoneTxtFld.setBounds(135, 144, 167, 26);
		contentPane.add(phoneTxtFld);
		
		emailTxtFld = new JTextField();
		emailTxtFld.setEditable(false);
		emailTxtFld.setColumns(10);
		emailTxtFld.setBounds(135, 174, 167, 26);
		contentPane.add(emailTxtFld);
		
		addressTxtArea = new JTextArea();
		addressTxtArea.setEditable(false);
		addressTxtArea.setBounds(135, 207, 167, 35);
		contentPane.add(addressTxtArea);
		
		searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 patientId = idTxtField.getText();
				  JSONObject jsonParams = new JSONObject();
				  HttpResponse<String> response = req.makeHttpRequest(("http://localhost:5000/patient/" + patientId), "GET", jsonParams, accessToken);
				  if(response.statusCode()==HttpStatus.SC_OK)
				  {
					  afterSearch();
					  String jsonString = response.body();
					  JSONObject jsonObj = new JSONObject(jsonString);
					  nameTxtField.setText(jsonObj.getString("name"));
					  phoneTxtFld.setText(jsonObj.getString("phone"));
	                 dobTxtFld.setText(jsonObj.getString("dob"));
	                 emailTxtFld.setText(jsonObj.getString("email"));
	                 addressTxtArea.setText(jsonObj.getString("address"));
	                 setNonEditMode();
				  }
				  else if(response.statusCode() == HttpStatus.SC_NOT_FOUND)
				  {
					  JOptionPane.showMessageDialog(null,"Sorry! The patient with ID "+ patientId +" is not found!");
				  
					  
				  }else
				  {
					  JOptionPane.showMessageDialog(null,"Something went wrong...");
				  }
					  
			}
		});
		searchBtn.setBounds(366, 40, 78, 29);
		contentPane.add(searchBtn);
		
		 btnBack = new JButton("");
		btnBack.setBounds(26, 2, 29, 29);
		contentPane.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				PatientApplicationGUI appGUI = new PatientApplicationGUI(accessToken);
				appGUI.setVisible(true);
			}
		});
		btnBack.setBounds(19, 4, 29, 29);
		ImageIcon backImage = createResizedIcon("/resources/BackButton.png", 25, 25);
		btnBack.setIcon(backImage);
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editMode = !editMode;
				if(editMode)
				{
					setEditMode();
				}else
				{
					setNonEditMode();
				}
			}
		});
		btnEdit.setBounds(366, 88, 78, 29);
		contentPane.add(btnEdit);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSONObject jsonParams = new JSONObject();
				jsonParams.put("id", patientId);
				jsonParams.put("name", nameTxtField.getText());
				jsonParams.put("dob", dobTxtFld.getText());
				jsonParams.put("address", addressTxtArea.getText());
				jsonParams.put("phone", phoneTxtFld.getText());
				jsonParams.put("email", emailTxtFld.getText());
		        HttpResponse<String> response = req.makeHttpRequest("http://localhost:5000/patient/update", "PATCH", jsonParams, accessToken);
		        if(response.statusCode()==HttpStatus.SC_OK)
		        {
		        	JOptionPane.showMessageDialog(null,"The information is updated successfully!");
		        	setNonEditMode();
		        }else
		        {
		        	JOptionPane.showMessageDialog(null,"Internal Server Error!");
		        }
		        			
			}
		});
		btnSave.setBounds(366, 213, 78, 29);
		contentPane.add(btnSave);
		
		lblName.setVisible(false);
		nameTxtField.setVisible(false);
		lblDob.setVisible(false);
		dobTxtFld.setVisible(false);
		lblPhone.setVisible(false);
		phoneTxtFld.setVisible(false);
		lblEmail.setVisible(false);
		emailTxtFld.setVisible(false);
		lblAddress.setVisible(false);
		addressTxtArea.setVisible(false);
		btnEdit.setVisible(false);
		btnSave.setVisible(false);
		
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

	private void afterSearch()
	{
		lblName.setVisible(true);
		nameTxtField.setVisible(true);
		lblDob.setVisible(true);
		dobTxtFld.setVisible(true);
		lblPhone.setVisible(true);
		phoneTxtFld.setVisible(true);
		lblEmail.setVisible(true);
		emailTxtFld.setVisible(true);
		lblAddress.setVisible(true);
		addressTxtArea.setVisible(true);
		btnEdit.setVisible(true);
		btnSave.setVisible(true);
	}
	private void setEditMode()
	{
		idTxtField.setEditable(false);
		nameTxtField.setEditable(true);
		nameTxtField.setForeground(Color.BLACK); 
		dobTxtFld.setEditable(true);
		dobTxtFld.setForeground(Color.BLACK); 
		phoneTxtFld.setEditable(true);
		phoneTxtFld.setForeground(Color.BLACK); 
		emailTxtFld.setEditable(true);
		emailTxtFld.setForeground(Color.BLACK); 
		emailTxtFld.setEditable(true);
		addressTxtArea.setEditable(true);
		addressTxtArea.setForeground(Color.BLACK); 
		
		
		btnSave.setVisible(true);
		btnSave.setEnabled(true);
	}
	
	private void setNonEditMode()
	{
		idTxtField.setEditable(true);
		nameTxtField.setEditable(false);
		nameTxtField.setForeground(Color.GRAY); 
		dobTxtFld.setEditable(false);
		dobTxtFld.setForeground(Color.GRAY); 
		phoneTxtFld.setEditable(false);
		phoneTxtFld.setForeground(Color.GRAY); 
		emailTxtFld.setEditable(false);
		emailTxtFld.setForeground(Color.GRAY); 
		emailTxtFld.setEditable(false);
		addressTxtArea.setEditable(false);
		addressTxtArea.setForeground(Color.GRAY); 
		
		btnSave.setVisible(false);
		btnSave.setEnabled(false);
		
	}
}
