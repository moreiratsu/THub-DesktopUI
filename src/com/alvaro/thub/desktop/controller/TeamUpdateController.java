package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alvaro.thub.desktop.views.TeamView;
import com.alvaro.thub.model.TeamDTO;
import com.alvaro.thub.service.TeamService;
import com.alvaro.thub.service.impl.TeamServiceImpl;

/**
 * Controlador para ACTUALIZAR un equipo existente.
 * 
 * Este controlador se encarga de tomar los datos del formulario de edición, validar la información y llamar al servicio para actualizar el equipo en la base de datos.
 * Después de la actualización, muestra un mensaje de éxito o error según corresponda.
 */

public class TeamUpdateController extends AbstractController {
	
	private TeamView view = null;

	private TeamService teamService = null;

	public TeamUpdateController(TeamView view) {
		super(view, "Actualizar", new ImageIcon(TeamSearchController.class.getResource("/nuvola/actualizarx16.png")) ); 		
		this.view = view;
		this.teamService = new TeamServiceImpl();
	}

	public void doAction() {
		TeamDTO team = view.getModel();

		boolean updated = teamService.update(team);
		
		if (updated) {
			JOptionPane.showMessageDialog(view, 
					"Equipo"+team.getName() +" actualizado correctamente.", 
					"Éxito", JOptionPane.INFORMATION_MESSAGE);
			
			view.setEditable(false);
			view.setAgreeController(new TeamSetEditableController(view));
			
			// Opcional: limpiar el formulario
			// limpiarFormulario();
		} else {
			JOptionPane.showMessageDialog(view, 
					"Error al actualizar el equipo", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}   		
	}

}
