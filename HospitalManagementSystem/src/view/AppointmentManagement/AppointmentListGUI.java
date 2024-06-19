package view.AppointmentManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.http.HttpStatus;
import org.json.JSONArray;

import controller.MakeHttpRequest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.http.HttpResponse;

import model.Appointment.Appointment;

public class AppointmentListGUI extends JFrame {

    private String column[] = {"ID", "Appointment", "Action"};
    private JTable jt;
    private Object data[][];
    Appointment list[] = new Appointment[10];
    private MakeHttpRequest req;

    public AppointmentListGUI() {
        req = new MakeHttpRequest();
        JSONArray jsonList = loadAppointment();   
        list = loadAppointment(jsonList);
        for (Appointment a: list) {
            a.printAppointment();
        }
        data = loadRow(list);
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setTitle("Appointment Application");
        setMinimumSize(new Dimension(450, 250));
        setBounds(100, 100, 600, 400);

        JPanel centerPane = new JPanel();
        centerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        jt = new JTable(new AppointmentTableModel());
        jt.setRowHeight(40);  // Set the row height to ensure enough space for the buttons
        jt.getColumn("Action").setCellRenderer(new ButtonRenderer());
        jt.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane sp = new JScrollPane(jt);
        centerPane.add(sp);

        getContentPane().add(centerPane, BorderLayout.CENTER);
        setVisible(true);
    }

    // Load icon from file system
    private ImageIcon loadIcon(String fullPath, int width, int height) {
        File file = new File(fullPath);
        if (file.exists()) {
            ImageIcon icon = new ImageIcon(fullPath);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } else {
            System.err.println("Resource not found: " + fullPath);
            return new ImageIcon(); // Return an empty icon or a default one if preferred
        }
    }

    // dunno how these work
    // dont touch from here pls
    class AppointmentTableModel extends AbstractTableModel {
        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return column.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 2) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
                
                // Change this to the full path of your resources
                String basePath = "C:/Users/User/Desktop/java/dadproj/HospitalManagementSystem/src/resources/";  
                int buttonSize = 30;
                JButton editButton = new JButton(loadIcon(basePath + "edit.png", buttonSize, buttonSize));
                JButton deleteButton = new JButton(loadIcon(basePath + "delete.png", buttonSize, buttonSize));
                JButton viewButton = new JButton(loadIcon(basePath + "view.png", buttonSize, buttonSize));

                editButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
                deleteButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
                viewButton.setPreferredSize(new Dimension(buttonSize, buttonSize));

                panel.add(editButton);
                panel.add(deleteButton);
                panel.add(viewButton);

                editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle edit action
                        EditAppointmentGUI editAppointment = new EditAppointmentGUI(list[rowIndex].getId());
                        editAppointment.setVisible(true);

                        //list[rowIndex].printAppointment();
                    }
                });

                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle delete action
                        HttpResponse <String> response = req.makeHttpRequest(("http://127.0.0.1:3000/api/appointments/" + list[rowIndex].getId()), "DELETE", null);
                        if(response.statusCode()==HttpStatus.SC_OK)
                        {
                            JOptionPane.showMessageDialog(null,"The patient is deleted successfully!");
                        }else
                        {
                            JOptionPane.showMessageDialog(null,"Something went wrong...");
                        }
                        System.out.println("Delete button clicked for row " + rowIndex);
                        // Remove the row from data and notify table model of the change
                        Object[][] newData = new Object[data.length - 1][2];
                        for (int i = 0, j = 0; i < data.length; i++) {
                            if (i != rowIndex) {
                                newData[j++] = data[i];
                            }
                        }
                        data = newData;
                        fireTableRowsDeleted(rowIndex, rowIndex);
                    }
                });

                viewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle view action
                        System.out.println("View button clicked for row " + rowIndex);
                    
                        list[rowIndex].printAppointment();
                    }
                });

                return panel;
            }
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return AppointmentListGUI.this.column[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 2;
        }
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JPanel) {
                return (JPanel) value;
            }
            return new JPanel();
        }
    }
    class ButtonEditor extends DefaultCellEditor {

        private JPanel panel;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value instanceof JPanel) {
                panel = (JPanel) value;
                return panel;
            }
            return new JPanel();
        }

        @Override
        public Object getCellEditorValue() {
            return panel;
        }
    }
    // to here

    //load appointment from server
    private JSONArray loadAppointment()
	 {
		 HttpResponse<String> response = req.makeHttpRequest("http://127.0.0.1:3000/api/appointments/", "GET", null);	
		 if(response.statusCode()== HttpStatus.SC_OK)
			 return new JSONArray(response.body());
		 return new JSONArray();
	 }

     //load JSON list into model 
    public static Appointment[] loadAppointment(JSONArray jsonList) {
        Appointment list[] = new Appointment[jsonList.length()];
        for (int i = 0; i < jsonList.length(); i++) {
            list[i] = new Appointment(Integer.toString(jsonList.getJSONObject(i).getInt("id")), Integer.toString(jsonList.getJSONObject(i).getInt("patientID")), jsonList.getJSONObject(i).getString("startTime"), jsonList.getJSONObject(i).getString("endTime"), jsonList.getJSONObject(i).getString("date"), Integer.toString(jsonList.getJSONObject(i).getInt("doctorID")), jsonList.getJSONObject(i).getString("purpose"));
        }
        return list;
    }


    //  load data from model into table
    public static Object[][] loadRow(Appointment list[]){
    
        Object data[][] = new Object[list.length][3];
    
        for (int i = 0; i < list.length; i++) {
            data[i][0] = i+1;
            data[i][1] = "Appointment_" + list[i].getId();
            data[i][2] = new Panel();
        }
        return data;
    }
}
