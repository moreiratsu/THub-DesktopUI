package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.alvaro.thub.dao.criteria.TeamCriteria;
import com.alvaro.thub.desktop.views.TeamSearchView;
import com.alvaro.thub.model.TeamDTO;
import com.alvaro.thub.service.TeamService;
import com.alvaro.thub.service.impl.TeamServiceImpl;
import com.alvaro.thub.utils.Results;

/**
 * Controlador para manejar la búsqueda de equipos.
 * 
 * Este controlador se encarga de recoger los criterios de búsqueda del formulario, 
 * llamar al servicio para obtener los resultados y actualizar la vista con los resultados encontrados.
 * Además, se activa cada vez que el usuario escribe en los campos de búsqueda o cambia alguna opción, para mostrar resultados en tiempo real.
 */

public class TeamSearchController extends AbstractController {

	

	private TeamService teamService = null;

	private TeamSearchView view = null;

	public TeamSearchController(TeamSearchView view) {
		super("Buscar", new ImageIcon(TeamSearchController.class.getResource("/nuvola/lupa16.png")));
		this.view = view;
		this.teamService  = new TeamServiceImpl();
	}
	
	public void doAction() {
		TeamCriteria criteria = view.getCriteria();
		Results<TeamDTO> resultados = teamService.findByCriteria(criteria, 1, Integer.MAX_VALUE);
		view.setModel(resultados);
	}
	

}
