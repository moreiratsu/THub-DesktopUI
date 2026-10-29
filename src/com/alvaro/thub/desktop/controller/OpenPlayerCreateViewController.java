package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.PlayerView;

public class OpenPlayerCreateViewController extends AbstractController{
	
	
	public OpenPlayerCreateViewController() {	
	}
	
	public void doAction() {
		PlayerView playerCreateView = new PlayerView();
		playerCreateView.setEditable(true);
		MainWindow.getInstance().addClosableView(playerCreateView.getName(), playerCreateView);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();	
	}

}
