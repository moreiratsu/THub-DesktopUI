package com.alvaro.thub.desktop.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.ParticipantsByAgeChartView;

public class OpenStatsMenuController extends AbstractController{

	private Component parent = null;

	public OpenStatsMenuController(Component parent) {
		this.parent = parent;
	}
	
	public void doAction() {
		// Menú desplegable para Estadísticas
				JPopupMenu statisticsPopup = new JPopupMenu();
				JMenuItem participantesPorEdadItem = new JMenuItem("Participantes por Edad");
				participantesPorEdadItem.setHorizontalAlignment(SwingConstants.LEFT);
				participantesPorEdadItem.addActionListener(new OpenPlayersByAgeViewController());

				statisticsPopup.add(participantesPorEdadItem);
				statisticsPopup.show(parent, 0, parent.getHeight());
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();
	}

}
