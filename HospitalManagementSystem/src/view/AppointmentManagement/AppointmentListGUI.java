package view.AppointmentManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import model.Appointment.Appointment;

public class AppointmentListGUI extends JFrame {

    private String column[] = {"ID", "Appointment", "Action"};
    private JTable jt;
    private Object data[][];
    Appointment list[] = new Appointment[10];

    public AppointmentListGUI() {   
        data = loadData(list);
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
                        System.out.println("Edit button clicked for row " + rowIndex);

                        list[rowIndex].printAppointment();
                    }
                });

                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle delete action
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



    public static Object[][] loadData(Appointment list[]){
        
        Appointment appointment_1 = new Appointment("1", "Patient_1", "10:00 AM", "11:00 AM", "2021-09-01", "Doctor_1", "Checkup");
        Appointment appointment_2 = new Appointment("2", "Patient_2", "11:00 AM", "12:00 PM", "2021-09-02", "Doctor_2", "Consultation");
        Appointment appointment_3 = new Appointment("3", "Patient_3", "12:00 PM", "01:00 PM", "2021-09-03", "Doctor_3", "Follow-up");
        Appointment appointment_4 = new Appointment("4", "Patient_4", "01:00 PM", "02:00 PM", "2021-09-04", "Doctor_4", "Treatment");
        Appointment appointment_5 = new Appointment("5", "Patient_5", "02:00 PM", "03:00 PM", "2021-09-05", "Doctor_5", "Surgery");
        Appointment appointment_6 = new Appointment("6", "Patient_6", "03:00 PM", "04:00 PM", "2021-09-06", "Doctor_6", "Diagnosis");
        Appointment appointment_7 = new Appointment("7", "Patient_7", "04:00 PM", "05:00 PM", "2021-09-07", "Doctor_7", "Prescription");
        Appointment appointment_8 = new Appointment("8", "Patient_8", "05:00 PM", "06:00 PM", "2021-09-08", "Doctor_8", "Therapy");
        Appointment appointment_9 = new Appointment("9", "Patient_9", "06:00 PM", "07:00 PM", "2021-09-09", "Doctor_9", "Counseling");
        Appointment appointment_10 = new Appointment("10", "Patient_10", "07:00 PM", "08:00 PM", "2021-09-10", "Doctor_10", "Rehabilitation");
        list[0] = appointment_1;
        list[1] = appointment_2;
        list[2] = appointment_3;
        list[3] = appointment_4;
        list[4] = appointment_5;
        list[5] = appointment_6;
        list[6] = appointment_7;
        list[7] = appointment_8;
        list[8] = appointment_9;
        list[9] = appointment_10;
            
        Object data[][] = new Object[10][3];
    
        for (int i = 0; i < 10; i++) {
            data[i][0] = i;
            data[i][1] = "Appointment_" + list[i].getId();
            data[i][2] = new Panel();
        }
        return data;
    }
}
