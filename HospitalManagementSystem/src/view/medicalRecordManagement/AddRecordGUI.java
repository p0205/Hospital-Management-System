package view.medicalRecordManagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import controller.MakeHttpRequest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddRecordGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFldPatientID;
	private JTextField txtFldFollowUpDate;
	private JTextField txtFldDate;
	
	private final int patientID;
	private Map<String, Integer> doctorMap;
	private MakeHttpRequest req; 
	private JSONArray doctors;
	private String accessToken;

	/**
	 * Create the frame.
	 */
	public AddRecordGUI(int patientID, String accessToken) {
		this.accessToken = accessToken;
		this.patientID = patientID;
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
		
		JLabel lblAddNewMedical = new JLabel("Add New Medical Record");
		lblAddNewMedical.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblAddNewMedical.setBounds(139, 6, 187, 19);
		contentPane.add(lblAddNewMedical);
		
		JLabel LblPatientID = new JLabel("Patient ID      :");
		LblPatientID.setBounds(33, 37, 101, 16);
		contentPane.add(LblPatientID);
		
		txtFldPatientID = new JTextField();
		txtFldPatientID.setEditable(false);
		txtFldPatientID.setText(String.valueOf(patientID));
		txtFldPatientID.setColumns(10);
		txtFldPatientID.setBounds(146, 32, 130, 26);
		contentPane.add(txtFldPatientID);
		
		JLabel lblNewRecord = new JLabel("New Record Information");
		lblNewRecord.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblNewRecord.setBounds(33, 65, 187, 16);
		contentPane.add(lblNewRecord);
		
		JLabel lblDoctor = new JLabel("Doctor                        :");
		lblDoctor.setBounds(33, 93, 153, 16);
		contentPane.add(lblDoctor);
		
		JLabel lblDate = new JLabel("Date                            :");
		lblDate.setBounds(33, 119, 153, 16);
		contentPane.add(lblDate);
		
		JLabel lblDiagnosis = new JLabel("Diagnosis                    :");
		lblDiagnosis.setBounds(33, 147, 153, 16);
		contentPane.add(lblDiagnosis);
		
		JLabel lblTreatment = new JLabel("Treatment                    :");
		lblTreatment.setBounds(33, 189, 153, 16);
		contentPane.add(lblTreatment);
		
		JLabel lblFollowUpDate = new JLabel("Follow Up Date (if any) :");
		lblFollowUpDate.setBounds(33, 239, 153, 16);
		contentPane.add(lblFollowUpDate);
		
		txtFldFollowUpDate = new JTextField();
		txtFldFollowUpDate.setBounds(196, 234, 130, 26);
		contentPane.add(txtFldFollowUpDate);
		txtFldFollowUpDate.setColumns(10);
		
		txtFldDate = new JTextField();
		txtFldDate.setColumns(10);
		txtFldDate.setBounds(196, 114, 130, 26);
		contentPane.add(txtFldDate);
		
		JTextArea txtAreaDiagnosis = new JTextArea();
		txtAreaDiagnosis.setBounds(198, 147, 244, 28);
		contentPane.add(txtAreaDiagnosis);
		
		JTextArea txtAreaTreatment = new JTextArea();
		txtAreaTreatment.setBounds(198, 189, 244, 28);
		contentPane.add(txtAreaTreatment);
		
		JComboBox<String> comboBoxDoctor = new JComboBox<String>();
		doctors = loadDoctors();
		 doctorMap = new HashMap<>();
		 for (int i = 0; i < doctors.length(); i++) {
			 
			 String idWithName = doctors.getJSONObject(i).getInt("id") + " - " + doctors.getJSONObject(i).getString("name");
			 comboBoxDoctor.addItem(idWithName); 
			 doctorMap.put(idWithName, doctors.getJSONObject(i).getInt("id"));
	     }
		comboBoxDoctor.setBounds(195, 89, 239, 27);
		contentPane.add(comboBoxDoctor);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtFldDate.getText().trim().isEmpty() || txtAreaDiagnosis.getText().trim().isEmpty() || txtAreaTreatment.getText().trim().isEmpty())
				{
					JOptionPane.showMessageDialog(null,"Please fill in the required fields!");
				}
				else
				{
					JSONObject jsonParams = new JSONObject();
					jsonParams.put("date", txtFldDate.getText());
					jsonParams.put("diagnosis", txtAreaDiagnosis.getText());
					jsonParams.put("treatment", txtAreaTreatment.getText());
					jsonParams.put("followUpDate", txtFldFollowUpDate.getText());
					int selectedDoctor = doctorMap.get((String) comboBoxDoctor.getSelectedItem());
					jsonParams.put("doctorID", selectedDoctor);
					HttpResponse<String> response = req.makeHttpRequest("http://localhost:5000/medicalRecord/" + patientID + "/add","POST", jsonParams, accessToken);
					if(response.statusCode() == HttpStatus.SC_OK)
					{
						JOptionPane.showMessageDialog(null, "New medical record is added successfully!");
					}else
					{
						JOptionPane.showMessageDialog(null, "Internal Server Error...");
					}
				}
				
			}
		});
		btnSave.setBounds(338, 234, 96, 29);
		contentPane.add(btnSave);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				OperationGUI operationGUI = new OperationGUI(patientID, accessToken);
				operationGUI.setVisible(true);
			}
		});
		ImageIcon backImage = createResizedIcon("/resources/BackButton.png", 25, 25);
		btnBack.setIcon(backImage);
		btnBack.setBounds(6, 3, 29, 29);
		contentPane.add(btnBack);
	}
	
	private JSONArray loadDoctors()
	{
		HttpResponse<String> response = req.makeHttpRequest("http://localhost:5000/doctor/","GET", null, accessToken);
		String jsonString = response.body();
		return new JSONArray(jsonString);
	}
	
	private ImageIcon createResizedIcon(String imagePath, int width, int height) {
	    
	    ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
	    Image originalImage = originalIcon.getImage();
	    Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    return new ImageIcon(resizedImage);
	}
}
