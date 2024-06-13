package view.patientManagement;
import javax.swing.JFrame;
import javax.swing.JLabel;

import view.MainApplicationGUI;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatientApplicationGUI extends JFrame{


	private static final long serialVersionUID = 1L;



	/**
	 * Create the application.
	 */
	public PatientApplicationGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("Patient Management System");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		titleLabel.setBounds(114, 16, 226, 39);
		getContentPane().add(titleLabel);
		
		JButton addBtn = new JButton("Register Patient");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				RegisterPatientGUI registerGUI = new RegisterPatientGUI();
				registerGUI.setVisible(true);
			}
		});
		addBtn.setBounds(149, 72, 148, 29);
		getContentPane().add(addBtn);
		
		JButton searchBtn = new JButton("Search Patient");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
				ViewPatientGUI viewGUI = new ViewPatientGUI();
				viewGUI.setVisible(true);
			}
		});
		searchBtn.setBounds(149, 134, 148, 29);
		getContentPane().add(searchBtn);
		
		JButton btnDeletePatient = new JButton("Delete Patient");
		btnDeletePatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				DeletePatientGUI deleteGUI = new DeletePatientGUI();
				deleteGUI.setVisible(true);
			}
		});
		btnDeletePatient.setBounds(149, 197, 148, 29);
		getContentPane().add(btnDeletePatient);
		
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
		btnBack.setBounds(6, 6, 29, 29);
		getContentPane().add(btnBack);
	}
	private ImageIcon createResizedIcon(String imagePath, int width, int height) {
	    
	    ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
	    Image originalImage = originalIcon.getImage();
	    Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    return new ImageIcon(resizedImage);
	}
}
