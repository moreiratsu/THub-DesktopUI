package com.alvaro.thub.desktop;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXTitledPanel;

import com.alvaro.thub.desktop.controller.OpenUserMenuController;
import com.alvaro.thub.desktop.controller.OpenEventMenuController;
import com.alvaro.thub.desktop.controller.OpenEventSearchViewController;
import com.alvaro.thub.desktop.controller.OpenHomeViewController;
import com.alvaro.thub.desktop.controller.OpenPlayerMenuController;
import com.alvaro.thub.desktop.controller.OpenStatsMenuController;
import com.alvaro.thub.desktop.controller.OpenTeamMenuController;
import com.alvaro.thub.desktop.views.EventView;
import com.alvaro.thub.desktop.views.EventSearchView;
import com.alvaro.thub.desktop.views.HomeView;
import com.alvaro.thub.desktop.views.ParticipantsByAgeChartView;
import com.alvaro.thub.desktop.views.PlayerView;
import com.alvaro.thub.desktop.views.PlayerSearchView;
import com.alvaro.thub.desktop.views.TeamSearchView;
import com.alvaro.thub.desktop.views.TeamView;
import com.alvaro.thub.desktop.views.UserView;
import com.alvaro.thub.desktop.views.View;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.alvaro.thub.desktop.views.UserSearchView;

/**
 * Ventana principal de la aplicación.
 * Implementada con el patrón Singleton.
 */
public class MainWindow {

	// Instancia Única de MainWindow para controladores
	private static MainWindow instance = null;

	private JFrame frame;
	private JTabbedPane contentTabbedPane;
	private JPanel centerPanel;
	private JButton eventButton;
	private AbstractButton buscarEventoMenuItem;
	private JButton userButton;
	private JButton homeButton;
	private JButton teamButton;
	private JButton playersButton;
	private JButton statisticsButton;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/**
					 * FlatLaf Light (clase com.formdev.flatlaf.FlatLightLaf)
					 * FlatLaf Oscuro (clase com.formdev.flatlaf.FlatDarkLaf)
					 * FlatLaf IntelliJ (basado en FlatLaf Light) se parece al tema de IntelliJ de IntelliJ IDEA 2019.2+ (clase com.formdev.flatlaf.FlatIntelliJLaf)
					 * FlatLaf Darcula (basado en FlatLaf Dark) se parece al tema Darcula de IntelliJ IDEA 2019.2+ (clase com.formdev.flatlaf.FlatDarculaLaf)
					 * FlatLaf macOS Light desde v3 (clase com.formdev.flatlaf.themes.FlatMacLightLaf)
					 * FlatLaf macOS Oscuro desde v3 (clase com.formdev.flatlaf.themes.FlatMacDarkLaf)
					 */
					UIManager.setLookAndFeel(new FlatDarculaLaf()); 
					getInstance().frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private MainWindow() {
		initialize();
		postInitialize();
	}

	/*
	 * Método para obtener la instancia única de MainWindow (Singleton)
	 */
	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}
		return instance;
	}

	/**
	 * Contenido del frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));

		JPanel northPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) northPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		mainPanel.add(northPanel, BorderLayout.NORTH);

		centerPanel = new JPanel();
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		contentTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		centerPanel.add(contentTabbedPane);

		JPanel southPanel = new JPanel();
		mainPanel.add(southPanel, BorderLayout.SOUTH);

		JPanel westPanel = new JPanel();
		mainPanel.add(westPanel, BorderLayout.WEST);

		JPanel menuPanel = new JPanel();
		westPanel.add(menuPanel);
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

		// INICIO 
		homeButton = new JButton("Inicio");	
		homeButton.setMaximumSize(new Dimension(150, 30));
		homeButton.setIcon(new ImageIcon(MainWindow.class.getResource("/nuvola/32x32/home.png")));
		homeButton.setHorizontalAlignment(SwingConstants.LEFT);
		homeButton.setAlignmentX(0.0f);
		menuPanel.add(homeButton);
		Component rigidArea = Box.createRigidArea(new Dimension(0, 5));
		menuPanel.add(rigidArea);

		// USUARIO 
		userButton = new JButton("Usuario");
		userButton.setIcon(new ImageIcon(MainWindow.class.getResource("/nuvola/32x32/user.png")));
		userButton.setHorizontalAlignment(SwingConstants.LEFT);
		userButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		userButton.setMaximumSize(new Dimension(150, 30));
		menuPanel.add(userButton);
		menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		// EVENTOS
		eventButton = new JButton("Eventos");
		eventButton.setIcon(new ImageIcon(MainWindow.class.getResource("/nuvola/32x32/eventos.png")));
		eventButton.setHorizontalAlignment(SwingConstants.LEFT);
		eventButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		eventButton.setMaximumSize(new Dimension(150, 30));
		menuPanel.add(eventButton);
		menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		// EQUIPOS
		teamButton = new JButton("Equipos");
		teamButton.setIcon(new ImageIcon(MainWindow.class.getResource("/nuvola/32x32/escudo.png")));
		teamButton.setHorizontalAlignment(SwingConstants.LEFT);
		teamButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		teamButton.setMaximumSize(new Dimension(150, 30));		
		menuPanel.add(teamButton);
		menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		// PARTICIPANTES
		playersButton = new JButton("Participantes");
		playersButton.setIcon(new ImageIcon(MainWindow.class.getResource("/nuvola/32x32/players.png")));
		playersButton.setHorizontalAlignment(SwingConstants.LEFT);
		playersButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		playersButton.setMaximumSize(new Dimension(150, 30));
		menuPanel.add(playersButton);

		// ESTADÍSTICAS
		statisticsButton = new JButton("Estadísticas");
		statisticsButton.setIcon(new ImageIcon(MainWindow.class.getResource("/nuvola/32x32/stats.png")));
		statisticsButton.setHorizontalAlignment(SwingConstants.LEFT);
		statisticsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		statisticsButton.setMaximumSize(new Dimension(150, 30));		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(0, 5));
		menuPanel.add(rigidArea_1);
		menuPanel.add(statisticsButton);
		menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));



		JPanel eastPanel = new JPanel();
		mainPanel.add(eastPanel, BorderLayout.EAST);

	}

	/**
	 * Método para añadir pestaña con botón de cerrar con icono
	 */
	public void addClosableView(String title, Component component) {

		// Crear panel para el título de la pestaña
		JPanel tabTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		tabTitlePanel.setOpaque(false);
		// Etiqueta con el título
		JLabel titleLabel = new JLabel(title);
		// Botón de cerrar con icono
		JButton closeButton = new JButton();
		// Intentar cargar icono personalizado para cerrar, si no se encuentra, usar texto "X"
		ImageIcon closeIcon = null;

		try {
			closeIcon = new ImageIcon(MainWindow.class.getResource("/nuvola/16x16/close.png"));
		} catch (Exception e) {
			closeButton.setText("✕");
			closeButton.setFont(closeButton.getFont().deriveFont(10f));
		}
		if (closeIcon != null) {
			closeButton.setIcon(closeIcon);
		}
		// Configurar el botón
		closeButton.setPreferredSize(new Dimension(16, 16));
		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.setBorderPainted(false);
		closeButton.setContentAreaFilled(false);
		closeButton.setFocusPainted(false);
		closeButton.setRolloverEnabled(true);
		closeButton.setToolTipText("Cerrar pestaña");
		// Acción al hacer clic en el botón de cerrar
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = contentTabbedPane.indexOfComponent(component);
				if (index != -1) {
					contentTabbedPane.remove(index);
				}
			}
		});
		// Añadir componentes al panel del título
		tabTitlePanel.add(titleLabel);
		tabTitlePanel.add(Box.createHorizontalStrut(3));
		tabTitlePanel.add(closeButton);
		// Añadir la pestaña al tabbedPane
		contentTabbedPane.addTab(title, component);
		// Obtener el índice de la pestaña recién añadida
		int tabIndex = contentTabbedPane.indexOfComponent(component);
		// Establecer el panel personalizado como componente de la pestaña
		contentTabbedPane.setTabComponentAt(tabIndex, tabTitlePanel);
		// Seleccionar la nueva pestaña
		contentTabbedPane.setSelectedIndex(tabIndex);
		// Forzar actualización
		contentTabbedPane.revalidate();
		contentTabbedPane.repaint();
		centerPanel.revalidate();
		centerPanel.repaint();
	}

	public void remove(View view) {
		contentTabbedPane.remove((Component) view);
	}

	private void postInitialize() {
		// Enganchamos controladores	
		homeButton.addActionListener(new OpenHomeViewController());
		userButton.addActionListener(new OpenUserMenuController(userButton));	
		eventButton.addActionListener(new OpenEventMenuController(eventButton));
		teamButton.addActionListener(new OpenTeamMenuController(teamButton));
		playersButton.addActionListener(new OpenPlayerMenuController(playersButton));
		statisticsButton.addActionListener(new OpenStatsMenuController(statisticsButton));}
}