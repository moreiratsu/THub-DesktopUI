package com.alvaro.thub.desktop.views;

import java.awt.EventQueue;

import javax.swing.JFrame;

// al ejecutar esta vista podemos ver un ejemplo de combobox con autocompletado usando la librería SwingX. Es una vista de prueba para comprobar que la integración de SwingX funciona correctamente en nuestro proyecto.

public class SwingXTestWindow {
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingXTestWindow window = new SwingXTestWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingXTestWindow() {
		initialize();
		postInitialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void postInitialize() {
		frame.add(new SwingXTestView());
	}
	
}
