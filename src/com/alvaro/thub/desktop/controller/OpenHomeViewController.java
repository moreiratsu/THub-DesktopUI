package com.alvaro.thub.desktop.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.HomeView;
import com.alvaro.thub.desktop.views.ParticipantsByAgeChartView;

public class OpenHomeViewController extends AbstractController{


	public OpenHomeViewController() {
	}

	public void doAction() {
		HomeView homeView = new HomeView();
		MainWindow.getInstance().addClosableView(homeView.getName(), homeView);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();
	}



}
