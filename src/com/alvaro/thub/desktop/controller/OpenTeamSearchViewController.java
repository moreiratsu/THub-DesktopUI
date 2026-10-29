package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.TeamSearchView;

/**
 * Controlador para abrir la vista de búsqueda de equipos.
 * 
 * Este controlador se encarga de crear una nueva instancia de TeamSearchView y mostrarla en la ventana principal cuando el usuario selecciona la opción de búsqueda de equipos.
 */

public class OpenTeamSearchViewController extends AbstractController{

	
	public OpenTeamSearchViewController() {
	}
	
	@Override
	public void doAction() {
		TeamSearchView teamSearchView = new TeamSearchView();
		MainWindow.getInstance().addClosableView(teamSearchView.getName(), teamSearchView);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();
	}

}
