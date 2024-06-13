package view.medicalRecordManagement;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.http.HttpStatus;

import java.net.http.HttpResponse;

import controller.MakeHttpRequest;
import view.MainApplicationGUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecordApplicationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private int patientID;
	private MakeHttpRequest req;
	/**
	 * Create the frame.
	 */
	public RecordApplicationGUI() {
		req = new MakeHttpRequest();
		initialize();
	}
	
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLbl = new JLabel("Medical Record Management System");
		titleLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		titleLbl.setBounds(92, 6, 270, 19);
		contentPane.add(titleLbl);
		
		JLabel lblNewLabel = new JLabel("Enter Patient ID :");
		lblNewLabel.setBounds(52, 55, 117, 16);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(181, 50, 219, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText()==null)
				{
					JOptionPane.showMessageDialog(null, "Please enter patient ID!");
				}
				else
				{
					patientID = Integer.valueOf(textField.getText());
					HttpResponse<String> response = req.makeHttpRequest("http://localhost:8080/patient/"+patientID, "GET", null);
					if(response.statusCode()==HttpStatus.SC_OK)
					{
						dispose();
						OperationGUI operationGUI = new OperationGUI(patientID);
						operationGUI.setVisible(true);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The patient is not found!");
					}
				}
				
			}
		});
		btnNewButton.setBounds(150, 105, 117, 29);
		contentPane.add(btnNewButton);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainApplicationGUI appGUI = new MainApplicationGUI();
				appGUI.setVisible(true);
			}
		});
		ImageIcon backImage = createResizedIcon("/resources/BackButton.png", 25, 25);
		btnBack.setIcon(backImage);
		btnBack.setBounds(6, 3, 29, 29);
		contentPane.add(btnBack);
	}

	private ImageIcon createResizedIcon(String imagePath, int width, int height) {
	    
	    ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
	    Image originalImage = originalIcon.getImage();
	    Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    return new ImageIcon(resizedImage);
	}
}
