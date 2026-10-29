package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alvaro.thub.desktop.views.EventView;
import com.alvaro.thub.model.Event;
import com.alvaro.thub.service.EventService;
import com.alvaro.thub.service.impl.EventServiceImpl;

public class EventUpdateController extends AbstractController {

    private EventView view = null;
    private EventService eventService = null;

    public EventUpdateController(EventView view) {
        super(view, "Actualizar", new ImageIcon(EventSearchController.class.getResource("/nuvola/actualizarx16.png")));
        this.view = view;
        this.eventService = new EventServiceImpl();
    }

    public void doAction() {
        // Verificar que la vista tenga un modelo
        if (view.getModel() == null) {
            JOptionPane.showMessageDialog(view, "Error: No hay datos para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Event event = view.getModel();
        
        // Obtener el ID del evento que se está editando
        Long eventId = view.getCurrentEventId();
        if (eventId == null) {
            JOptionPane.showMessageDialog(view, "Error: El evento no tiene ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        event.setId(eventId);

        boolean updated = eventService.update(event);

        if (updated) {
            JOptionPane.showMessageDialog(view, 
                    "Evento " + event.getName() + " actualizado correctamente.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            view.setEditable(false);
            view.setAgreeController(new EventSetEditableController(view));
        } else {
            JOptionPane.showMessageDialog(view, 
                    "Error al actualizar el evento", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}