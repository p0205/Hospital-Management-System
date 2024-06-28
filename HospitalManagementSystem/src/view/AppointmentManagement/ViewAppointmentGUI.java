package view.AppointmentManagement;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.http.HttpStatus;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.json.JSONArray;

import controller.MakeHttpRequest;
import model.Appointment.Appointment;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.net.http.HttpResponse;


public class ViewAppointmentGUI extends JFrame{

	private JTextField patientIdFld;
	private JTextField doctorIdFld;
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
	JButton updateBtn;

	private Appointment appointment;
	private MakeHttpRequest req;
	private String[] startTimeParts;
	private String[] endTimeParts;

	private String accessToken;

	/**
	 * Create the frame
	 */
	public ViewAppointmentGUI(String appointmentID, String accessToken) {
		this.accessToken = accessToken;
		req = new MakeHttpRequest();
		appointment = loadAppointment(appointmentID);
		appointment.printAppointment();
		startTimeParts = convertAndStoreTime(appointment.getStartTime());
		endTimeParts = convertAndStoreTime(appointment.getEndTime());
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
	 * @param patientIDFld 
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("Appointment Application");
        setMinimumSize(new Dimension(450, 250));
		setBounds(100, 100, 569, 441);
		
		// Appointment Form
		appointmentLabel = new JLabel("Appointment");
		appointmentLabel.setFont(titleFontStyle);
		appointmentLabel.setBounds(40, 24, 326, 42);
		getContentPane().add(appointmentLabel);
		

		// Patient Name Label
		patientLabel = new JLabel("Patient Id: ");
		patientLabel.setFont(bodyFontStyle);
		patientLabel.setBounds(40, 85, 94, 24);
		getContentPane().add(patientLabel);
		
		// Patient Name Field
		patientIdFld = new JTextField();
		patientIdFld.setFont(new Font("Open Sans", Font.PLAIN, 14));
		patientIdFld.setBounds(139, 89, 148, 21);
		patientIdFld.setText(appointment.getPatientName());
        patientIdFld.setEditable(false);
		getContentPane().add(patientIdFld);
		patientIdFld.setColumns(10);
		
		// Start Time Label
		startTimeLabel = new JLabel("Start Time: ");
		startTimeLabel.setFont(bodyFontStyle);
		startTimeLabel.setBounds(40, 143, 79, 13);
		getContentPane().add(startTimeLabel);
		
		// Start Time Drop Down
		startHourCB = new JComboBox<>(hours);
		startHourCB.setFont(new Font("Open Sans", Font.PLAIN, 14));
		startHourCB.setBounds(139, 143, 60, 17);
		startHourCB.setSelectedItem(startTimeParts[0]);
        startHourCB.setEditable(false);
		getContentPane().add(startHourCB);
		
		JLabel lblNewLabel_3 = new JLabel(":");
		lblNewLabel_3.setFont(bodyFontStyle);
		lblNewLabel_3.setBounds(205, 143, 4, 13);
		getContentPane().add(lblNewLabel_3);
		
		// Start Time Minutes Drop Down
		startMinuteCB = new JComboBox<>(minutes);
		startMinuteCB.setFont(new Font("Open Sans", Font.PLAIN, 14));
		startMinuteCB.setBounds(213, 143, 60, 17);
		startMinuteCB.setSelectedItem(startTimeParts[1]);
        startMinuteCB.setEditable(false);
		getContentPane().add(startMinuteCB);

		startMeridiemCB = new JComboBox<>(meridiem);
		startMeridiemCB.setFont(new Font("Open Sans", Font.PLAIN, 14));
		startMeridiemCB.setBounds(278, 143, 50, 17);
		startMeridiemCB.setSelectedItem(startTimeParts[2]);
        startMeridiemCB.setEditable(false);
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
		endHourCB.setSelectedItem(endTimeParts[0]);
        endHourCB.setEditable(false);
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
		endMinuteCB.setSelectedItem(endTimeParts[1]);
        endMinuteCB.setEditable(false);
		getContentPane().add(endMinuteCB);		
		
		endMeridiemCB = new JComboBox<>(meridiem);
		endMeridiemCB.setFont(new Font("Open Sans", Font.PLAIN, 14));
		endMeridiemCB.setBounds(278, 189, 50, 17);
		endMeridiemCB.setSelectedItem(endTimeParts[2]);
        endMeridiemCB.setEditable(false);
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
		datePicker.getJFormattedTextField().setText(appointment.getDate());
        datePicker.getJFormattedTextField().setEditable(false);
		getContentPane().add(datePicker);
		
		// Doctor Name Label
		doctorLabel = new JLabel("Doctor Name:");
		doctorLabel.setFont(bodyFontStyle);
		doctorLabel.setBounds(40, 271, 94, 13);
		getContentPane().add(doctorLabel);
		
		// Doctor Name Field
		doctorIdFld = new JTextField();
		doctorIdFld.setFont(new Font("Open Sans", Font.PLAIN, 14));
		doctorIdFld.setBounds(40, 289, 148, 19);
		doctorIdFld.setText(appointment.getDoctorName());
        doctorIdFld.setEditable(false);
		getContentPane().add(doctorIdFld);
		doctorIdFld.setColumns(10);
		
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
		purposeFld.setText(appointment.getPurpose());
        purposeFld.setEditable(false);
		getContentPane().add(purposeFld);

		//Cancel Button
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(420, 366, 85, 21);
		getContentPane().add(cancelBtn);

		cancelBtn.addActionListener(e -> {
			this.dispose();
			AppointmentListGUI appointmentList = new AppointmentListGUI(accessToken);
			appointmentList.setVisible(true);
		});
	}

	private Appointment loadAppointment(String appointmentID) {
		System.out.println("Loading appointment with ID: " + appointmentID);
		HttpResponse<String> response = req.makeHttpRequest("http://127.0.0.1:5001/api/appointments/"+appointmentID, "GET", null, accessToken);	
		JSONArray arr;
		Appointment appointment;
		if(response.statusCode()== HttpStatus.SC_OK){
			arr = new JSONArray(response.body());
			appointment = new Appointment(Integer.toString(arr.getJSONObject(0).getInt("id")), Integer.toString(arr.getJSONObject(0).getInt("patientID")), arr.getJSONObject(0).getString("startTime"), arr.getJSONObject(0).getString("endTime"), convertToDateOnly(arr.getJSONObject(0).getString("date")), Integer.toString(arr.getJSONObject(0).getInt("doctorID")), arr.getJSONObject(0).getString("purpose"));
        }
		else{
			appointment = null;
		}
			
		return appointment;
	}

	private String convertToDateOnly(String timestamp) {
        try {
            // Define the input and output formatters
            DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            // Parse the input timestamp
            LocalDateTime dateTime = LocalDateTime.parse(timestamp, inputFormatter);
            
            // Format the date portion
            return dateTime.format(outputFormatter);
        } catch (DateTimeParseException e) {
            // Handle the case where the input format is incorrect
            System.err.println("Invalid date format: " + e.getMessage());
            return null;
        }
    }

	public String[] convertAndStoreTime(String time24) {
        // Define the input formatter for 24-hour time
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Define the output formatter for 12-hour time with AM/PM
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        // Parse the input time string to a LocalTime object
        LocalTime time = LocalTime.parse(time24, inputFormatter);

        // Format the time to 12-hour format with AM/PM
        String time12 = time.format(outputFormatter);

        // Extract the components
        String[] timeParts = time12.split("[: ]");
        
		return timeParts;
    }
}
