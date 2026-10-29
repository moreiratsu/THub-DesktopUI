package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.alvaro.thub.desktop.views.EventView;



public class EventSetEditableController extends AbstractController {

	private EventView view = null;
	
	public EventSetEditableController(EventView view) {
		super(view, "Editar", new ImageIcon(EventSearchController.class.getResource("/nuvola/editarx16.png")) ); 			
	}

	@Override
	public void doAction() {
		EventView view = (EventView) getView();
		view.setEditable(true);
		view.setAgreeController(new EventUpdateController(view));
	}
}
