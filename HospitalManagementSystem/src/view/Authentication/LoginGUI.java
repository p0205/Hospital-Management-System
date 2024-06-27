package view.Authentication;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import view.MenuGUI;

public class LoginGUI extends JFrame{
    private JLabel labelUsername;
    private JLabel labelPassword;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JButton buttonLogin;
    private String accessToken;

    public LoginGUI(){
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setTitle("Medical Application");
        setMinimumSize(new Dimension(450, 250));
        setBounds(100, 100, 400, 300);

        labelUsername = new JLabel("Username");
        labelUsername.setBounds(50, 50, 100, 25);
        getContentPane().add(labelUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(150, 50, 150, 25);
        getContentPane().add(textFieldUsername);

        labelPassword = new JLabel("Password");
        labelPassword.setBounds(50, 100, 100, 25);
        getContentPane().add(labelPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 150, 25);
        getContentPane().add(passwordField);

        buttonLogin = new JButton("Login");
        buttonLogin.setBounds(150, 150, 100, 25);
        getContentPane().add(buttonLogin);

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = new String(passwordField.getPassword());
                try {
                    login(username, password);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginGUI.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void login(String username, String password) throws Exception {
        String apiUrl = "http://localhost:5000/api/login"; // Replace with your API URL
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        JSONObject jsonParams = new JSONObject();
		jsonParams.put("username", username);
		jsonParams.put("password", password);

        String jsonInputString = jsonParams.toString();

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int code = connection.getResponseCode();
        if (code == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            JSONObject jsonResponse = new JSONObject(response.toString());
            accessToken = jsonResponse.getString("access_token");
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            getContentPane().setVisible(false);

            MenuGUI menuGUI = new MenuGUI(accessToken);
            menuGUI.setVisible(true);
        } else {
            
            JOptionPane.showMessageDialog(this, "Login failed. Status code: " + code, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
