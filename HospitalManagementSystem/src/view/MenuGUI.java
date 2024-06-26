package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.AppointmentManagement.AddAppointmentGUI;
import view.AppointmentManagement.AppointmentListGUI;
import view.AppointmentManagement.EditAppointmentGUI;
import view.Authentication.LoginGUI;
import view.medicalRecordManagement.RecordApplicationGUI;
import view.patientManagement.PatientApplicationGUI;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private String accessToken;

	/**
	 * Create the frame.
	 */
	public MenuGUI(String accessToken) {
        this.accessToken = accessToken;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hospital Management System");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(121, 6, 219, 24);
		contentPane.add(lblNewLabel);
		
		JButton btnPatient = new JButton("Patient Management");
		btnPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				PatientApplicationGUI patientGUI = new PatientApplicationGUI(accessToken);
				patientGUI.setVisible(true);
			}
		});
		btnPatient.setBounds(131, 53, 209, 39);
		contentPane.add(btnPatient);
		
		JButton btnMedicalRecordManagement = new JButton("Medical Record Management");
		btnMedicalRecordManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				RecordApplicationGUI recordGUI = new RecordApplicationGUI(accessToken);
				recordGUI.setVisible(true);
			}
		});
		btnMedicalRecordManagement.setBounds(128, 116, 212, 39);
		contentPane.add(btnMedicalRecordManagement);
		
		JButton btnAppointmentManagement = new JButton("Appointment Management");
		btnAppointmentManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//appointment
				AppointmentListGUI addAppointment = new AppointmentListGUI(accessToken);
				addAppointment.setVisible(true);
			}
		});
		btnAppointmentManagement.setBounds(131, 179, 209, 39);
		contentPane.add(btnAppointmentManagement);
	}
}
