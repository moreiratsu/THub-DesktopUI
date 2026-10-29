package com.alvaro.thub.desktop.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import com.alvaro.thub.desktop.MainWindow;

/**
 * Controlador para abrir el menú de opciones relacionadas con los eventos.
 * 
 * Este controlador se encarga de mostrar un menú desplegable con opciones para buscar o crear eventos cuando el usuario hace clic en el botón correspondiente en la barra de herramientas.
 */

public class OpenEventMenuController extends AbstractController {

	// aqui declaro el parent, para poder usarlo
	private Component parent = null;

	// en el constructor le paso el parent, para poder usarlo en el actionPerformed
	public OpenEventMenuController(Component parent) {
		this.parent = parent;		
	}

	@Override
	public void doAction() {
		// Aquí ponemos el codigo de main window que hace la accion de abrir el popup menu de evento, con las opciones de buscar y crear eventos
		JPopupMenu eventPopup = new JPopupMenu();

		JMenuItem buscarEventoMenuItem = new JMenuItem("Buscar Evento");			
		buscarEventoMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		buscarEventoMenuItem.addActionListener(new OpenEventSearchViewController());

		JMenuItem nuevoEventoMenuItem = new JMenuItem("Nuevo Evento");
		nuevoEventoMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		nuevoEventoMenuItem.addActionListener(new OpenEventCreateViewController());
		
		eventPopup.add(buscarEventoMenuItem);
		eventPopup.add(nuevoEventoMenuItem);
		eventPopup.show(parent, 0, parent.getHeight());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();}
}
