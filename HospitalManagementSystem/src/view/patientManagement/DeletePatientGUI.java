package view.patientManagement;


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

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.http.HttpResponse;
import java.awt.event.ActionEvent;

public class DeletePatientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel titleLbl;
	private JTextField IDTxtField;
	private JLabel patientIDLbl;
	private JTextArea patientTextArea;
	private JButton findBtn;
	private JLabel deleteLbl;
	private JButton btnDelete;
	private JButton btnCancel ;
	private MakeHttpRequest req = new MakeHttpRequest();
	private JSONObject jsonParams = new JSONObject();


	/**
	 * Create the frame.
	 */
	public DeletePatientGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		titleLbl = new JLabel("Delete Patient");
		titleLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		titleLbl.setBounds(162, 6, 115, 16);
		contentPane.add(titleLbl);
		
		patientIDLbl = new JLabel("Enter Patient ID: ");
		patientIDLbl.setBounds(40, 39, 115, 16);
		contentPane.add(patientIDLbl);
		
		IDTxtField = new JTextField();
		IDTxtField.setBounds(154, 34, 165, 26);
		contentPane.add(IDTxtField);
		IDTxtField.setColumns(10);
		
		patientTextArea = new JTextArea();
		patientTextArea.setBounds(56, 70, 349, 115);
		patientTextArea.setVisible(false);
		patientTextArea.setEditable(false);
		contentPane.add(patientTextArea);
		
		findBtn = new JButton("Search");
		findBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				 HttpResponse<String> response = req.makeHttpRequest(("http://localhost:8080/patient/" + IDTxtField.getText()), "GET", jsonParams);
				  if(response.statusCode()==HttpStatus.SC_OK)
				  {
					  afterFoundPatient();
		               patientTextArea.setText(patientInfo(response));
				  }
				  else if(response.statusCode() == HttpStatus.SC_NOT_FOUND)
				  {
					  JOptionPane.showMessageDialog(null,"Sorry! The patient with ID "+  IDTxtField.getText() + " is not found!");
				  }else
				  {
					  JOptionPane.showMessageDialog(null,"Something went wrong...");
				  }
			}
		});
		findBtn.setBounds(327, 36, 117, 29);
		contentPane.add(findBtn);
		
		deleteLbl = new JLabel("Do you sure to delete this patient?");
		deleteLbl.setBounds(123, 197, 223, 16);
		deleteLbl.setVisible(false);
		contentPane.add(deleteLbl);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 HttpResponse<String> response = req.makeHttpRequest(("http://localhost:8080/patient/delete/" + IDTxtField.getText()), "DELETE", jsonParams);
				  if(response.statusCode()==HttpStatus.SC_OK)
				  {
					  JOptionPane.showMessageDialog(null,"The patient is deleted successfully!");
					  reset();
				  }else
				  {
					  JOptionPane.showMessageDialog(null,"Something went wrong...");
				  }
			}
		});
		btnDelete.setBounds(56, 226, 117, 29);
		btnDelete.setVisible(false);
		btnDelete.setEnabled(false);
		contentPane.add(btnDelete);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnCancel.setBounds(284, 226, 117, 29);
		btnCancel.setVisible(false);
		btnCancel.setEnabled(false);
		contentPane.add(btnCancel);
		
		
		JButton btnBack = new JButton("");
		ImageIcon backImage = createResizedIcon("/resources/BackButton.png", 25, 25);
		btnBack.setIcon(backImage);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				PatientApplicationGUI appGUI = new PatientApplicationGUI();
				appGUI.setVisible(true);
			}
		});
		
		btnBack.setBounds(17, 2, 30, 29);
		contentPane.add(btnBack);
	}
	
	private String patientInfo(HttpResponse<String> res)
	{
		JSONObject patientInfo = new JSONObject(res.body());

        // Extract patient information
        String name = patientInfo.getString("name");
        String address = patientInfo.getString("address");
        String phone = patientInfo.getString("phone");
        String email = patientInfo.getString("email");
        String dob = patientInfo.getString("dob");

        // Set text area with patient information
       return("Name: " + name + "\n" +
                                "DOB: " + dob + "\n" +
                                "Address: " + address + "\n" +
                                "Email: " + email + "\n" +
                                "Phone: " + phone);
	}
	
	private void afterFoundPatient()
	{
		patientTextArea.setVisible(true);
		deleteLbl.setVisible(true);
		btnDelete.setVisible(true);
		btnCancel.setVisible(true);
		btnDelete.setEnabled(true);
		btnCancel.setEnabled(true);
	}
	
	private void reset()
	{
		IDTxtField.setText("");
		patientTextArea.setText("");
		patientTextArea.setVisible(false);
		deleteLbl.setVisible(false);
		btnDelete.setVisible(false);
		btnCancel.setVisible(false);
		btnDelete.setEnabled(false);
		btnCancel.setEnabled(false);
	}
	
	
	private ImageIcon createResizedIcon(String imagePath, int width, int height) {
	    
	    ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
	    Image originalImage = originalIcon.getImage();
	    Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    return new ImageIcon(resizedImage);
	}
}
