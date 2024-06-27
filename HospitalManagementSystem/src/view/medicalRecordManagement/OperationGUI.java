package view.medicalRecordManagement;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import controller.MakeHttpRequest;

import java.net.URL;
import java.net.http.HttpResponse;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OperationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFldPatientID;
	private JTextField txtFldName;
	 private JScrollPane scrollPane;
	
	
	private final int patientID;
	private MakeHttpRequest req;
	private JSONObject patient;
	private JSONArray records;

	private String accessToken;
	
	/**
	 * Create the frame.
	 */
	public OperationGUI(int patientID, String accessToken) {
		this.accessToken = accessToken;
		this.patientID = patientID;
		req = new MakeHttpRequest();
		patient = getPatient();
		records = loadRecords();
		initialize();
	}
	
	
	public void initialize() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUpdateMedicalRecord = new JLabel("Medical Record Management System");
		lblUpdateMedicalRecord.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblUpdateMedicalRecord.setBounds(104, 6, 275, 19);
		contentPane.add(lblUpdateMedicalRecord);
		
		JLabel LblPatientID = new JLabel("Patient ID      :");
		LblPatientID.setBounds(33, 37, 101, 16);
		contentPane.add(LblPatientID);
		
		txtFldPatientID = new JTextField();
		txtFldPatientID.setEditable(false);
		txtFldPatientID.setColumns(10);
		txtFldPatientID.setBounds(146, 32, 130, 26);
		txtFldPatientID.setText(String.valueOf(patient.getInt("id")));
		contentPane.add(txtFldPatientID);
		
		JLabel lblNewLabel = new JLabel("Medical Records");
		lblNewLabel.setBounds(33, 93, 118, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblPatientName = new JLabel("Patient Name :");
		lblPatientName.setBounds(33, 65, 101, 16);
		contentPane.add(lblPatientName);
		
		txtFldName = new JTextField();
		txtFldName.setEditable(false);
		txtFldName.setColumns(10);
		txtFldName.setText(String.valueOf(patient.getString("name")));
		txtFldName.setBounds(145, 60, 228, 26);
		
		contentPane.add(txtFldName);
		
		  // Add the scroll pane to the frame
        scrollPane = new JScrollPane();
        scrollPane.setBounds(33, 128, 400, 127);
        contentPane.add(scrollPane);

        refreshRecordsPanel();

        
        JButton btnAddRecord = new JButton("+");
        btnAddRecord.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//jump to Add record GUI
        		dispose();
        		AddRecordGUI addGUI = new AddRecordGUI(patientID, accessToken);
        		addGUI.setVisible(true);
        	}
        });
        btnAddRecord.setBackground(new Color(147, 147, 147));
        btnAddRecord.setBounds(142, 88, 40, 29);
        contentPane.add(btnAddRecord);
        
        JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				RecordApplicationGUI appGUI = new RecordApplicationGUI(accessToken);
				appGUI.setVisible(true);
			}
		});
		ImageIcon backImage = createResizedIcon("/resources/BackButton.png", 25, 25);
		btnBack.setIcon(backImage);
        btnBack.setBounds(6, 3, 29, 29);
        contentPane.add(btnBack);
        setVisible(true);

	}

	 private JPanel createRecordPanel(String date,int patientID,int recordID) {
	        // Create a panel for each record
	        JPanel panel = new JPanel();
	        panel.setLayout(null);
	        panel.setPreferredSize(new Dimension(330, 30));

	        // Create the delete button
	        JButton btnDelete = new JButton("-");
	        btnDelete.setBounds(0, 0, 40, 30);
	        btnDelete.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		if (JOptionPane.showConfirmDialog(null, "Are you sure to delete this medical record?", "WARNING",
	        		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
	       
	        			 // Delete record
	        			HttpResponse<String> response = deleteRecord(recordID);
	        			if(response.statusCode()== HttpStatus.SC_ACCEPTED)
	        			{
	        				JOptionPane.showMessageDialog(null, "The record is deleted.");
	        				refreshRecordsPanel();
	        			}
	        			else
	        			{
	        				JOptionPane.showMessageDialog(null, "Internal Server Error...");
	        			}
	                    
	        		} 
	        	}
	        });
	        
	        JLabel lblRecord = new JLabel("Record : " + date);
	        lblRecord.setBounds(40, 0, 200, 30);

	        JButton btnView = new JButton("View");
	        btnView.setBounds(250, 0, 80, 30);
	        btnView.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Dispose the current frame and open the update record page
	                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
	                currentFrame.dispose();
	                UpdateRecordGUI updateGUI = new UpdateRecordGUI(patientID, recordID, accessToken);
	                updateGUI.setVisible(true);
	            }
	        });

	        // Add components to the panel
	        panel.add(btnDelete);
	        panel.add(lblRecord);
	        panel.add(btnView);

	        return panel;
	    }
	 
	 private JSONArray loadRecords()
	 {
		 HttpResponse<String> response = req.makeHttpRequest("http://localhost:5000/medicalRecord/" + patientID, "GET", null, accessToken);	
		 if(response.statusCode()== HttpStatus.SC_OK)
			 return new JSONArray(response.body());
		 return new JSONArray();
	 }
	 
	 private JSONObject getPatient()
	 {
		 HttpResponse<String> response = req.makeHttpRequest("http://localhost:5000/patient/" + patientID, "GET", null, accessToken);
		 return new JSONObject(response.body());
	 }
	 
	  private void refreshRecordsPanel() {
	        records = loadRecords();
	        JPanel mainPanel = new JPanel();
	        mainPanel.setLayout(null);

	        int panelHeight = 0;
	        // Add appointments to the main panel
	        int yOffset = 10;
	        for (int i = 0; i < records.length(); i++) {
	            JSONObject jsonObj = records.getJSONObject(i);
	            JPanel appointmentPanel = createRecordPanel((String) jsonObj.get("date"), jsonObj.getInt("patientID"), jsonObj.getInt("id"));
	            appointmentPanel.setBounds(10, yOffset, 330, 30); // Set position and size
	            mainPanel.add(appointmentPanel);
	            yOffset += 40;
	            panelHeight += 40;
	        }
	        mainPanel.setPreferredSize(new Dimension(350, panelHeight + 10)); // Set preferred size to allow scrolling

	        scrollPane.setViewportView(mainPanel);
	    }

	  private HttpResponse<String> deleteRecord(int recordID)
	  {
		 return req.makeHttpRequest("http://localhost:5000/medicalRecord/" + recordID + "/delete", "DELETE", null, accessToken);
		  
	  }
	  
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
	  /* private ImageIcon createResizedIcon(String imagePath, int width, int height) {
		    
		    ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
		    Image originalImage = originalIcon.getImage();
		    Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		    return new ImageIcon(resizedImage);
		} */
}
