package com.alvaro.thub.desktop.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import com.alvaro.thub.desktop.MainWindow;

/**
 * Controlador para abrir el menú de opciones relacionadas con los equipos.
 * 
 * Este controlador se encarga de mostrar un menú desplegable con opciones para buscar o crear equipos cuando el usuario hace clic en el botón correspondiente en la barra de herramientas.
 */

public class OpenTeamMenuController extends AbstractController{

	private Component parent = null;

	public OpenTeamMenuController(Component parent) {
		this.parent = parent;		
	}

	
	public void doAction() {
		// Menú desplegable para Equipos
		JPopupMenu teamPopup = new JPopupMenu();				
		JMenuItem buscarEquipoItem = new JMenuItem("Buscar Equipo");
		buscarEquipoItem.setHorizontalAlignment(SwingConstants.LEFT);
		buscarEquipoItem.addActionListener(new OpenTeamSearchViewController());			
		JMenuItem nuevoEquipoItem = new JMenuItem("Nuevo Equipo");
		nuevoEquipoItem.setHorizontalAlignment(SwingConstants.LEFT);
		nuevoEquipoItem.addActionListener(new OpenTeamCreateViewController());				
		teamPopup.add(buscarEquipoItem);
		teamPopup.add(nuevoEquipoItem);				
		teamPopup.show(parent, 0, parent.getHeight());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();		
	}



}
