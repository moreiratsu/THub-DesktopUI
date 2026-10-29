package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alvaro.thub.desktop.views.TeamView;
import com.alvaro.thub.model.LocalityDTO;
import com.alvaro.thub.model.Sport;
import com.alvaro.thub.model.TeamDTO;
import com.alvaro.thub.service.TeamService;
import com.alvaro.thub.service.impl.TeamServiceImpl;

/**
 * Controlador para manejar la CREACION DE UN NUEVO EQUIPO.
 * 
 * Este controlador se encarga de recoger los datos del formulario, 
 * llamar al servicio para crear el equipo y manejar la respuesta.
 */

public class TeamCreateController extends AbstractController {

	private TeamView view = null;
	
	private TeamService teamService = null;
	
	public TeamCreateController(TeamView view) {
		super(view, "Guardar", new ImageIcon(TeamSearchController.class.getResource("/nuvola/guardarx16.png")) ); 			
		this.view = view;
		this.teamService = new TeamServiceImpl();
	}
	
	public void doAction() {
			TeamDTO team = view.getModel();
			
			Long teamId = teamService.create(team);
			
			if (teamId != null) {
				JOptionPane.showMessageDialog(view, 
						"Equipo creado correctamente con ID: " + teamId, 
						"Éxito", JOptionPane.INFORMATION_MESSAGE);
				
				view.setEditable(false);
				view.setAgreeController(new TeamSetEditableController(view));
				
				// Opcional: limpiar el formulario
				// limpiarFormulario();
			} else {
				JOptionPane.showMessageDialog(view, 
						"Error al crear el equipo", 
						"Error", JOptionPane.ERROR_MESSAGE);
			}   			
		}
}
