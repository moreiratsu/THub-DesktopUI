package com.alvaro.thub.desktop.controller;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alvaro.thub.desktop.views.TeamCreateView;
import com.alvaro.thub.model.ContentData;
import com.alvaro.thub.model.ContentMetadata;
import com.alvaro.thub.model.TeamDTO;
import com.alvaro.thub.service.ContentService;
import com.alvaro.thub.service.TeamService;
import com.alvaro.thub.service.impl.ContentServiceFileImpl;
import com.alvaro.thub.service.impl.TeamServiceImpl;

/**
 * Controlador para ACTUALIZAR un equipo existente.
 * 
 * Este controlador se encarga de tomar los datos del formulario de edición, validar la información,
 * llamar al servicio para actualizar el equipo en la base de datos y gestionar la actualización de su logo en el CMS.
 */
public class TeamUpdateController extends AbstractController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TeamCreateView view = null;
	private TeamService teamService = null;
	private ContentService contentService = null;

	public TeamUpdateController(TeamCreateView view) {
		super(view, "Actualizar", new ImageIcon(TeamSearchController.class.getResource("/nuvola/actualizarx16.png"))); 		
		this.view = view;
		this.teamService = new TeamServiceImpl();
		this.contentService = new ContentServiceFileImpl();
	}

	@Override
	public void doAction() {
		TeamDTO team = view.getModel();

		boolean updated = teamService.update(team);
		
		if (updated) {
			// Comprobamos si el usuario ha seleccionado una nueva imagen
			byte[] logoBytes = view.getSelectedLogoBytes();
			String extension = view.getSelectedLogoExtension();

			if (logoBytes != null && logoBytes.length > 0) {
				try {
					// 1. Borrar posibles logos anteriores para evitar conflictos de extensiones (.png vs .jpg)
					String[] formatosPosibles = {".png", ".jpg", ".jpeg"};
					for (String fmt : formatosPosibles) {
						ContentMetadata oldMeta = new ContentMetadata();
						oldMeta.setEntityType("TEAM");
						oldMeta.setEntityId(String.valueOf(team.getId()));
						oldMeta.setResourceId("logo");
						oldMeta.setFormat(fmt);
						
						try {
							contentService.remove(oldMeta);
						} catch (Exception e) {
							// Ignoramos si la extensión específica no existía
						}
					}

					// 2. Crear los metadatos y guardar la nueva imagen
					ContentMetadata metadata = new ContentMetadata();
					metadata.setEntityType("TEAM");
					metadata.setEntityId(String.valueOf(team.getId()));
					metadata.setResourceId("logo");
					metadata.setFormat(extension != null ? extension : ".png");

					ContentData contentData = new ContentData();
					contentData.setMetadata(metadata);
					contentData.setRaw(logoBytes);

					contentService.save(contentData);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(view,
							"Equipo actualizado, pero hubo un error al guardar el nuevo logo: " + e.getMessage(),
							"Aviso CMS", JOptionPane.WARNING_MESSAGE);
				}
			}

			JOptionPane.showMessageDialog(view, 
					"Equipo " + team.getName() + " actualizado correctamente.", 
					"Éxito", JOptionPane.INFORMATION_MESSAGE);
			
			// Refrescar la vista con los datos persistidos y bloquear edición
			view.setLogoData(null, null, null); // Resetea el buffer temporal de la vista
			view.setModel(team);                 // Llama a updateView() para recargar la imagen desde disco
			view.setEditable(false);
			view.setAgreeController(new TeamSetEditableController(view));
			
		} else {
			JOptionPane.showMessageDialog(view, 
					"Error al actualizar el equipo", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}   		
	}
}