package view.AppointmentManagement;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.net.http.HttpResponse;

import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.http.HttpStatus;
import org.json.JSONArray;

import controller.MakeHttpRequest;
import model.Appointment.Appointment;
import view.MenuGUI;

public class AppointmentListGUI extends JFrame {

    private final String column[] = {"ID", "Appointment", "Action"};
    private JTable jt;
    private Object data[][];
    Appointment list[] = new Appointment[10];
    private final MakeHttpRequest req;
    private final String accessToken;

    public AppointmentListGUI(String accessToken) {
        this.accessToken = accessToken;
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

        JPanel northPane = new JPanel();
        northPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel appointmentLabel = new JLabel("Appointment List");
        appointmentLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnBack = new JButton("");
		btnBack.addActionListener((ActionEvent e) -> {
                    dispose();
                    MenuGUI appGUI = new MenuGUI(accessToken);
                    appGUI.setVisible(true);
        });
		ImageIcon backImage = createResizedIcon("resources/BackButton.png", 25, 25);
		btnBack.setIcon(backImage);
        northPane.add(btnBack);
        northPane.add(Box.createRigidArea(new Dimension(50, 0)));
        northPane.add(appointmentLabel);

        JPanel centerPane = new JPanel();
        centerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        jt = new JTable(new AppointmentTableModel());
        jt.setRowHeight(40);  // Set the row height to ensure enough space for the buttons
        jt.getColumn("Action").setCellRenderer(new ButtonRenderer());
        jt.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane sp = new JScrollPane(jt);
        centerPane.add(sp);

        JPanel southPane = new JPanel();
        southPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Add Appointment");
        btnAdd.addActionListener((ActionEvent e) -> {
            getContentPane().setVisible(false);
            AddAppointmentGUI addAppointment = new AddAppointmentGUI(accessToken);
            addAppointment.setVisible(true);
        });
        southPane.add(btnAdd);

        getContentPane().add(northPane, BorderLayout.NORTH);
        getContentPane().add(centerPane, BorderLayout.CENTER);
        getContentPane().add(southPane, BorderLayout.SOUTH);
        setVisible(true);
    }

    private ImageIcon createResizedIcon(String imagePath, int width, int height) {
        URL resourceUrl = getClass().getClassLoader().getResource(imagePath);
        if (resourceUrl != null) {
            ImageIcon icon = new ImageIcon(resourceUrl);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } else {
            System.err.println("Resource not found: " + imagePath);
            return new ImageIcon(); // Return an empty icon or a default one if preferred
        }
    }

    // // Load icon from file system
    // private ImageIcon loadIcon(String fullPath, int width, int height) {
    //     File file = new File(fullPath);
    //     if (file.exists()) {
    //         ImageIcon icon = new ImageIcon(fullPath);
    //         Image img = icon.getImage();
    //         Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    //         return new ImageIcon(scaledImg);
    //     } else {
    //         System.err.println("Resource not found: " + fullPath);
    //         return new ImageIcon(); // Return an empty icon or a default one if preferred
    //     }
    // }

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
                // String basePath = "C:\\Users\\User\\Desktop\\java\\dad_proj\\Hospital-Management-System\\HospitalManagementSystem\\src\\resources\\";  
                int buttonSize = 30;
                ImageIcon editImage = createResizedIcon("resources/edit.png", buttonSize, buttonSize);
                JButton editButton = new JButton();
                editButton.setIcon(editImage);
                ImageIcon deleteImage = createResizedIcon("resources/delete.png", buttonSize, buttonSize);
                JButton deleteButton = new JButton();
                deleteButton.setIcon(deleteImage);
                ImageIcon viewImage = createResizedIcon("resources/view.png", buttonSize, buttonSize);
                JButton viewButton = new JButton();
                viewButton.setIcon(viewImage);

                editButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
                deleteButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
                viewButton.setPreferredSize(new Dimension(buttonSize, buttonSize));

                panel.add(editButton);
                panel.add(deleteButton);
                panel.add(viewButton);

                editButton.addActionListener((ActionEvent e) -> {
                    // Handle edit action
                    getContentPane().setVisible(false);
                    EditAppointmentGUI editAppointment = new EditAppointmentGUI(list[rowIndex].getId(),accessToken);
                    editAppointment.setVisible(true);
                });

                deleteButton.addActionListener((ActionEvent e) -> {
                    // Handle delete action
                    HttpResponse <String> response = req.makeHttpRequest(("http://127.0.0.1:5001/api/appointments/" + list[rowIndex].getId()), "DELETE", null, accessToken);
                    if(response.statusCode()==HttpStatus.SC_ACCEPTED)
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
                });

                viewButton.addActionListener((ActionEvent e) -> {
                    // Handle view action
                    getContentPane().setVisible(false);
                    ViewAppointmentGUI viewAppointment = new ViewAppointmentGUI(list[rowIndex].getId(), accessToken);
                    viewAppointment.setVisible(true);
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
            if (value instanceof JPanel jPanel) {
                return jPanel;
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
            if (value instanceof JPanel jPanel) {
                panel = jPanel;
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
		 HttpResponse<String> response = req.makeHttpRequest("http://127.0.0.1:5001/api/appointments/", "GET", null, accessToken);	
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
