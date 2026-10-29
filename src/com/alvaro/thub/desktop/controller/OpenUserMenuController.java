package com.alvaro.thub.desktop.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.UserSearchView;

/*
 * Controlador para abrir el menú desplegable de Usuario, con opciones para buscar y crear usuarios.
 * Ya que en mi aplicacion, el menú de Usuario no es un menú fijo en la barra de menú, sino un botón que despliega un menú popup,
 *  este controlador se encarga de mostrar ese menú cuando se hace clic en el botón.
 */

public class OpenUserMenuController extends AbstractController {

	private Component parent = null;
	
	// TODO: preguntar al profe como quitar MainWindow con Singleton
	// TODO: Que no haya que crear el popup cada vez, sino solo una vez! 
	
	public OpenUserMenuController( Component parent) {
		this.parent = parent;		
	}	

	public void doAction() {
		// Menú desplegable para Usuario
		JPopupMenu userPopup = new JPopupMenu();
		
		JMenuItem buscarUsuarioItem = new JMenuItem("Buscar Usuario");
		buscarUsuarioItem.addActionListener(new OpenUserSearchViewController());
		
		JMenuItem nuevoUsuarioItem = new JMenuItem("Nuevo Usuario");
		nuevoUsuarioItem.addActionListener(new OpenUserCreateViewController());
		
		// Agregar los items al menú popup
		userPopup.add(buscarUsuarioItem);
		userPopup.add(nuevoUsuarioItem);
		// Mostrar el menú popup 
		userPopup.show(parent, 0, parent.getHeight());
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();
	}


}
