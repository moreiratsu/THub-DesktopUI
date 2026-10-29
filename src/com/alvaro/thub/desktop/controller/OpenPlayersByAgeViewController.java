package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.ParticipantsByAgeChartView;

public class OpenPlayersByAgeViewController extends AbstractController{


	public OpenPlayersByAgeViewController( ) {
	}

	public void doAction() {
		ParticipantsByAgeChartView chartView = new ParticipantsByAgeChartView();
		MainWindow.getInstance().addClosableView(chartView.getName(), chartView);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();
	}

}
