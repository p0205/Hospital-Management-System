# Hospital-Management-System

## Overview

This project involves three main applications:

1. **Patient Registration Application**: Allows users to register new patients, view patient details, update patient details, and delete patients.
2. **Appointment Scheduling Application**: Allow users to add new appointment, view appointment list. view appointment details, update appointment details, and delete selected appointment.
3. **Medical Record Application**: Handles updating, deleting, and viewing patient medical records.

## Applications 

### Patient Registration Application

This application is responsible for managing patient information. Users can perform the following actions:
- Register new patients
- View patient details
- Update patient details
- Delete patients

### Medical Record Application

This application deals with managing patient medical records. Its functionalities include:
- Updating medical records
- Deleting medical records
- Viewing patient medical records

### Appointmment Application

This application deals with managing patient apppointment. Its function include:
- Add new Appointment
- View Appointment List
- View selected appointment details
- Update selected appointment
- Delete selected appointment

## Architecture/Layer Diagram

Unfortunately, without specific details about your architecture and middleware, I can't provide a detailed diagram. However, you should include diagrams showcasing the architecture and layers of each application, including any middleware components.

## List of URL Endpoints

### Middleware - RESTful

#### Doctor Controller
- Retrieve doctor: `GET "/doctor/{doctorID}"`
- Retrieve doctor list: `GET "/doctor/"`
  
#### Medical Record Controller
- Add medical record: `POST "/medicalRecord/{patientID}/add"`
- Retrieve medical record list of a patient: `GET "/medicalRecord/{patientID}"`
- Retrieve a specific medical record of a patient: `GET "/medicalRecord/{patientID}/{recordID}"`
- Update medical record of a patient: `PATCH "/medicalRecord/{patientID}/{recordID}/update"`
- Delete a medical record: `DELETE "/medicalRecord/{recordID}/delete"`
  
#### Patient Controller
- Add Patient: `POST "/patient/add"`
- Retrieve patient: `GET "/patient/{id}"`
- Delete patient: `DELETE "/patient/delete/{id}"`
- Update patient: `PATCH "/patient/update"`

### Appointment Controller
- Add Appointment: `POST "/api/appointments/"`
- Retrieve Appointment List: `GET "/api/appointments/"`
- Retrieve Selected Appointment: `GET "/api/appointments/:id"`
- Update Selected Appointment: `PATCH "/api/appointments/:id"`
- Delete Selected Appointment: `DELETE "/api/appointments/:id"`

## Functions/Features in the Middleware
The middleware serves as the intermediary for communication between the applications and the database. It provides RESTful endpoints to manage operations related to doctors, patients, and medical records. Key functions/features include:

- Doctor Management: Retrieve doctor details and lists.
- Medical Record Management: Add, update, retrieve, and delete patient medical records.
- Patient Management: Add new patients, retrieve patient information, update patient details, and delete patient records.
- Appointment Management: Add, update, retrieve, and delete patient medical appointment

## Database and Tables
### Doctor Table
- ID (int, Primary Key)
- IC_No (Varchar(12), Unique, Required)
- Phone_No (Varchar(11), Unique, Required)
- Name (Varchar(30), Required)
- Age (Int, Required)
- Department (Varchar(20), Required)

### Patient Table
- ID (int, Primary Key)
- Name (Varchar(50), Required)
- DOB (Date, Required)
- Address (Varchar(20), Required)
- Phone (Varchar(20), Unique, Required)
- Email (Varchar(20), Unique, Required)

### Medical Record Table
- ID (int, Primary Key)
- PatientID (int, Foreign Key referencing Patient Table)
- DoctorID (int, Foreign Key referencing Doctor Table)
- Date (Date, Required)
- Diagnosis (Varchar(100), Required)
- Treatment (Varchar(100), Required)
- FollowUpDate (Date)

### Appointment Table 
- ID (int, Primary Key)
- PatientID (int, Foreign Key referencing Patient Table)
- DoctorID (int, Foreign Key referencing Doctor Table)
- Date (Date, Required)
- StartTime (Time, Required)
- EndTime (Time, Required)
- purpose (Varchar(50))
