package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.alvaro.thub.dao.criteria.EventCriteria;
import com.alvaro.thub.desktop.views.EventSearchView;
import com.alvaro.thub.desktop.views.UserSearchView;
import com.alvaro.thub.model.EventDTO;
import com.alvaro.thub.service.EventService;
import com.alvaro.thub.service.impl.EventServiceImpl;
import com.alvaro.thub.utils.Results;

/**
 * Controlador para manejar la búsqueda de eventos.
 * 
 * Este controlador se encarga de recoger los criterios de búsqueda del formulario, 
 * llamar al servicio para obtener los resultados y actualizar la vista con los resultados encontrados.
 * Además, se activa cada vez que el usuario escribe en los campos de búsqueda o cambia alguna opción, para mostrar resultados en tiempo real.
 */

public class EventSearchController extends AbstractController {

	private EventSearchView view = null;

	private EventService eventService = null;

	public EventSearchController(EventSearchView view) {
		super("Buscar", new ImageIcon(EventSearchController.class.getResource("/nuvola/lupa16.png")));
		this.view = view;
		this.eventService = new EventServiceImpl();
	}

	public void doAction(){
		EventCriteria criteria = view.getCriteria();	
		Results<EventDTO> resultados = eventService.findBy(criteria, 1, Integer.MAX_VALUE); 
		view.setModel(resultados);
	}

}
