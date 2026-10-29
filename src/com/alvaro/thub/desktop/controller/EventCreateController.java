package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alvaro.thub.desktop.views.EventView;
import com.alvaro.thub.desktop.views.EventSearchView;
import com.alvaro.thub.model.Country;
import com.alvaro.thub.model.Event;
import com.alvaro.thub.model.EventDTO;
import com.alvaro.thub.model.Format;
import com.alvaro.thub.model.Status;
import com.alvaro.thub.service.EventService;
import com.alvaro.thub.service.impl.EventServiceImpl;

/**
 * Controlador para manejar la CREACION DE UN NUEVO EVENTO.
 * 
 * Este controlador se encarga de recoger los datos del formulario, 
 * llamar al servicio para crear el evento y manejar la respuesta.
 */

public class EventCreateController extends AbstractController{

	private EventView view = null;

	private EventService eventService = null;

	public EventCreateController(EventView view) {
		super(view, "Guardar", new ImageIcon(EventCreateController.class.getResource("/nuvola/lupa16.png")));
		this.view = view;
		this.eventService = new EventServiceImpl();
	}

	public void doAction() {
	    Event event = view.getModel();  

		EventDTO eventId = eventService.create(event);

		if (eventId != null) {
			JOptionPane.showMessageDialog(view, 
					"Evento creado correctamente con ID: " + eventId, 
					"Éxito", JOptionPane.INFORMATION_MESSAGE);
			view.setEditable(false);
			view.setAgreeController(new EventSetEditableController(view));
			// Opcional: limpiar el formulario aun no implementado
			// limpiarFormulario();
		} else {
			JOptionPane.showMessageDialog(view, 
					"Error al crear el evento", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
