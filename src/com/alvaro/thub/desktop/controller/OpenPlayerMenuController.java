package com.alvaro.thub.desktop.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import com.alvaro.thub.desktop.MainWindow;

public class OpenPlayerMenuController extends AbstractController{

	private Component parent = null;

	public OpenPlayerMenuController(Component parent) {	
		this.parent = parent;
	}

	public void doAction() {
		// Menú desplegable para Participantes
		JPopupMenu playersPopup = new JPopupMenu();
		JMenuItem buscarParticipanteItem = new JMenuItem("Buscar Participante");
		buscarParticipanteItem.setHorizontalAlignment(SwingConstants.LEFT);
		buscarParticipanteItem.addActionListener(new OpenPlayerSearchViewController());
		JMenuItem nuevoParticipanteItem = new JMenuItem("Nuevo Participante");
		nuevoParticipanteItem.setHorizontalAlignment(SwingConstants.LEFT);
		nuevoParticipanteItem.addActionListener(new OpenPlayerCreateViewController());
		playersPopup.add(buscarParticipanteItem);
		playersPopup.add(nuevoParticipanteItem);
		playersPopup.show(parent, 0, parent.getHeight());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		doAction();
	}

}
