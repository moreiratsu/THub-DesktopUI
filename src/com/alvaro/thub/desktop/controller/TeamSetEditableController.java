package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.alvaro.thub.desktop.views.TeamCreateView;

/**
 * Este controlador se encarga de cambiar la vista del equipo a modo editable.
 * 
 * permitiendo al usuario modificar los datos del equipo. Además, asigna un nuevo controlador para manejar la acción de guardar
 * los cambios realizados.
 */

public class TeamSetEditableController extends AbstractController {

	private TeamCreateView view = null;

	public TeamSetEditableController(TeamCreateView view) {
		super(view, "Editar", new ImageIcon(TeamSearchController.class.getResource("/nuvola/editarx16.png")) ); 	
	}

	public void doAction() {
		TeamCreateView view = (TeamCreateView) getView();
		view.setEditable(true);
		view.setAgreeController(new TeamUpdateController(view));
	}

}
