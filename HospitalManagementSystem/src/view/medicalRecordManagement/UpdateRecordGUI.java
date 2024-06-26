package view.medicalRecordManagement;
import java.awt.Color;
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
import javax.swing.JButton;
import java.net.http.HttpResponse;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class UpdateRecordGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFldPatientID;
	private JTextField txtFldRecordID;
	private JTextField txtFieldDrID;
	private JTextField txtFldDate;
	private JTextField txtFldFollowUpDate;
	
	
	private JTextArea txtAreaTreatment;
	private JTextArea txtAreaDiagnosis;
	private JButton btnEdit;
	private JButton btnSave;
	

	private MakeHttpRequest req;
	private final int patientID ;
	private final int recordID ;
	private JSONObject record;
	private boolean editMode ;

	private String accessToken;

	/**
	 * Create the frame.
	 */
	public UpdateRecordGUI(int patientID, int recordID, String accessToken) {
		this.accessToken = accessToken;
		this.patientID = patientID;
		this.recordID = recordID;
		editMode = false;
		req = new MakeHttpRequest();
		record = getMedicalRecord();
		initialize();
	}
	
	public void initialize() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Medical Record");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(168, 4, 113, 16);
		contentPane.add(lblNewLabel);
		
		JLabel LblPatientID = new JLabel("Patient ID      :");
		LblPatientID.setBounds(33, 37, 101, 16);
		contentPane.add(LblPatientID);
		
		txtFldPatientID = new JTextField();
		txtFldPatientID.setEditable(false);
		txtFldPatientID.setColumns(10);
		txtFldPatientID.setBounds(146, 32, 130, 26);
		txtFldPatientID.setText(String.valueOf(patientID));
		txtFldPatientID.setForeground(Color.GRAY); 
		contentPane.add(txtFldPatientID);
		
		JLabel lblRecordId = new JLabel("Record ID      :");
		lblRecordId.setBounds(33, 64, 101, 16);
		contentPane.add(lblRecordId);
		
		txtFldRecordID = new JTextField();
		txtFldRecordID.setEditable(false);
		txtFldRecordID.setColumns(10);
		txtFldRecordID.setBounds(146, 59, 130, 26);
		txtFldRecordID.setText(String.valueOf(recordID));
		txtFldRecordID.setForeground(Color.GRAY); 
		contentPane.add(txtFldRecordID);
		
		JLabel lblDate = new JLabel("Doctor ID      :");
		lblDate.setBounds(33, 92, 101, 16);
		contentPane.add(lblDate);
		
		JLabel lblDate_2 = new JLabel("Date              :");
		lblDate_2.setBounds(33, 120, 101, 16);
		contentPane.add(lblDate_2);
		
		JLabel lblDiagnosis = new JLabel("Diagnosis     :");
		lblDiagnosis.setBounds(33, 148, 101, 16);
		contentPane.add(lblDiagnosis);
		
		JLabel lblTreatment = new JLabel("Treatment     :");
		lblTreatment.setBounds(33, 193, 101, 16);
		contentPane.add(lblTreatment);
		
		JLabel lblFollowUpDate = new JLabel("Follow Up Date  :");
		lblFollowUpDate.setBounds(33, 239, 119, 16);
		contentPane.add(lblFollowUpDate);
		
		txtFieldDrID = new JTextField();
		txtFieldDrID.setEditable(false);
		txtFieldDrID.setColumns(10);
		txtFieldDrID.setBounds(146, 87, 130, 26);
		txtFieldDrID.setText(String.valueOf(record.getInt("doctorID")));
		txtFieldDrID.setForeground(Color.GRAY); 
		contentPane.add(txtFieldDrID);
		
		txtFldDate = new JTextField();
		txtFldDate.setEditable(false);
		txtFldDate.setColumns(10);
		txtFldDate.setBounds(146, 115, 174, 26);
		txtFldDate.setText(String.valueOf(record.get("date")));
		txtFldDate.setForeground(Color.GRAY); 
		contentPane.add(txtFldDate);
		
		txtFldFollowUpDate = new JTextField();
		txtFldFollowUpDate.setEditable(false);
		txtFldFollowUpDate.setColumns(10);
		txtFldFollowUpDate.setBounds(146, 234, 174, 26);
		txtFldFollowUpDate.setForeground(Color.GRAY); 
		Object followUpDateObj = record.get("followUpDate");

		if (followUpDateObj == null || followUpDateObj.toString().equals("null")) {
		    txtFldFollowUpDate.setText("");
		} else {
		    txtFldFollowUpDate.setText(followUpDateObj.toString());
		}
		
		contentPane.add(txtFldFollowUpDate);
		
		txtAreaTreatment = new JTextArea();
		txtAreaTreatment.setEditable(false);
		txtAreaTreatment.setBounds(146, 193, 202, 37);
		txtAreaTreatment.setText(record.getString("treatment"));
		txtAreaTreatment.setForeground(Color.GRAY); 
		contentPane.add(txtAreaTreatment);
		
		txtAreaDiagnosis = new JTextArea();
		txtAreaDiagnosis.setEditable(false);
		txtAreaDiagnosis.setBounds(146, 148, 202, 37);
		txtAreaDiagnosis.setText(record.getString("diagnosis"));
		txtAreaDiagnosis.setForeground(Color.GRAY); 
		contentPane.add(txtAreaDiagnosis);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSONObject jsonParams = new JSONObject();
				jsonParams.put("id",recordID);
				jsonParams.put("date",txtFldDate.getText());
				jsonParams.put("diagnosis",txtAreaDiagnosis.getText());
				jsonParams.put("treatment",txtAreaTreatment.getText());
				jsonParams.put("followUpDate",txtFldFollowUpDate.getText());
				jsonParams.put("doctorID",txtFieldDrID.getText());
				jsonParams.put("patientID",patientID);
				HttpResponse<String> response = req.makeHttpRequest("http://localhost:5000/medicalRecord/"+patientID+"/"+recordID+"/update","PATCH", jsonParams, accessToken);
				if(response.statusCode()==HttpStatus.SC_OK)
				{
					JOptionPane.showMessageDialog(null, "The record is updated successfully!");
					setNonEditMode();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Internal Server Error...");
				}
			}
		});
		btnSave.setEnabled(false);
		btnSave.setBounds(360, 234, 75, 29);
		btnSave.setVisible(false);
		contentPane.add(btnSave);
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editMode = !editMode;
				if(editMode)
				{
					setEditMode();
				}
				else
				{
					setNonEditMode();
				}
			}
		});
		btnEdit.setBounds(342, 32, 93, 29);
		contentPane.add(btnEdit);
		
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
		btnBack.setBounds(6, 0, 29, 29);
		contentPane.add(btnBack);
	}
	
	private JSONObject getMedicalRecord()
	{
		HttpResponse<String> response = req.makeHttpRequest("http://localhost:5000/medicalRecord/"+patientID+"/"+recordID,"GET", null, accessToken);
		String jsonString = response.body();
		JSONObject jsonObj = new JSONObject(jsonString);
		return jsonObj;
	}
	private ImageIcon createResizedIcon(String imagePath, int width, int height) {
	    
	    ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
	    Image originalImage = originalIcon.getImage();
	    Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    return new ImageIcon(resizedImage);
	}
	
	private void setEditMode()
	{
		txtFieldDrID.setEditable(true);
		txtFieldDrID.setForeground(Color.BLACK); 
		txtFldDate.setEditable(true);
		txtFldDate.setForeground(Color.BLACK); 
		txtFldFollowUpDate.setEditable(true);
		txtFldFollowUpDate.setForeground(Color.BLACK); 
		txtAreaTreatment.setEditable(true);
		txtAreaTreatment.setForeground(Color.BLACK); 
		txtAreaDiagnosis.setEditable(true);
		txtAreaDiagnosis.setForeground(Color.BLACK); 
		btnSave.setVisible(true);
		btnSave.setEnabled(true);
	}
	
	private void setNonEditMode()
	{
		txtFieldDrID.setEditable(false);
		txtFieldDrID.setForeground(Color.GRAY); 
		txtFldDate.setEditable(false);
		txtFldDate.setForeground(Color.GRAY); 
		txtFldFollowUpDate.setEditable(false);
		txtFldFollowUpDate.setForeground(Color.GRAY); 
		txtAreaTreatment.setEditable(false);
		txtAreaTreatment.setForeground(Color.GRAY); 
		txtAreaDiagnosis.setEditable(false);
		txtAreaDiagnosis.setForeground(Color.GRAY); 
		btnSave.setVisible(false);
		btnSave.setEnabled(false);
		
	}
	

}
