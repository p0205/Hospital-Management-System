package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.AppointmentManagement.AddAppointmentGUI;
import view.AppointmentManagement.AppointmentListGUI;
import view.AppointmentManagement.EditAppointmentGUI;
import view.medicalRecordManagement.RecordApplicationGUI;
import view.patientManagement.PatientApplicationGUI;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainApplicationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditAppointmentGUI frame = new EditAppointmentGUI();
					frame.setVisible(true);

					//MainApplicationGUI frame = new MainApplicationGUI();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainApplicationGUI() {
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
				PatientApplicationGUI patientGUI = new PatientApplicationGUI();
				patientGUI.setVisible(true);
			}
		});
		btnPatient.setBounds(131, 53, 209, 39);
		contentPane.add(btnPatient);
		
		JButton btnMedicalRecordManagement = new JButton("Medical Record Management");
		btnMedicalRecordManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				RecordApplicationGUI recordGUI = new RecordApplicationGUI();
				recordGUI.setVisible(true);
			}
		});
		btnMedicalRecordManagement.setBounds(128, 116, 212, 39);
		contentPane.add(btnMedicalRecordManagement);
		
		JButton btnAppointmentManagement = new JButton("Appointment Management");
		btnAppointmentManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//appointment
			}
		});
		btnAppointmentManagement.setBounds(131, 179, 209, 39);
		contentPane.add(btnAppointmentManagement);
	}
}
