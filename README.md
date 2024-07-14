# Hospital-Management-System

## Overview

This project involves three main applications:

1. **Patient Registration Application**: Allow users to register new patients, view patient details, update patient details, and delete patient.
2. **Appointment Scheduling Application**: Allow users to add new appointment, view appointment list, view appointment details, update appointment details, and delete selected appointment.
3. **Medical Record Application**: Allow users to add new medical record, view, update and delete medical record.

## Applications 

### Authentication Middleware

This middleware is responsible for administrator login and register. It could encrypt user password and generate JWT access token to access other applications API. It act as the communication media between different backend appplication api and frontend application.  

### Patient Registration Application

This application is responsible for managing patient information. Users can perform the following actions:
- Register new patients
- View patient details
- Update patient details
- Delete patients

### Medical Record Application

This application deals with managing patient medical records. Its functionalities include:
- Add new medical record
- Update medical records
- Delete medical records
- View medical records

### Appointmment Application

This application deals with managing patient apppointment. Its function include:
- Add new appointment
- View appointment list
- View selected appointment details
- Update selected appointment
- Delete selected appointment

## Architecture/Layer Diagram

Overall Architecture
![architecture_diagram-Page-1 drawio](https://github.com/p0205/Hospital-Management-System/assets/63178114/d83553dc-7119-49a7-85d8-731b961626ab)

Aunthentication Middleware Architecture
![ad-Page-2 drawio](https://github.com/user-attachments/assets/121376f3-687a-49f5-a38e-359a312e599e)

Patient Management Application Architecture
![Patient drawio](https://github.com/user-attachments/assets/55d0611b-5ae5-4b8a-8c72-26e08be37409)

Doctor Management Application Architecture
![doctor drawio](https://github.com/user-attachments/assets/9d2064eb-8a6a-4c20-b087-c462ca34710e)

Medical Record Application Architecture
![MR drawio](https://github.com/user-attachments/assets/9c453180-24c1-4784-8aff-e0157c470882)

Appointment Application Architecture
![ad-Page-1 drawio](https://github.com/user-attachments/assets/bd3fc21e-c83d-4de2-a614-51391152fe75)

## List of URL Endpoints

### Middleware - RESTful
- Administrator login: `POST "/api/login"`
- Administrator register: `POST "/api/register"`

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
The middleware provide authentication using jwt access token, and user encryption and serves as the intermediary for communication between the applications and the database. It provides RESTful endpoints to manage operations related to doctors, patients, and medical records. Key functions/features include:

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

### Test User Data: 
- username : test
- password : test1234

### Youtube Link: https://youtu.be/FlEpclmWTUI?si=7c638x7DZ-ubdLY3
