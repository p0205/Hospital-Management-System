package view.AppointmentManagement;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;


public class AddAppointmentGUI extends JFrame{

	private JTextField patientNameFld;
	private JTextField doctorNameFld;
	String [] hours = new String[]{ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", 
			"11", "12"};
	String [] minutes = new String[]{ "00", "30" };
	String [] meridiem = new String[]{ "AM", "PM" };
	Font bodyFontStyle = new Font("SansSerif", Font.PLAIN, 14);
	Font titleFontStyle = new Font("Open Sans Semibold", Font.BOLD, 36);

	JLabel appointmentLabel;
	JLabel patientLabel;
	JLabel startTimeLabel;
	JComboBox<String> startHourCB;
	JComboBox<String> startMinuteCB;
	JComboBox<String> startMeridiemCB;
	JLabel endTimeLabel;
	JComboBox<String> endHourCB;
	JComboBox<String> endMinuteCB;
	JComboBox<String> endMeridiemCB;
	JLabel dateLabel;
	JLabel purposeLabel;
	JLabel doctorLabel;
	JTextArea purposeFld;
	JButton addBtn;

	/**
	 * Create the frame
	 */
	public AddAppointmentGUI() {
		initialize();
	}

	//DateLabelFormatter class
	public class DateLabelFormatter extends AbstractFormatter {

		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("Appointment Application");
        setMinimumSize(new Dimension(450, 250));
		setBounds(100, 100, 569, 441);
		
		// Add Appointment Form
		appointmentLabel = new JLabel("New Appointment");
		appointmentLabel.setFont(titleFontStyle);
		appointmentLabel.setBounds(40, 24, 326, 42);
		getContentPane().add(appointmentLabel);
		

		// Patient Name Label
		patientLabel = new JLabel("Patient Name: ");
		patientLabel.setFont(bodyFontStyle);
		patientLabel.setBounds(40, 85, 94, 24);
		getContentPane().add(patientLabel);
		
		// Patient Name Field
		patientNameFld = new JTextField();
		patientNameFld.setFont(new Font("Open Sans", Font.PLAIN, 14));
		patientNameFld.setBounds(139, 89, 148, 21);
		getContentPane().add(patientNameFld);
		patientNameFld.setColumns(10);
		
		// Start Time Label
		startTimeLabel = new JLabel("Start Time: ");
		startTimeLabel.setFont(bodyFontStyle);
		startTimeLabel.setBounds(40, 143, 79, 13);
		getContentPane().add(startTimeLabel);
		
		// Start Time Drop Down
		startHourCB = new JComboBox<>(hours);
		startHourCB.setFont(new Font("Open Sans", Font.PLAIN, 14));
		startHourCB.setBounds(139, 143, 60, 17);
		getContentPane().add(startHourCB);
		
		JLabel lblNewLabel_3 = new JLabel(":");
		lblNewLabel_3.setFont(bodyFontStyle);
		lblNewLabel_3.setBounds(205, 143, 4, 13);
		getContentPane().add(lblNewLabel_3);
		
		// Start Time Minutes Drop Down
		startMinuteCB = new JComboBox<>(minutes);
		startMinuteCB.setFont(new Font("Open Sans", Font.PLAIN, 14));
		startMinuteCB.setBounds(213, 143, 60, 17);
		getContentPane().add(startMinuteCB);

		startMeridiemCB = new JComboBox<>(meridiem);
		startMeridiemCB.setFont(new Font("Open Sans", Font.PLAIN, 14));
		startMeridiemCB.setBounds(278, 143, 50, 17);
		getContentPane().add(startMeridiemCB);
		
		// End Time Label
		endTimeLabel = new JLabel("End Time:");
		endTimeLabel.setFont(bodyFontStyle);
		endTimeLabel.setBounds(40, 189, 66, 13);
		getContentPane().add(endTimeLabel);
		
		// End Time Drop Down
		endHourCB = new JComboBox<>(hours);
		endHourCB.setFont(new Font("Open Sans", Font.PLAIN, 14));
		endHourCB.setBounds(139, 189, 60, 17);
		getContentPane().add(endHourCB);

		
		// End Time Minutes Label
		JLabel lblNewLabel_5 = new JLabel(":");
		lblNewLabel_5.setFont(bodyFontStyle);
		lblNewLabel_5.setBounds(205, 189, 4, 13);
		getContentPane().add(lblNewLabel_5);
		
		// End Time Minutes Drop Down
		endMinuteCB = new JComboBox<>(minutes);
		endMinuteCB.setFont(new Font("Open Sans", Font.PLAIN, 14));
		endMinuteCB.setBounds(213, 189, 60, 17);
		getContentPane().add(endMinuteCB);		
		
		endMeridiemCB = new JComboBox<>(meridiem);
		endMeridiemCB.setFont(new Font("Open Sans", Font.PLAIN, 14));
		endMeridiemCB.setBounds(278, 189, 50, 17);
		getContentPane().add(endMeridiemCB);
		
		// Date Label
		dateLabel = new JLabel("Date: ");
		dateLabel.setFont(bodyFontStyle);
		dateLabel.setBounds(40, 235, 66, 13);
		getContentPane().add(dateLabel);
		
		// Date Picker
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(139, 235, 148, 21);
		getContentPane().add(datePicker);
		
		// Doctor Name Label
		doctorLabel = new JLabel("Doctor Name:");
		doctorLabel.setFont(bodyFontStyle);
		doctorLabel.setBounds(40, 271, 94, 13);
		getContentPane().add(doctorLabel);
		
		// Doctor Name Field
		doctorNameFld = new JTextField();
		doctorNameFld.setFont(new Font("Open Sans", Font.PLAIN, 14));
		doctorNameFld.setBounds(40, 289, 148, 19);
		getContentPane().add(doctorNameFld);
		doctorNameFld.setColumns(10);
		
		// Purpose Label
		purposeLabel = new JLabel("Purpose:");
		purposeLabel.setFont(bodyFontStyle);
		purposeLabel.setBounds(306, 271, 66, 13);
		getContentPane().add(purposeLabel);
		
		// Purpose Field
		purposeFld = new JTextArea();
		purposeFld.setFont(new Font("Open Sans", Font.PLAIN, 14));
		purposeFld.setLineWrap(true);
		purposeFld.setBounds(306, 289, 224, 56);
		getContentPane().add(purposeFld);
		
		// Add Button
		addBtn = new JButton("Add");
		addBtn.setBounds(445, 366, 85, 21);
		getContentPane().add(addBtn);

		addBtn.addActionListener(e -> {
			// Add Appointment
			String patientName = patientNameFld.getText();
			String doctorName = doctorNameFld.getText();
			String purpose = purposeFld.getText();
			String startTime = startHourCB.getSelectedItem() + ":" + startMinuteCB.getSelectedItem() + " " + startMeridiemCB.getSelectedItem();
			String endTime = endHourCB.getSelectedItem() + ":" + endMinuteCB.getSelectedItem() + " " + endMeridiemCB.getSelectedItem();
			String date = datePicker.getJFormattedTextField().getText();
			System.out.println("Patient Name: " + patientName);
			System.out.println("Doctor Name: " + doctorName);
			System.out.println("Purpose: " + purpose);
			System.out.println("Start Time: " + startTime);
			System.out.println("End Time: " + endTime);
			System.out.println("Date: " + date);
		});
	}
}
