package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.PlayerSearchView;

public class OpenPlayerSearchViewController extends AbstractController{

	
	public OpenPlayerSearchViewController() {	
	}
	
	public void doAction() {
		PlayerSearchView playerSearchView = new PlayerSearchView();
		MainWindow.getInstance().addClosableView(playerSearchView.getName(), playerSearchView);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();
	}

}
