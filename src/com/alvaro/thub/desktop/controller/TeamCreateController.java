package com.alvaro.thub.desktop.controller;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alvaro.thub.desktop.views.TeamCreateView;
import com.alvaro.thub.model.TeamDTO;
import com.alvaro.thub.service.ContentService;
import com.alvaro.thub.service.TeamService;
import com.alvaro.thub.service.impl.ContentServiceFileImpl;
import com.alvaro.thub.service.impl.TeamServiceImpl;
import com.alvaro.thub.utils.FileUtils;

/**
 * Controlador para manejar la CREACION DE UN NUEVO EQUIPO.
 * 
 * Este controlador se encarga de recoger los datos del formulario, 
 * llamar al servicio para crear el equipo y manejar la respuesta.
 */

public class TeamCreateController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TeamCreateView view = null;	
	private TeamService teamService = null;
	private ContentService contentService = null; // PASO 3 ahora he implementado el boton para adjuntar un logo al crear un equipo, entonces en el controlador que se encarga de recibir los datos de la vista con el formulario y guardarlos tengo que añadir el servicio de gestion de contenidos para poder guardar el logo 
	
	public TeamCreateController(TeamCreateView view) {
		super(view, "Guardar", new ImageIcon(TeamSearchController.class.getResource("/nuvola/guardarx16.png")) ); 			
		this.view = view;
		this.teamService = new TeamServiceImpl();
		this.contentService = new ContentServiceFileImpl(); // PASO 3 en el contructor construimos su implementacion
	}
	
	public void doAction() {
			// obtenemos los datos del formulario de la vista de creacion de equipo
			TeamDTO team = view.getModel();
			
			// creamos el equipo en la BD y obtenemos el id que le ha geneado
			Long teamId = teamService.create(team);
			
			// si existe
			if (teamId != null) {
				// comprobamos si adjuntaron un logo ya que pueden no hacerlo con el metodo especial creado en FileUtils del middleware
				try {
					FileUtils.saveOrUpdateResource(contentService, "TEAM", String.valueOf(teamId), "logo", view.getSelectedLogoBytes(), view.getSelectedLogoExtension());
				} catch (IOException e) {
					throw new RuntimeException("Error al guardar el logo del equipo", e);
				}
				
				
				
				
				JOptionPane.showMessageDialog(view, 
						"Equipo creado correctamente con ID: " + teamId, 
						"Éxito", JOptionPane.INFORMATION_MESSAGE);
				
				view.setLogoData(null, null, null);
			    view.setModel(team);
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
