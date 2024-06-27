package model.Appointment;

public class Appointment {
    private String id;
    private String patientName;
    private String startTime;
    private String endTime;
    private String date;
    private String doctorName;
    private String purpose;

    public Appointment(String id, String patientName, String startTime, String endTime, String date, String doctorName, String purpose) {
        this.id = id;
        this.patientName = patientName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.doctorName = doctorName;
        this.purpose = purpose;
    }

    public String getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDate() {
        return date;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void printAppointment(){
        System.out.println("ID: " + id);
        System.out.println("Patient Name: " + patientName);
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Date: " + date);
        System.out.println("Doctor Name: " + doctorName);
        System.out.println("Purpose: " + purpose);
    }
}
