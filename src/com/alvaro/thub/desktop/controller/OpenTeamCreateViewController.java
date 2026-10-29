package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.TeamView;
import com.alvaro.thub.desktop.views.UserView;

/**
 * Controlador para ABRIR LA VISTA DE CREACION DE EQUIPOS.
 */

public class OpenTeamCreateViewController extends AbstractController{


	public OpenTeamCreateViewController() {	
	}

	public void doAction() {
		TeamView teamCreateView = new TeamView();
		teamCreateView.setEditable(true);
		MainWindow.getInstance().addClosableView(teamCreateView.getName(), teamCreateView);			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();
	}

}
