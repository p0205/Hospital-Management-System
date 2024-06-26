package view;

import java.awt.EventQueue;

import view.Authentication.LoginGUI;


public class MainApplicationGUI{

	MainApplicationGUI(){}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI login = new LoginGUI();
					login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
